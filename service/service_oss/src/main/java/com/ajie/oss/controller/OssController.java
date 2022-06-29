package com.ajie.oss.controller;


import com.ajie.commonutils.R;
import com.ajie.oss.service.OssService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin//跨域
public class OssController {
    @Autowired
    private OssService ossService;

    //上传头像的方法
    @ApiOperation(value = "头像上传")
    @PostMapping
    public R uploadFileOss(@ApiParam(name = "file",value = "文件",required = true)
                           @RequestParam("file") MultipartFile file) {
        //获取上传文件MultipartFile
        // 返回上传文件的OSS地址
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }

}
