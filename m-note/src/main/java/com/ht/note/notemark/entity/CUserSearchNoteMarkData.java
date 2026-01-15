package com.ht.note.notemark.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "用户笔记查询参数")
public class CUserSearchNoteMarkData implements Serializable {

    @ApiModelProperty(value = "分页参数当前页")
    private long pageNo;

    @ApiModelProperty(value = "每页大小")
    private long pageSize;

    @ApiModelProperty(value = "标题,可空,模糊查询")
    private String title;

    @ApiModelProperty(value = "查询时间段 开始时间 yyyy-mm-dd格式")
    private String startTime;

    @ApiModelProperty(value = "查询时间段 结束时间 yyyy-mm-dd格式")
    private String endTime;

}
