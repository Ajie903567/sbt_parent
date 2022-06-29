package com.ajie.demo.edu.service;

import com.ajie.demo.edu.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author AjieJava
 * @since 2021-11-09
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    void removeDescription(String courseId);
}
