package com.ajie.demo.edu.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {
    private String id;
    private String title;

    List<VideoVo> videoVoList = new ArrayList<VideoVo>();
}
