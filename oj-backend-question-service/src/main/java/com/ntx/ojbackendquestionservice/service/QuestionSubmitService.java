package com.ntx.ojbackendquestionservice.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ntx.ojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.ntx.ojbackendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.ntx.ojbackendmodel.model.entity.QuestionSubmit;
import com.ntx.ojbackendmodel.model.entity.User;
import com.ntx.ojbackendmodel.model.vo.QuestionSubmitVO;

import javax.servlet.http.HttpServletRequest;


/**
* @author n1072
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-08-09 15:27:58
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
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
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param request
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, HttpServletRequest request);
}
