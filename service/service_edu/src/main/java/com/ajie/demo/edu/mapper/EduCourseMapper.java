package com.ajie.demo.edu.mapper;

import com.ajie.demo.edu.entity.EduCourse;
import com.ajie.demo.edu.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author AjieJava
 * @since 2021-11-09
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishedCourseData(String courseId);
}
