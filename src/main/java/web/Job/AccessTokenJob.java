package web.Job;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import web.Configurator.WxProperties;
import web.redis.RedisUtil;

/**
 * 定时获取access_token
 *
 * @author tym
 * @ceeate 2019/11/18
 **/
@Lazy(false)
@Service
public class AccessTokenJob {
    @Autowired
    private WxProperties wxProperties;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisUtil redisUtil;
    Logger logger = LoggerFactory.getLogger("AccessTokenJob");

    @Scheduled(cron="0 */110 * * * ? ")   //每110分钟执行一次
    public void accessTokenJob() {
        System.out.println("accessTokenJob定时任务开始");
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
        String access_token = obj.getString("access_token");
        int expires_in = Integer.parseInt(obj.getString("expires_in"))-300;

        System.out.println("access_token:"+access_token);
        redisUtil.set("access_token",access_token,expires_in);

        System.out.println("accessTokenJob定时任务结束");
    }

    @Scheduled(cron="0 */110 * * * ? ")   //每110分钟执行一次
    public void gzhAccessTokenJob() {
        System.out.println("gzhaccessTokenJob定时任务开始");
        // 小程序唯一标识 (在微信小程序管理后台获取)
        String wxspAppid = wxProperties.getGzhAppid();
        logger.debug("wxspAppid:"+wxspAppid);
        // 小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = wxProperties.getGzhSecret();
        logger.debug("wxspSecret:"+wxspSecret);
        String grant_type = "client_credential";
        //封装请求数据
        String params = "grant_type=" + grant_type + "&secret=" + wxspSecret + "&appid=" + wxspAppid;
        //发送GET请求
        String result = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin/token?" + params, String.class);

        // 解析相应内容（转换成json对象）
        JSONObject obj = JSONObject.parseObject(result);
        String access_token = obj.getString("access_token");
        int expires_in = Integer.parseInt(obj.getString("expires_in"))-300;

        System.out.println("gzh_access_token:"+access_token);
        redisUtil.set("gzh_access_token",access_token,expires_in);

        System.out.println("gzhaccessTokenJob定时任务结束");
    }
}
