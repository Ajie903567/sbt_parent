package com.ajie.demo.edu.service.impl;

import com.ajie.demo.edu.service.EduCourseDescriptionService;
import com.ajie.demo.edu.entity.EduCourseDescription;
import com.ajie.demo.edu.mapper.EduCourseDescriptionMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author AjieJava
 * @since 2021-11-09
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    //删除课程全部信息时，课程的描述部分
    @Override
    public void removeDescription(String courseId) {
        QueryWrapper<EduCourseDescription> wrapper = new QueryWrapper<>();
        wrapper.eq("id",courseId);
        baseMapper.delete(wrapper);
    }
}
