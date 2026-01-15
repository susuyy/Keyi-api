package com.ht.note.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class MonthStatisticsData implements Serializable {

    /**
     * 年月格式的字符串
     */
    private String yearMonthKey;

    /**
     * 单月格式的字符串
     */
    private String monthKey;


}
