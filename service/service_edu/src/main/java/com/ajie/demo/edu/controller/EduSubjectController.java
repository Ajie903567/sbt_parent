package com.ajie.demo.edu.controller;


import com.ajie.commonutils.R;
import com.ajie.demo.edu.entity.OneSubject;
import com.ajie.demo.edu.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author AjieJava
 * @since 2021-11-04
 */
@Api(description = "课程分类管理")
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @ApiOperation(value = "xml文件添加课程")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
    //上传过来的Excel文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    @ApiOperation(value = "查询所有课程分类")
    @GetMapping("getAllOneTwoSubject")
    public R getAllOneTwoSubject() {
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

