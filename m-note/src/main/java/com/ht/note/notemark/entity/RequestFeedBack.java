package com.ht.note.notemark.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RequestFeedBack implements Serializable {

    /**
     * 文字内容
     */
    private String content;

    /**
     * 图片1
     */
    private List<String> imageUrlList;


    /**
     * 联系方式
     */
    private String contactMessage;
}
