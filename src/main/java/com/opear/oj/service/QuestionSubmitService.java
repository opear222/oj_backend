package com.opear.oj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.opear.oj.model.dto.questioSubmit.QuestionSubmitAddRequest;
import com.opear.oj.model.dto.questioSubmit.QuestionSubmitQueryRequest;
import com.opear.oj.model.entity.QuestionSubmit;
import com.opear.oj.model.entity.User;
import com.opear.oj.model.vo.QuestionSubmitVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 帖子点赞服务
 *
 * @author <a href="https://github.com/liopear">程序员鱼皮</a>
 * @from <a href="https://opear.icu">编程导航知识星球</a>
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 点赞
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return 提交id
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);


    /**
     * 获取题目提交封装
     *
     * @param questionSubmit
     * @param request
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, HttpServletRequest request,User loginUser);

    /**
     * 分页获取题目提交封装
     *
     * @param questionSubmitPage
     * @param request
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, HttpServletRequest request,User loginUser);
}
