package com.ajie.demo.edu.service;

import com.ajie.demo.edu.entity.EduChapter;
import com.ajie.demo.edu.entity.chapter.ChapterVo;
import com.ajie.demo.edu.entity.vo.ChapterInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author AjieJava
 * @since 2021-11-09
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    void addChapter(ChapterInfoVo chapterInfoVo);

    Boolean deleteAllChapter(String courseId);

    EduChapter selectChapterByID(String id);

    void removeChapter(String courseId);
}
