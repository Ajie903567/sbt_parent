package com.ajie.demo.edu.service.impl;

import com.ajie.demo.edu.entity.EduVideo;
import com.ajie.demo.edu.entity.vo.VideoInfoVo;
import com.ajie.demo.edu.service.EduVideoService;
import com.ajie.demo.edu.mapper.EduVideoMapper;
import com.ajie.servicebase.exceptionhandler.SelfException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author AjieJava
 * @since 2021-11-09
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Override
    public void saveVideo(EduVideo eduVideo) {
//        EduVideo eduVideo = new EduVideo();
//        BeanUtils.copyProperties(videoInfoVo,eduVideo);
        int i = baseMapper.insert(eduVideo);
        if (i != 1) {
            throw new SelfException(20001,"课程小节添加失败！");
        }
    }

    //全部删除时，删除小节部分
    @Override
    public void removeVideo(String courseId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }
}
