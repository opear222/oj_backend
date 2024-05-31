package com.opear.oj.model.dto.questioSubmit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
public class QuestionSubmitQueryRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;


    /**
     * 提交状态 0-带判题 1-判题中 2-成功 3-失败
     */
    private Integer status;

    /**
     * 提交用户 id
     */
    private Long userId;

    /**
     * 问题 id
     */
    private Long questionId;





}