package web.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import web.Configurator.WxProperties;
import web.redis.RedisUtil;

/**
 * ${DESCRIPTION}
 *
 * @author tym
 * @ceeate 2019/11/19
 **/
@Component
public class AccessTokenUtil {
    static Logger logger = LoggerFactory.getLogger("AccessTokenUtil");

    private static WxProperties wxProperties;

    private static  RestTemplate restTemplate;

    private static  RedisUtil redisUtil;

    @Autowired
    public  void setWxProperties(WxProperties wxProperties) {
        AccessTokenUtil.wxProperties = wxProperties;
    }
    @Autowired
    public  void setRestTemplate(RestTemplate restTemplate) {
        AccessTokenUtil.restTemplate = restTemplate;
    }
    @Autowired
    public  void setRedisService(RedisUtil redisUtil) {
        AccessTokenUtil.redisUtil = redisUtil;
    }

    /**
     * 获取access_token
     * @return
     */
    public static String  getAccessToken() {

        Object access_token =  redisUtil.get("access_token");

        if(access_token == null || access_token.toString().equals("")) {
            // 小程序唯一标识 (在微信小程序管理后台获取)
            String wxspAppid = wxProperties.getAppid();
            logger.debug("wxspAppid:"+wxspAppid);
            // 小程序的 app secret (在微信小程序管理后台获取)
            String wxspSecret = wxProperties.getSecret();
            logger.debug("wxspSecret:"+wxspSecret);
            String grant_type = "client_credential";
            //封装请求数据
            String params = "grant_type=" + grant_type + "&secret=" + wxspSecret + "&appid=" + wxspAppid;
            //发送GET请求
            String result = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin/token?" + params, String.class);

            // 解析相应内容（转换成json对象）
            JSONObject obj = JSONObject.parseObject(result);
            access_token = obj.getString("access_token");
            int expires_in = Integer.parseInt(obj.getString("expires_in"))-300;
            System.out.println("access_token:"+access_token);

            redisUtil.set("access_token",access_token,expires_in);
        }

        return access_token.toString();
    }


    /**
     * 获取公众号access_token
     * @return
     */
    public static String  getGzhAccessToken() {

        Object gzh_access_token =  redisUtil.get("gzh_access_token");

        if(gzh_access_token == null || gzh_access_token.toString().equals("")) {
            // 公众号唯一标识
            String wxspAppid = wxProperties.getGzhAppid();
            logger.debug("wxspAppid:"+wxspAppid);
            // 公众号的 app secret
            String wxspSecret = wxProperties.getGzhSecret();
            logger.debug("wxspSecret:"+wxspSecret);
            String grant_type = "client_credential";
            //封装请求数据
            String params = "grant_type=" + grant_type + "&secret=" + wxspSecret + "&appid=" + wxspAppid;
            //发送GET请求
            String result = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin/token?" + params, String.class);

            // 解析相应内容（转换成json对象）
            JSONObject obj = JSONObject.parseObject(result);
            gzh_access_token = obj.getString("access_token");
            int expires_in = Integer.parseInt(obj.getString("expires_in"))-300;
            System.out.println("gzh_access_token:"+gzh_access_token);

            redisUtil.set("gzh_access_token",gzh_access_token,expires_in);
        }

        return gzh_access_token.toString();
    }
}
