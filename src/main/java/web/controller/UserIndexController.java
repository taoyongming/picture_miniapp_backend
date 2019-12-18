package web.controller;

import cn.hutool.crypto.SecureUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.entity.FileDocument;
import web.entity.ResponseModel;
import web.entity.UserFavor;
import web.service.IFileService;
import web.serviceImpl.FileServiceImpl;
import web.util.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 小程序首页
 *
 * @author tym
 * @ceeate 2019/12/18
 **/
@Slf4j
@Api(tags = "UserIndexController", description = "小程序首页")
@RestController()
@RequestMapping("/index")
public class UserIndexController {

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private IFileService fileService;


    @ApiOperation(value = "获取首页图片列表", httpMethod = "GET" , notes = "用户左滑右滑选择")
    @GetMapping("/getImageList/{pageIndex}/{pageSize}")
    public R getImageList(@PathVariable(value = "pageIndex",  required = true) int pageIndex,
                          @PathVariable(value = "pageSize", required = true) int pageSize) {
        HttpSession session = httpServletRequest.getSession();
        String openid =  session.getAttribute("openid").toString();



        return R.ok();
    }

    @ApiOperation(value = "上传图片", httpMethod = "POST" , notes = "上传图片")
    @PostMapping(value = "/uploadImages", headers = "content-type=multipart/form-data")
    public R uploadImages(@RequestParam("file") MultipartFile file) {
        HttpSession session = httpServletRequest.getSession();
        String openid =  session.getAttribute("openid").toString();
        try {
            if(file != null && !file.isEmpty()){
                String fileMd5 = SecureUtil.md5(file.getInputStream());
                FileDocument fileDocument = fileService.saveFile(fileMd5 , file, openid);

                System.out.println(fileDocument);

            }else {
                R.error("请传入文件");
            }
        }catch (IOException ex){
            ex.printStackTrace();
            R.error(ex.getMessage());
        }
        return R.ok();
    }

}
