package com.ajie.vod.controller;

import com.ajie.commonutils.R;
import com.ajie.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(description = "视频管理")
@RestController
@RequestMapping("/eduvod/videos")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;
    //上传视频到阿里云
    @ApiOperation("上传视频到阿里云")
    @PostMapping("addvod/uploadALiYunVideo")
    public R uploadALiYunVideo(MultipartFile file) {
        //返回上传视频的id
        String videoId = vodService.uploadALiYunVideo(file);
        return R.ok().data("videoId",videoId);
    }
}
