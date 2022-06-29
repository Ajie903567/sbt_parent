package com.ajie.demo.edu.controller;


import com.ajie.commonutils.R;
import com.ajie.demo.edu.entity.EduCourse;
import com.ajie.demo.edu.entity.vo.CoursePublishVo;
import com.ajie.demo.edu.service.EduCourseService;
import com.ajie.demo.edu.entity.vo.CourseInfoVo;
import com.ajie.servicebase.exceptionhandler.SelfException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author AjieJava
 * @since 2021-11-09
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    //添加课程基本信息的方法
    @ApiOperation(value = "添加课程信息")
    @PostMapping("addCourseData")
    public R addCourseData(@RequestBody CourseInfoVo courseInfoVo) {
        if (courseInfoVo.getTitle() == null || courseInfoVo.getTitle().equals("")) {
            throw new SelfException(20001,"请填写课程的名字！！！");
        }else if (courseInfoVo.getSubjectParentId() == null || courseInfoVo.getSubjectParentId().equals("")){
            throw new SelfException(20001,"请为课程添加一级分类！！！");
        }else if (courseInfoVo.getSubjectId() == null || courseInfoVo.getSubjectId().equals("")){
            throw new SelfException(20001,"请为课程选择二级分类！！！");
        }else if (courseInfoVo.getTeacherId() == null || courseInfoVo.getTeacherId().equals("")){
            throw new SelfException(20001,"情选择课程的讲师！！！");
        }else if (courseInfoVo.getDescription() == null || courseInfoVo.getDescription().equals("")){
            throw new SelfException(20001,"请添加课程描述！！！");
        }else {
            String id = eduCourseService.saveCourseData(courseInfoVo);
            return R.ok().data("courseId",id);
        }
    }

    //根据ID删除课程的基本信息
    @ApiOperation(value = "删除课程的基本信息")
    @DeleteMapping("deleteCourseById/{id}")
    public R deleteCourseById(@PathVariable String id) {
        boolean removeById = eduCourseService.deleteCourseDataById(id);
        if (removeById) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //课程修改
    @ApiOperation(value = "课程基本信息修改")
    @PostMapping("updateCourse")
    public R updateCourseById(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourse(courseInfoVo);
        return R.ok();
    }

    //根据ID进行查询
    @ApiOperation(value = "查询课程基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //课程发布前的全部信息查询
    @ApiOperation(value = "查询课程的所有信息")
    @GetMapping("getPublishCourseData/{courseId}")
    public R getPublishCourseData(@PathVariable String courseId){
        CoursePublishVo coursePublishVo= eduCourseService.getPublishCourseData(courseId);
        return R.ok().data("publishCourseData",coursePublishVo);
    }

    //课程最终发布（修改发布状态）
    @ApiOperation(value = "课程的最终发布")
    @PostMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    //查询全部课程用于列表显示
    @ApiOperation(value = "课程列表")
    @PostMapping("getCourseList")
    public R getCourseList() {
        List<EduCourse> eduCourseList = eduCourseService.list(null);
        return R.ok().data("courseList",eduCourseList);
    }

    //根据id删除课程全部的信息
    @ApiOperation(value = "删除课程全部信息")
    @DeleteMapping("deleteCourseAllData/{courseId}")
    public R deleteCourseAllData(@PathVariable String courseId) {
        eduCourseService.deleteCourseAllData(courseId);
        return R.ok();
    }
}

