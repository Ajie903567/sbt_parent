package com.ajie.demo.edu.controller;

import com.ajie.commonutils.R;
import com.ajie.demo.edu.entity.EduTeacher;
import com.ajie.demo.edu.entity.vo.TeacherQuery;
import com.ajie.demo.edu.service.EduTeacherService;
import com.ajie.servicebase.exceptionhandler.SelfException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author AjieJava
 * @since 2021-10-11
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin //解决跨域问题
public class EduTeacherController {

//    把service注入
    @Autowired
    private EduTeacherService eduTeacherService;

//    一、查询所有
    @ApiOperation(value = "查询所有")
    @GetMapping("findAll")
    public R findAllTeacher() {
//        调用service方法来实现查询所有
        List<EduTeacher> teacherList = eduTeacherService.list(null);
        return R.ok().data("items",teacherList);
    }

//    二、逻辑删除（根据ID）
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("{deleteById}")
//    通过路径获取id值
    public R removeTeacher(@PathVariable String deleteById) {
        boolean flag = eduTeacherService.removeById(deleteById);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }

//    三、分页查询
    @ApiOperation(value = "分页查询")
    @GetMapping("pageList/{currentPage}/{pageSize}")
    public R pageListTeacher(@PathVariable Long currentPage,@PathVariable Long pageSize) {
//        创建page对象
        Page<EduTeacher> teacherPage = new Page<>(currentPage,pageSize);
//        调用的方法实现分页，底层封装，把所有的数据封装到pageList对象中
        eduTeacherService.page(teacherPage,null);

        Long total = teacherPage.getTotal();//总记录数
        List<EduTeacher> teacherList = teacherPage.getRecords();//每页的list集合

//        返回的写法一
        return R.ok().data("total",total).data("rows",teacherList);

////        返回的写法二
//        Map map = new HashMap();
//        map.put("total",total);
//        map.put("rows",pageList);
//        return R.ok().data(map);

    }

//    四、多条件查询分页
    @ApiOperation(value = "分页条件查询")
    @PostMapping("pageTeacherCondition/{currentPage}/{pageSize}")
    public R pageTeacherCondition (@PathVariable Long currentPage, @PathVariable Long pageSize,
                                   @RequestBody(required = false) TeacherQuery teacherQuery) {
//        创建page对象
        Page<EduTeacher> teacherPage = new Page<>(currentPage, pageSize);
//        创建查询条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        // 排序
        wrapper.orderByDesc("gmt_create");
//        判断四个条件的情况，进行相关的查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isNullOrEmpty(name)) {
            wrapper.like("name",name);
        }
        if (level != null){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isNullOrEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isNullOrEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        //        调用方法
        eduTeacherService.page(teacherPage,wrapper);
        Long total = teacherPage.getTotal();
        List<EduTeacher> teacherList = teacherPage.getRecords();
        return R.ok().data("total",total).data("rows",teacherList);
    }

//    五、添加老师
    @ApiOperation(value = "添加老师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        if (eduTeacher.getName() == null || eduTeacher.getName().equals("")){
            throw new SelfException(20001,"请填写讲师姓名！");
        }else if (eduTeacher.getIntro() == null || eduTeacher.getIntro().equals("")){
            throw new SelfException(20001,"请填写讲师简介！");
        }else if (eduTeacher.getCareer() == null || eduTeacher.getCareer().equals("")){
            throw new SelfException(20001,"请填写讲师资历！");
        }else if (eduTeacher.getLevel() == null || eduTeacher.getLevel().equals("")){
            throw new SelfException(20001,"请选择讲师头衔！");
        }else {
            boolean save = eduTeacherService.save(eduTeacher);
            if (save) {
                return R.ok();
            }else {
                return R.error();
            }

        }
    }

//    六、根据ID进行老师的查询
    @ApiOperation(value = "根据ID查询")
    @GetMapping("selectTeacherById/{id}")
    public R selectTeacherById(@PathVariable String id) {
//        异常测试
//        try {
//            int i = 10/0;
//        } catch (Exception e) {
//            throw new SelfException(2001,"执行了SelfException异常处理。。。。");
//        }
        EduTeacher eduTeacher = eduTeacherService.getById(id);
            return R.ok().data("teacher",eduTeacher);
    }

//    七、根据ID对老师进行修改
    @ApiOperation(value = "修改老师")
    @PostMapping("updateTeacherById")
    public R updateTeacherById(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }



}

