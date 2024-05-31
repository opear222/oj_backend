package com.opear.oj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.opear.oj.common.BaseResponse;
import com.opear.oj.common.ErrorCode;
import com.opear.oj.common.ResultUtils;
import com.opear.oj.exception.BusinessException;
import com.opear.oj.model.dto.questioSubmit.QuestionSubmitAddRequest;
import com.opear.oj.model.dto.questioSubmit.QuestionSubmitQueryRequest;
import com.opear.oj.model.entity.QuestionSubmit;
import com.opear.oj.model.entity.User;
import com.opear.oj.model.vo.QuestionSubmitVO;
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
@RequestMapping("/questionSubmit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
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
        // 登录才能提交
        final User loginUser = userService.getLoginUser(request);
//        long questionId = questionSubmitAddRequest.getQuestionId();
        long result = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success((int) result);
    }


    @PostMapping("/list")
        public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest, HttpServletRequest httpServletRequest) {
            long current = questionSubmitQueryRequest.getCurrent();
            long size = questionSubmitQueryRequest.getPageSize();
            Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                    questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
            User loginUser = userService.getLoginUser(httpServletRequest);
            return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage,httpServletRequest,loginUser));
    }
}
