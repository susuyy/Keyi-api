package com.ht.note.notemark.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RequestRecordNote implements Serializable {

    private String title;

    private String answer;

    private String type;

    private List<String> titleImageUrlList;

    private List<String> answerImageUrlList;

}
