package com.ajie.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    //上传视频到阿里云的方法
    String uploadALiYunVideo(MultipartFile file);
}
