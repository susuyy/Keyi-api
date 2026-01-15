package com.ht.note.notemark.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OpsGradePointReqData implements Serializable {

    private Long noteMarkId;

    private String opsFlag;
}
