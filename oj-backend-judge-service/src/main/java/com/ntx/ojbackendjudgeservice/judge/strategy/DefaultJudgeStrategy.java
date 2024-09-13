package com.ntx.ojbackendjudgeservice.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.ntx.ojbackendmodel.model.dto.question.JudgeCase;
import com.ntx.ojbackendmodel.model.dto.question.JudgeConfig;
import com.ntx.ojbackendmodel.model.dto.questionsubmit.JudgeInfo;
import com.ntx.ojbackendmodel.model.entity.Question;
import com.ntx.ojbackendmodel.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * @ClassName DefaultJudgeStrategy
 * @Author ntx
 * @Description 默认判题策略
 */
public class DefaultJudgeStrategy implements JudgeStrategy {

    /**
     * 默认判题策略
     *
     * @param judgeContext
     * @return
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        Question question = judgeContext.getQuestion();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);


        //判断输入和输出结果数量是否相等
        if (outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!judgeCase.getOutput().equals(outputList.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        //判断题目限制

        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        if (!memory.equals(judgeConfig.getMemoryLimit())) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        if (time > judgeConfig.getTimeLimit()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }

        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }
}
