package com.opear.oj.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.opear.oj.common.ErrorCode;
import com.opear.oj.constant.CommonConstant;
import com.opear.oj.exception.BusinessException;
import com.opear.oj.mapper.QuestionSubmitMapper;
import com.opear.oj.model.dto.questioSubmit.JudgeInfo;
import com.opear.oj.model.dto.questioSubmit.QuestionSubmitAddRequest;
import com.opear.oj.model.dto.questioSubmit.QuestionSubmitQueryRequest;
import com.opear.oj.model.entity.Question;
import com.opear.oj.model.entity.QuestionSubmit;
import com.opear.oj.model.entity.User;
import com.opear.oj.model.enums.QuestionSubmitLanguageEnum;
import com.opear.oj.model.enums.QuestionSubmitStatusEnum;
import com.opear.oj.model.enums.UserRoleEnum;
import com.opear.oj.model.vo.QuestionSubmitVO;
import com.opear.oj.model.vo.QuestionVO;
import com.opear.oj.model.vo.UserVO;
import com.opear.oj.service.QuestionService;
import com.opear.oj.service.QuestionSubmitService;
import com.opear.oj.service.UserService;
import com.opear.oj.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private UserService userService;


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

    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        Long id = questionSubmitQueryRequest.getId();
        String language = questionSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long userId = questionSubmitQueryRequest.getUserId();


//        int current = questionSubmitQueryRequest.getCurrent();
//        int pageSize = questionSubmitQueryRequest.getPageSize();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();


        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);

        queryWrapper.like(QuestionSubmitLanguageEnum.getEnumByValue(language)!= null, "language", language);
        queryWrapper.like(QuestionSubmitStatusEnum.getEnumByValue(status)!= null, "status", status);

        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }


    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, HttpServletRequest request, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        // 1. 关联查询用户信息
        Long userId = questionSubmit.getUserId();

        //仅本人和管理员用户可以查看提交的代码
        if(!loginUser.getId().equals(userId) && !loginUser.getUserRole().equals(UserRoleEnum.ADMIN.getValue())){
            questionSubmitVO.setJudgeInfo(new JudgeInfo());
            questionSubmitVO.setCode("");
        }
        UserVO userVO = null;
        if (userId != null && userId > 0) {
            User user = userService.getById(userId);
            userVO = userService.getUserVO(user);
        }
        questionSubmitVO.setUserVO(userVO);
        // 2. 关联查询问题信息
        Long questionId = questionSubmit.getQuestionId();
        QuestionVO questionVO = null;
        if (userId != null && userId > 0) {
            Question question = questionService.getById(questionId);
            questionVO = questionService.getQuestionVO(question,request);
        }
        questionSubmitVO.setQuestionVO(questionVO);

        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, HttpServletRequest request, User loginUser) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollUtil.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        List<QuestionSubmitVO> collect = questionSubmitList.stream().map(questionSubmit -> {
            return getQuestionSubmitVO(questionSubmit, request, loginUser);
        }).collect(Collectors.toList());
        questionSubmitVOPage.setRecords(collect);
        return questionSubmitVOPage;
    }


}




