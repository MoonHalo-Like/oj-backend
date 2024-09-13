package com.ntx.ojbackendquestionservice.controller.inner;

import com.ntx.ojbackendmodel.model.entity.Question;
import com.ntx.ojbackendmodel.model.entity.QuestionSubmit;
import com.ntx.ojbackendquestionservice.service.QuestionService;
import com.ntx.ojbackendquestionservice.service.QuestionSubmitService;
import com.ntx.ojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName QuestionInnerCollertion
 * @Author ntx
 * 内部调用
 */
@RestController
@RequestMapping("/inner")
public class QuestionInnerCollertion implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    /**
     * 根据id获取题目
     *
     * @param questionId
     * @return
     */
    @Override
    @GetMapping("/get/id")
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    /**
     * 根据id获取提交信息
     *
     * @param questionSubmitId
     * @return
     */
    @GetMapping("/question_submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    /**
     * 更新提交信息
     *
     * @param questionSubmit
     * @return
     */
    @PostMapping("/question_submit/update")
    @Override
    public Boolean updateById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }

}
