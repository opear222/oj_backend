package com.opear.oj.model.dto.questioSubmit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.opear.oj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author <a href="https://github.com/liopear">程序员鱼皮</a>
 * @from <a href="https://opear.icu">编程导航知识星球</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

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







    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}