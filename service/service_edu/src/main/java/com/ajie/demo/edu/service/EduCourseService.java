package com.ajie.demo.edu.service;

import com.ajie.demo.edu.entity.EduCourse;
import com.ajie.demo.edu.entity.vo.CourseInfoVo;
import com.ajie.demo.edu.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author AjieJava
 * @since 2021-11-09
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseData(CourseInfoVo courseInfoVo);

    void updateCourse(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    boolean deleteCourseDataById(String id);

    CoursePublishVo getPublishCourseData(String courseId);

    void deleteCourseAllData(String courseId);
}
