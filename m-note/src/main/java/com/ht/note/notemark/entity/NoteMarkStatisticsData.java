package com.ht.note.notemark.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class NoteMarkStatisticsData implements Serializable {

    private long countNumber;

    private String noteRecordDay;
}
