package com.ht.note.notemark.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseRequestPageData implements Serializable {

    private long pageNo;

    private long pageSize;
}
