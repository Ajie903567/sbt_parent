package com.ajie.demo.edu.controller;


import com.ajie.commonutils.R;
import com.ajie.demo.edu.entity.EduVideo;
import com.ajie.demo.edu.entity.vo.VideoInfoVo;
import com.ajie.demo.edu.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author AjieJava
 * @since 2021-11-09
 */
@Api(description = "课程小节管理")
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    //往指定章节中添加小节
    @ApiOperation(value = "添加小节")
    @PostMapping("addVideoInChapter")
    public R addVideoInChapter(@RequestBody EduVideo eduVideo) {
        eduVideoService.saveVideo(eduVideo);
        return R.ok();
    }

    //删除小节
    //TODO 在删除小节的时候同时删除对应的视频数据
    @ApiOperation(value = "删除小节")
    @DeleteMapping("deleteVideoById/{id}")
    public R deleteVideoById(@PathVariable String id) {
        boolean flag = eduVideoService.removeById(id);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //修改小节
    @ApiOperation(value = "修改小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        boolean flag = eduVideoService.updateById(eduVideo);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //查询小节信息
    @ApiOperation(value = "查询小节信息")
    @GetMapping("getVideo/{id}")
    public R getVideo(@PathVariable String id) {
        EduVideo eduVideo = eduVideoService.getById(id);
        return R.ok().data("videoData",eduVideo);
    }
}

