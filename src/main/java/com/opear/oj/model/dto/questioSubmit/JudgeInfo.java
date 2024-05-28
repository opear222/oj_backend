package com.opear.oj.model.dto.questioSubmit;

import lombok.Data;

/**
 * 题目判题信息
 */
@Data
public class JudgeInfo {
    /**
     * 判题信息
     */
    private String message;

    /**
     * 消耗内存
     */
    private long memory;

    /**
     * 消耗时间
     */
    private long time;
}
