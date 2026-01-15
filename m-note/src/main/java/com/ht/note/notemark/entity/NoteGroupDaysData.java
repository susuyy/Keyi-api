package com.ht.note.notemark.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NoteGroupDaysData implements Serializable {

    /**
     * 几笔记录日期
     */
    private String noteRecordDay;

    /**
     * 笔记列表
     */
    private List<NoteMark> noteMarkList;
}
