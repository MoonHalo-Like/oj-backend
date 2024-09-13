package com.ntx.ojbackendserviceclient.service;

import com.ntx.ojbackendmodel.model.entity.Question;
import com.ntx.ojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author n1072
 * @description 针对表【question(题目)】的数据库操作Service
 * @createDate 2024-08-09 15:27:48
 */
@FeignClient(name = "oj-backend-question-service", path = "/api/question/inner")
public interface QuestionFeignClient{

    /**
     * 根据id获取题目
     *
     * @param questionId
     * @return
     */
    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    /**
     * 根据id获取提交信息
     *
     * @param questionSubmitId
     * @return
     */
    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId);

    /**
     * 更新提交信息
     *
     * @param questionSubmit
     * @return
     */
    @PostMapping("/question_submit/update")
    Boolean updateById(@RequestBody QuestionSubmit questionSubmit);

}
