package com.opear.oj.model.dto.questioSubmit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 帖子点赞请求
 *
 * @author <a href="https://github.com/liopear">程序员鱼皮</a>
 * @from <a href="https://opear.icu">编程导航知识星球</a>
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {


    /**
     * 编程语言
     */
    private String language;

    /**
     * 代码
     */
    private String code;

    /**
     * 问题 id
     */
    private Long questionId;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}