package com.ajie.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParaUtils implements InitializingBean {

    @Value("${aliyun.vod.file.ACCESS_KEY_ID}")
    private String keyId;

    @Value("${aliyun.vod.file.ACCESS_KEY_SECRET}")
    private String keySecret;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
    }
}
