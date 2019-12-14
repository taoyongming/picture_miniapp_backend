package web.controller;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.DO.WechatLoginRequest;
import web.redis.RedisUtil;
import web.service.WechatService;
import web.service.WechatUserService;

import javax.annotation.Resource;

/**
 * ${DESCRIPTION}
 *
 * @author tym
 * @ceeate 2019/11/14
 **/
@RestController("LoginController")
@RequestMapping(value = "/login")
public class LoginController {

    Logger logger = LoggerFactory.getLogger("LoginController");

    @Resource
    WechatService wechatService;
    @Autowired
    WechatUserService wechatUserService;

    @Autowired
    RedisUtil redisUtil;

    @ApiOperation(value = "1.微信授权接口", httpMethod = "POST" , notes = "微信授权接口")
    @PostMapping("/wxlogin")
    public String login(
            @RequestBody WechatLoginRequest loginRequest
         ) throws Exception {

        String userInfoInfo = wechatService.getUserInfoMap(loginRequest);
        return userInfoInfo;
    }






}
