package com.ajie.demo.edu.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//一级分类
@Data
public class OneSubject{
    private String id;
    private String title;

    //一级分类中的二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
