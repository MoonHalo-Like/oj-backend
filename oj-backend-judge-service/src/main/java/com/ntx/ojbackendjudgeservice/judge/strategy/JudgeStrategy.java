package com.ntx.ojbackendjudgeservice.judge.strategy;


import com.ntx.ojbackendmodel.model.dto.questionsubmit.JudgeInfo;

/**
 * @ClassName JudgeStrategy
 * @Author ntx
 * @Description 判题策略
 */
public interface JudgeStrategy {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */

    JudgeInfo doJudge(JudgeContext judgeContext);
}
