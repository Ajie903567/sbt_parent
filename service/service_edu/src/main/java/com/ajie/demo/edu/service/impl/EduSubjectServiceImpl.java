package com.ajie.demo.edu.service.impl;

import com.ajie.demo.edu.entity.OneSubject;
import com.ajie.demo.edu.entity.excel.SubjectData;
import com.ajie.demo.edu.listener.SubjectExcelListener;
import com.ajie.demo.edu.service.EduSubjectService;
import com.ajie.demo.edu.entity.EduSubject;
import com.ajie.demo.edu.entity.TwoSubject;
import com.ajie.demo.edu.mapper.EduSubjectMapper;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author AjieJava
 * @since 2021-11-04
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            //创建文件输入流
            InputStream inputStream = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询全部的一级分类
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id","0");
        List<EduSubject> eduOneSubjects = baseMapper.selectList(wrapper1);

        //查询全部的二级分类
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id","0");
        List<EduSubject> eduTwoSubjects = baseMapper.selectList(wrapper2);

        //封装一级分类
        //因为要得到的结果的结构是OneSubject类型的，所以要转换格式
        List<OneSubject> oneSubjectList = new ArrayList<>();
        for (int i = 0; i < eduOneSubjects.size(); i++) {
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduOneSubjects.get(i),oneSubject);
            oneSubjectList.add(oneSubject);

            //封装二级分类
            List<TwoSubject> twoSubjectList = new ArrayList<>();
            for (int j = 0; j < eduTwoSubjects.size(); j++) {
                TwoSubject twoSubject = new TwoSubject();
                if (eduTwoSubjects.get(j).getParentId().equals(oneSubject.getId())) {
                    BeanUtils.copyProperties(eduTwoSubjects.get(j),twoSubject);
                    twoSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoSubjectList);
        }

        return oneSubjectList;
    }
}
