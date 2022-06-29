package com.ajie.demo.edu.service.impl;

import com.ajie.demo.edu.entity.EduVideo;
import com.ajie.demo.edu.entity.chapter.ChapterVo;
import com.ajie.demo.edu.entity.chapter.VideoVo;
import com.ajie.demo.edu.entity.vo.ChapterInfoVo;
import com.ajie.demo.edu.service.EduChapterService;
import com.ajie.demo.edu.entity.EduChapter;
import com.ajie.demo.edu.mapper.EduChapterMapper;
import com.ajie.demo.edu.service.EduVideoService;
import com.ajie.servicebase.exceptionhandler.SelfException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    //从数据库中查询所有章节和小节
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

//        根据课程ID查询其所有的章节
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(chapterWrapper);

//        根据课程ID查询其所有的小节
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(videoWrapper);

//        对章节进行封装
        //创建一个用于接受结果的集合
        List<ChapterVo> chapterVoList= new ArrayList<>();
        for (EduChapter eduChapter : eduChapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            chapterVoList.add(chapterVo);
//        对小节进行封装
            //创建一个用于接受结果的集合
            List<VideoVo> videoVoList = new ArrayList<>();
            for (EduVideo eduVideo : eduVideoList) {
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setVideoVoList(videoVoList);
        }

        return chapterVoList;
    }

    //为课程添加章节
    @Override
    public void addChapter(ChapterInfoVo chapterInfoVo) {
        //将ChapterInfoVo转换成EduChapter进行存储
        EduChapter eduChapter = new EduChapter();
        BeanUtils.copyProperties(chapterInfoVo,eduChapter);
        int i = baseMapper.insert(eduChapter);
        if (i !=1 ) {
            throw new SelfException(20001,"添加章节失败！");
        }
    }

    //删除全部章节
    @Override
    public Boolean deleteAllChapter(String courseId) {
        //删除小节
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(eduVideoQueryWrapper);
        if (eduVideoList.size() > 0) {
            boolean remove = eduVideoService.remove(eduVideoQueryWrapper);
        }
        //删除章节
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(eduChapterQueryWrapper);
        if (eduChapterList.size() > 0) {
            int delete = baseMapper.delete(eduChapterQueryWrapper);
        }
        return null;
    }

    //查询指定接口
    @Override
    public EduChapter selectChapterByID(String id) {
        EduChapter eduChapter = baseMapper.selectById(id);
        return eduChapter;
    }

    //删除课程的全部信息，章节部分
    @Override
    public void removeChapter(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
