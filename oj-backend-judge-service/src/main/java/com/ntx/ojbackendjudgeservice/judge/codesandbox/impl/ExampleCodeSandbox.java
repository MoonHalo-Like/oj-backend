package com.ntx.ojbackendjudgeservice.judge.codesandbox.impl;


import com.ntx.ojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.ntx.ojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.ntx.ojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.ntx.ojbackendmodel.model.dto.questionsubmit.JudgeInfo;
import com.ntx.ojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.ntx.ojbackendmodel.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * @ClassName ExampleCodeSandbox
 * @Author ntx
 * @Description 示例代码沙箱
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);


        return executeCodeResponse;
    }
}
