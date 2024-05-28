package com.opear.oj.model.dto.question;

import lombok.Data;

/**
 * 判题用例
 */
@Data
public class JudgeConfig {

    /**
     * 时间限制 ms
     */
    private long timeLimit;

    /**
     * 内存限制 KB
     */
    private long memoryLimit;


    /**
     * 堆栈限制 KN
     */
    private long stackLimit;
}
