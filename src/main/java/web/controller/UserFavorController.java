package web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.util.R;

/**
 * DESCRIPTION
 *
 * @author tym
 * @ceeate 2019/12/9
 **/
@RestController
@RequestMapping("picture")
public class UserFavorController {



    @ApiOperation(value = "用户左滑右滑选择", httpMethod = "GET" , notes = "用户左滑右滑选择")
    @GetMapping("/userfavor")
    public R userfavor(String imageMd5, boolean favor) {


        return R.ok();
    }

}
