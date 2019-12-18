package web.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.entity.FileDocument;
import web.entity.ResponseModel;
import web.service.IFileService;
import web.util.FileContentTypeUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/files")
@Api(tags = "FileController", description = "文件上传下载")
public class FileController {

    @Autowired
    private IFileService fileService;


    /**
     * 列表数据
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "列表数据", httpMethod = "GET", notes = "列表数据")
    @RequestMapping("/list")
    public List<FileDocument> list(  @RequestParam(value = "pageIndex", defaultValue = "1", required = true) int pageIndex,
                                     @RequestParam(value = "pageSize", defaultValue = "5", required = true) int pageSize){
        return fileService.listFilesByPage(pageIndex,pageSize);
    }

    /**
     * 在线显示文件
     * @param id 文件id
     * @return
     */
    @ApiOperation(value = "在线显示文件", httpMethod = "GET", notes = "在线显示文件")
    @GetMapping("/view/{id}")
    public ResponseEntity<Object> serveFileOnline( @PathVariable String id) {
        Optional<FileDocument> file = fileService.getById(id);
        if (file.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=" + file.get().getName())
                    .header(HttpHeaders.CONTENT_TYPE, file.get().getContentType())
                    .header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
                    .header(HttpHeaders.CONTENT_LENGTH , file.get().getSize() + "")
                    .body(file.get().getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not found");
        }
    }

    /**
     * 下载附件
     * @param id
     * @return
     * @throws UnsupportedEncodingException
     */
    @ApiOperation(value = "下载附件", httpMethod = "GET", notes = "下载附件")
    @GetMapping("/{id}")
    public ResponseEntity<Object> downloadFileById(@PathVariable String id) throws UnsupportedEncodingException {
        Optional<FileDocument> file = fileService.getById(id);
        if(file.isPresent()){
           return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + URLEncoder.encode(file.get().getName() , "utf-8"))
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
                    .header(HttpHeaders.CONTENT_LENGTH , file.get().getSize() + "")
                    .body(file.get().getContent());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not found");
        }
    }

    /**
     * JS传字节流上传 - 暂时未完成
     * @param md5
     * @param ext
     * @return
     */
    @Deprecated
    @ApiOperation(value = "JS传字节流上传 - 暂时未完成", httpMethod = "GET", notes = "JS传字节流上传 - 暂时未完成")
    @PostMapping("/upload/{md5}/{ext}")
    public ResponseModel jsUpload(@PathVariable String md5 , @PathVariable String ext , HttpServletRequest request, @RequestBody byte[] data){
        ResponseModel model = ResponseModel.getInstance();
        if(StrUtil.isEmpty(md5)){
            model.setMessage("请传入文件的md5值");
            return model;
        }
        if(!StrUtil.isEmpty(ext) && !ext.startsWith(".")){
            ext = "." + ext;
        }
        try{

            String name = request.getParameter("name");
            String description = request.getParameter("description");
            InputStream in = new ByteArrayInputStream(data);
            System.out.println("data_string:" + StrUtil.str(data , "UTF-8"));
            if(in != null && data.length > 0){
                FileDocument fileDocument = new FileDocument();
                fileDocument.setName(name);
                fileDocument.setSize(data.length);
                fileDocument.setContentType(FileContentTypeUtils.getContentType(ext));
                fileDocument.setUploadDate(new Date());
                fileDocument.setSuffix(ext);
                String fileMd5 = SecureUtil.md5(in);
                fileDocument.setMd5(fileMd5);
                System.out.println(md5 +" , "+ fileMd5);
                fileDocument.setDescription(description);
                fileService.saveFile(fileDocument , in);

                System.out.println(fileDocument);
                model.setData(fileDocument.getId());
                model.setCode(ResponseModel.Success);
                model.setMessage("上传成功");
            }else{
                model.setMessage("请传入文件");
            }
            in.close();
        }catch (Exception ex){
            ex.printStackTrace();
            model.setMessage("上传失败");
        }
        return model;
    }

    /**
     * 表单上传文件
     * 当数据库中存在该md5值时，可以实现秒传功能
     * @param file 文件
     * @return
     */
    @ApiOperation(value = "表单上传文件", httpMethod = "POST", notes = "表单上传文件")
    @PostMapping("/upload")
    public ResponseModel formUpload(@RequestParam("file") MultipartFile file){
        ResponseModel model = ResponseModel.getInstance();
        try {
            if(file != null && !file.isEmpty()){
                String fileMd5 = SecureUtil.md5(file.getInputStream());
                FileDocument fileDocument = fileService.saveFile(fileMd5 , file,null);

                System.out.println(fileDocument);
                model.setData(fileDocument.getId());
                model.setCode(ResponseModel.Success);
                model.setMessage("上传成功");
            }else {
                model.setMessage("请传入文件");
            }
        }catch (IOException ex){
            ex.printStackTrace();
            model.setMessage(ex.getMessage());
        }
        return model;
    }


    /**
     * 删除附件
     * @param id
     * @return
     */
    @ApiOperation(value = "删除附件", httpMethod = "DELETE", notes = "删除附件")
    @DeleteMapping("/{id}")
    public ResponseModel deleteFile(@PathVariable String id){
        ResponseModel model = ResponseModel.getInstance();
        if(!StrUtil.isEmpty(id)){
            fileService.removeFile(id , true);
            model.setCode(ResponseModel.Success);
            model.setMessage("删除成功");
        }else {
            model.setMessage("请传入文件id");
        }
        return model;
    }


    /**
     * 删除附件
     * @param id
     * @return
     */
    @ApiOperation(value = "删除附件", httpMethod = "GET", notes = "删除附件")
    @GetMapping("/delete/{id}")
    public ResponseModel deleteFileByGetMethod(@PathVariable String id){
        ResponseModel model = ResponseModel.getInstance();
        if(!StrUtil.isEmpty(id)){
            fileService.removeFile(id , true);
            model.setCode(ResponseModel.Success);
            model.setMessage("删除成功");
        }else {
            model.setMessage("请传入文件id");
        }
        return model;
    }
}
