package com.opear.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.opear.oj.model.entity.Question;
import com.opear.oj.service.QuestionService;
import com.opear.oj.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author 14998
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2024-05-28 16:49:46
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




