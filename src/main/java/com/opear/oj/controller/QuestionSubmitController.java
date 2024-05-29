package com.opear.oj.controller;

import com.opear.oj.common.BaseResponse;
import com.opear.oj.common.ErrorCode;
import com.opear.oj.common.ResultUtils;
import com.opear.oj.exception.BusinessException;
import com.opear.oj.model.dto.postthumb.QuestionSubmitAddRequest;
import com.opear.oj.model.dto.questioSubmit.QuestionSubmitAddRequest;
import com.opear.oj.model.entity.User;
import com.opear.oj.service.QuestionSubmitService;
import com.opear.oj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 * @author <a href="https://github.com/liopear">程序员鱼皮</a>
 * @from <a href="https://opear.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/post_thumb")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 点赞 / 取消点赞
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public BaseResponse<Integer> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
            HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long questionId = questionSubmitAddRequest.getQuestionId();
        int result = questionSubmitService.doQuestionSubmit(questionId, loginUser);
        return ResultUtils.success(result);
    }

}
