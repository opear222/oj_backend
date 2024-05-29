package com.opear.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.opear.oj.common.ErrorCode;
import com.opear.oj.exception.BusinessException;
import com.opear.oj.mapper.QuestionSubmitMapper;
import com.opear.oj.model.dto.questioSubmit.QuestionSubmitAddRequest;
import com.opear.oj.model.entity.Question;
import com.opear.oj.model.entity.QuestionSubmit;
import com.opear.oj.model.entity.User;
import com.opear.oj.model.enums.QuestionSubmitLanguageEnum;
import com.opear.oj.model.enums.QuestionSubmitStatusEnum;
import com.opear.oj.service.QuestionService;
import com.opear.oj.service.QuestionSubmitService;
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
     * 点赞
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        Long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已点赞
        long userId = loginUser.getId();
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum enumByValue = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if(enumByValue == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "该语言当前不支持");
        }
        String code = questionSubmitAddRequest.getCode();

        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setLanguage(language);
        questionSubmit.setCode(code);
        questionSubmit.setJudgeInfo("{}");
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);

        boolean save = this.save(questionSubmit);
        if(!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "新建题目失败");
        }
        return questionSubmit.getId();
    }


}




