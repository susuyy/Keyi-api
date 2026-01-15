package com.ht.note.notemark.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "用户笔记查询参数")
public class SearchStatisticsMonthCount implements Serializable {

    /**
     * 统计月份
     */
    @ApiModelProperty(value = "统计月份  yyyy-mm 格式",example = "2023-06")
    private String noteMarkStatisticsMonth;
}
