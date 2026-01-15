package com.ht.note.notemark.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AphorismData implements Serializable {

    private String aphorismStr;

    private String aphorismAuthor;
}
