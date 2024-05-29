package com.opear.oj.service;

import com.opear.oj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.opear.oj.model.entity.User;

/**
* @author 14998
* @description 针对表【question_submit(题目提交表)】的数据库操作Service
* @createDate 2024-05-28 16:49:58
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 提交题目
     *
     * @param questionId
     * @param loginUser
     * @return
     */
    int doQuestionSubmit(long questionId, User loginUser);
}
