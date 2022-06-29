package com.ajie.demo.edu.service.impl;

import com.ajie.demo.edu.entity.EduChapter;
import com.ajie.demo.edu.entity.EduCourse;
import com.ajie.demo.edu.entity.EduCourseDescription;
import com.ajie.demo.edu.entity.EduVideo;
import com.ajie.demo.edu.entity.vo.CoursePublishVo;
import com.ajie.demo.edu.service.EduChapterService;
import com.ajie.demo.edu.service.EduCourseDescriptionService;
import com.ajie.demo.edu.service.EduCourseService;
import com.ajie.demo.edu.entity.vo.CourseInfoVo;
import com.ajie.demo.edu.mapper.EduCourseMapper;
import com.ajie.demo.edu.service.EduVideoService;
import com.ajie.servicebase.exceptionhandler.SelfException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author AjieJava
 * @since 2021-11-09
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //这是在EduCourseService的实现类中，要向EduCourseDescription中添加信息，就要将其注入进来
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduVideoService eduVideoService;

    //添加课程信息
    @Override
    public String saveCourseData(CourseInfoVo courseInfoVo) {
        //将courseInfoVo转换成EduCourse,再进行存储
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);//记录返回的影响行数
        if (insert <= 0) {
            throw new SelfException(20001,"添加课程信息失败！！");
        }
        //如果添加成功，就获取课程的id
        String courseId = eduCourse.getId();

        //将courseInfoVo转换成EduCourseDescription,再进行存储
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        eduCourseDescription.setId(courseId);
        boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        if (!save) {
            throw new SelfException(20001,"添加课程信息失败！！！");
        }
        return courseId;
    }

    //修改
    @Override
    public void updateCourse(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update != 1) {
            throw new SelfException(20001,"课程信息修改失败！！！");
        }
        //修改描述
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,courseDescription);
        eduCourseDescriptionService.updateById(courseDescription);
    }

    //查询
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //查询课程信息
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //查询课程描述
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    //删除课程的全部信息（取消发布）
    @Override
    public boolean deleteCourseDataById(String id) {
        //删除课程基本信息
        int id1 = baseMapper.deleteById(id);
        //删除课程描述信息
        boolean id2 = eduCourseDescriptionService.removeById(id);
        //删除课程章节信息
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper();
        chapterWrapper.eq("course_id",id);
        List<EduChapter> eduChapterList = eduChapterService.list(chapterWrapper);
        if (eduChapterList.size() > 0) {
            boolean remove = eduChapterService.remove(chapterWrapper);
        }
        //删除课程小节信息
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",id);
        List<EduVideo> eduVideoList = eduVideoService.list(videoWrapper);
        if (eduVideoList.size() > 0) {
            boolean id4 = eduVideoService.removeById(id);
        }

        if (id1 != 0 && id2) {
            return true;
        }else {
            return false;
        }
    }

    //课程发布前的所有信息的查询
    @Override
    public CoursePublishVo getPublishCourseData(String courseId) {
        //调用自己的方法
        CoursePublishVo publishedCourseData = baseMapper.getPublishedCourseData(courseId);
        return publishedCourseData;
    }

    //删除课程的全部信息(虽然没有使用外键，但还是建议按照顺序删除)
    @Override
    public void deleteCourseAllData(String courseId) {
        //删除小节
        eduVideoService.removeVideo(courseId);
        //删除章节
        eduChapterService.removeChapter(courseId);
        //删除课程描述
        eduCourseDescriptionService.removeDescription(courseId);
        //删除课程本身
        int i = baseMapper.deleteById(courseId);
        if (i == 0) {
            throw new SelfException(20001,"删除失败");
        }
    }
}
