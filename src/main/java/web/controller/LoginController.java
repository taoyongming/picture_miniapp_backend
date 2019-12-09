package web.controller;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.DO.WechatLoginRequest;
import web.redis.RedisUtil;
import web.service.WechatService;
import web.service.WechatUserService;
import web.util.PasswordUtil;

import javax.annotation.Resource;

/**
 * ${DESCRIPTION}
 *
 * @author tym
 * @ceeate 2019/11/14
 **/
@RestController("LoginController")
@RequestMapping(value = "/wechat")
public class LoginController {
    Logger logger = LoggerFactory.getLogger("LoginController");


    @Resource
    WechatService wechatService;
    @Autowired
    WechatUserService wechatUserService;

    @Autowired
    RedisUtil redisUtil;

    @ApiOperation(value = "1.微信授权接口", httpMethod = "GET" , notes = "微信授权接口")
    @GetMapping("/wxlogin")
    public String login(
            WechatLoginRequest loginRequest
         ) throws Exception {

        String userInfoInfo = wechatService.getUserInfoMap(loginRequest);
        return userInfoInfo;
    }


    public static void main(String[] args) {
        String username = "fengyu";
        String password = "fengyu";
        String newPassword = PasswordUtil.md5(password, username);

        System.out.println(newPassword);
    }



}
