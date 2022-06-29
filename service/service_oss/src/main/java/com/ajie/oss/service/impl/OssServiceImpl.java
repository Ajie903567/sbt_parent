package com.ajie.oss.service.impl;

import com.ajie.oss.service.OssService;
import com.ajie.oss.utils.ConstantPropertiesUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override//上传头像文件到OSS
    public String uploadFileAvatar(MultipartFile file) {
        //工具类获取
        String endPoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        //创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);

        try {
            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();
            //1、在文件名称里添加一个随机值，用来区分重复上传的同名文件
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;

            //2、把文件按照日期进行分类
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //   2021/11/03/uuid/01.jpg
            fileName = datePath + "/" + fileName;

            //调用OSS方法实现上传
            ossClient.putObject(bucketName,fileName,inputStream);
            //关闭oss流
            ossClient.shutdown();

            //手动的拼接oss文件上传的地址
            //https://ajie-springboot.oss-cn-beijing.aliyuncs.com/%E9%A1%B9%E7%9B%AE%E5%9B%BE%E7%89%87/04.jpg
            String url = "https://" + bucketName + "." + endPoint + "/" + fileName;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
