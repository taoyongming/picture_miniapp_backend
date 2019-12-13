package web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.entity.UserFavor;
import web.service.UserFavorService;
import web.util.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * DESCRIPTION
 *
 * @author tym
 * @ceeate 2019/12/9
 **/
@Slf4j
@Api(tags = "UserFavorController", description = "用户左滑右滑功能")
@RestController()
@RequestMapping("/userfavor")
public class UserFavorController {
    @Autowired
    private UserFavorService userFavorService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @ApiOperation(value = "用户左滑右滑选择", httpMethod = "GET" , notes = "用户左滑右滑选择")
    @GetMapping("/choose/{imageMd5}/{favor}")
    public R choose(@PathVariable String imageMd5,  @PathVariable boolean favor) {
        HttpSession session = httpServletRequest.getSession();
        String openid =  session.getAttribute("openid").toString();

        UserFavor userFavor = new UserFavor();
        userFavor.setImageMd5(imageMd5);
        userFavor.setIsfavor(favor);
        userFavor.setOpenid(openid);
        userFavorService.save(userFavor);

        return R.ok();
    }

}
