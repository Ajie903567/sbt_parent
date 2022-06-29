package com.ajie.demo.edu.controller;


import com.ajie.commonutils.R;
import com.ajie.demo.edu.entity.EduChapter;
import com.ajie.demo.edu.entity.chapter.ChapterVo;
import com.ajie.demo.edu.entity.vo.ChapterInfoVo;
import com.ajie.demo.edu.service.EduChapterService;
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
@Api(description = "课程章节管理")
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //查询全部章节和小节
    @ApiOperation(value = "获取章节和小节")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("item",list);
    }

    //添加章节
    @ApiOperation(value = "章节添加")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody ChapterInfoVo chapterInfoVo) {
        eduChapterService.addChapter(chapterInfoVo);
        return R.ok();
    }

    //删除全部章节（返回上一步）
    @ApiOperation(value = "返回上一步，删除全部章节")
    @DeleteMapping("deleteAllChapter/{courseId}")
    public R deleteAllChapter(@PathVariable String courseId) {
        Boolean delete = eduChapterService.deleteAllChapter(courseId);
        return R.ok();
    }

    //修改章节
    @ApiOperation(value = "修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        boolean flag = eduChapterService.updateById(eduChapter);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据id查询章节
    @ApiOperation(value = "查询指定章节")
    @GetMapping("getChapterByID/{id}")
    public R getChapterByID(@PathVariable String id) {
        EduChapter eduChapter = eduChapterService.selectChapterByID(id);
        return R.ok().data("eduChapter",eduChapter);
    }

    //删除指定的章节
    @ApiOperation(value = "删除章节")
    @DeleteMapping("deleteChapterById/{id}")
    public R deleteChapterByID(@PathVariable String id) {
        boolean flag = eduChapterService.removeById(id);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }

}

