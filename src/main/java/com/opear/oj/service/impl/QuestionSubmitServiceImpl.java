package com.opear.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.opear.oj.common.ErrorCode;
import com.opear.oj.exception.BusinessException;
import com.opear.oj.model.entity.Question;
import com.opear.oj.model.entity.QuestionSubmit;
import com.opear.oj.model.entity.User;
import com.opear.oj.service.QuestionFavourService;
import com.opear.oj.service.QuestionService;
import com.opear.oj.service.QuestionSubmitService;
import com.opear.oj.mapper.QuestionSubmitMapper;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 14998
* @description 针对表【question_submit(题目提交表)】的数据库操作Service实现
* @createDate 2024-05-28 16:49:58
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{
    @Resource
    private QuestionService questionService;

    /**
     * 用户提交题目
     *
     * @param questionId
     * @param loginUser
     * @return
     */
    @Override
    public int doQuestionSubmit(long questionId, User loginUser) {
        // 判断是否存在
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已用户提交
        long userId = loginUser.getId();
        // 每个用户串行用户提交
        // 锁必须要包裹住事务方法
        QuestionSubmitService questionSubmitService = (QuestionSubmitService) AopContext.currentProxy();
        synchronized (String.valueOf(userId).intern()) {
            return questionSubmitService.doQuestionSubmit(userId, questionId);
        }
    }
}




