package com.opear.oj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题信息消息枚举
 *
 * @author <a href="https://github.com/liopear">程序员鱼皮</a>
 * @from <a href="https://opear.icu">编程导航知识星球</a>
 */
public enum JudgeInfoMessageEnum {

//    提交状态 0-java 1-cpp, 2-python
    ACCEPTED ("成功", "Accepted"),
    WRONG ("答案错误", "Wrong"),
    COMPILE ("编译错误", "Compile"),
    MEMORY_LIMIT_EXCEEDED ("内存溢出", "MemoryLimitExceeded"),
    TIME_LIMIT_EXCEEDED ("超时", "TimeLimitExceeded"),
    PRESENTATION_ERROR ("展示错误", "PresentationError"),
    OUTPUT_LIMIT_EXCEEDED ("输出溢出", "output limit exceeded"),
    WAITING ("等待中", "Waiting"),
    DANGEROUS_OPERATION ("危险操作", "Dangerous Operation"),
    RUNTIME_ERROR ("用户程序的问题", "Runtime Error"),
    SYSTEM_ERROR ("做系统人的问题", "System Error");
    private final String text;

    private final String value;

    JudgeInfoMessageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static JudgeInfoMessageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeInfoMessageEnum anEnum : JudgeInfoMessageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
