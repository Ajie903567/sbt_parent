package com.ajie.demo.edu.service;

import com.ajie.demo.edu.entity.EduVideo;
import com.ajie.demo.edu.entity.vo.VideoInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author AjieJava
 * @since 2021-11-09
 */
public interface EduVideoService extends IService<EduVideo> {

    void saveVideo(EduVideo eduVideo);

    void removeVideo(String courseId);
}
