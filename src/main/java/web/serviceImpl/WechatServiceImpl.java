package web.serviceImpl;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.Configurator.WxProperties;

import web.DO.RawDataDO;
import web.DO.WechatLoginRequest;
import web.entity.WechatUser;
import web.redis.RedisUtil;
import web.service.WechatService;
import web.service.WechatUserService;
import web.util.Constant;
import web.util.HttpClientUtils;
import web.util.TokenUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class WechatServiceImpl implements WechatService {
    private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private WxProperties wxProperties;
    @Override
    public String getUserInfoMap(WechatLoginRequest loginRequest) throws Exception {

        // log报错的话，删掉就好，或者替换为自己的日志对象
        log.info("Start get SessionKey，loginRequest的数据为：" + JSONObject.toJSONString(loginRequest));
        JSONObject sessionKeyOpenId = getSessionKeyOrOpenId(loginRequest.getCode());
        JSONObject returnJson = new JSONObject();

        // 获取openId && sessionKey
        String openId = sessionKeyOpenId.getString("openid");
        String sessionKey = sessionKeyOpenId.getString("session_key");

        log.debug(openId);
        if(openId == null) {
            log.error("openid is null");
            returnJson.put("errorCode", Constant.ERROR_CODE_loginError);
            returnJson.put("message", "openId is null");
            return returnJson.toJSONString();
        }

        //解密获得微信用户信息
        WechatUser wechatUser = this.buildWechatUserAuthInfoDO(loginRequest, sessionKey, openId);

        String token = UUID.randomUUID().toString();
        wechatUser.setToken(token);
        log.info(wechatUser.toString());

        // 根据openid查询用户
        WechatUser user = wechatUserService.getByOpenId(openId);

        if (user == null) {
                wechatUserService.save(wechatUser);
        }else {
            wechatUserService.updateByOpenId(wechatUser);
        }

        redisUtil.hset("token",token,openId);
        returnJson.put("token", token);
        returnJson.put("token", token);
        returnJson.put("token", token);
        returnJson.put("errorCode", Constant.ERROR_CODE_loginSucc);
        return returnJson.toJSONString();
    }

    // 这里的JSONObject是阿里的fastjson，自行maven导入
    public JSONObject getSessionKeyOrOpenId(String code) throws Exception {
        Map<String, String> requestUrlParam = new HashMap<>();
        // 小程序appId，自己补充
        requestUrlParam.put("appid", wxProperties.getAppid());
        // 小程序secret，自己补充
        requestUrlParam.put("secret", wxProperties.getSecret());
        // 小程序端返回的code
        requestUrlParam.put("js_code", code);
        // 默认参数
        requestUrlParam.put("grant_type", "authorization_code");
        log.debug(REQUEST_URL+requestUrlParam);
        // 发送post请求读取调用微信接口获取openid用户唯一标识
        String result = HttpClientUtils.doPost(REQUEST_URL, requestUrlParam);
        log.debug("https://api.weixin.qq.com/sns/jscode2session 返回结果："+result);
        return JSON.parseObject(result);
    }

    private WechatUser buildWechatUserAuthInfoDO(WechatLoginRequest loginRequest, String sessionKey, String openId){
        WechatUser wechatUserDO = new WechatUser();
        wechatUserDO.setOpenId(openId);
        wechatUserDO.setSessionKey(sessionKey);
        if (loginRequest.getRawData() != null) {
            RawDataDO rawDataDO = JSON.parseObject(loginRequest.getRawData(), RawDataDO.class);
            wechatUserDO.setNickname(rawDataDO.getNickName());
            wechatUserDO.setAvatarUrl(rawDataDO.getAvatarUrl());
            wechatUserDO.setGender(rawDataDO.getGender());
            wechatUserDO.setCity(rawDataDO.getCity());
            wechatUserDO.setCountry(rawDataDO.getCountry());
            wechatUserDO.setProvince(rawDataDO.getProvince());

        }

        // 解密加密信息，获取unionID
        if (loginRequest.getEncryptedData() != null){
            JSONObject encryptedData = getEncryptedData(loginRequest.getEncryptedData(), sessionKey, loginRequest.getIv());
            if (encryptedData != null){
                String unionId = encryptedData.getString("unionId");
                wechatUserDO.setUnionId(unionId);
            }
        }

        return wechatUserDO;
    }

    private JSONObject getEncryptedData(String encryptedData, String sessionkey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionkey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.这个if中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            log.error("解密加密信息报错"+e.getMessage());
        }
        return null;
    }


//        protected String getToken(WechatUser user) {
//            String token="";
//            token= JWT.create()
//                    .withKeyId(user.getOpenId())
//                    .withIssuer("www.taoyongming.com")
//                    .withIssuedAt(new Date())
//                    .withJWTId("www.taoyongming.com")
//                    .withClaim("session_key", user.getSessionKey())
//                    .withAudience(user.getOpenId())
//                    .sign(Algorithm.HMAC256(user.getOpenId()));
//            return token;
//        }





}