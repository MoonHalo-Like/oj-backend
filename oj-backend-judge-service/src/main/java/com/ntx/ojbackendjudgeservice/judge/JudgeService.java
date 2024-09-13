package com.ntx.ojbackendjudgeservice.judge;


import com.ntx.ojbackendmodel.model.entity.QuestionSubmit;

/**
 * @ClassName JudgeService
 * @Author ntx
 * @Description 判题服务
 */
public interface JudgeService {
    /**
     * 判题服务
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
