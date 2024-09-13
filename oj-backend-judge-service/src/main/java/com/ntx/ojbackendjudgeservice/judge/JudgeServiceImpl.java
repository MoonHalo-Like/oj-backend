package com.ntx.ojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.ntx.ojbackendcommon.common.ErrorCode;
import com.ntx.ojbackendcommon.exception.BusinessException;
import com.ntx.ojbackendjudgeservice.judge.codesandbox.CodeSandBoxFactory;
import com.ntx.ojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.ntx.ojbackendjudgeservice.judge.codesandbox.CodeSandboxProxy;
import com.ntx.ojbackendjudgeservice.judge.strategy.JudgeContext;
import com.ntx.ojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.ntx.ojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.ntx.ojbackendmodel.model.dto.question.JudgeCase;
import com.ntx.ojbackendmodel.model.dto.questionsubmit.JudgeInfo;
import com.ntx.ojbackendmodel.model.entity.Question;
import com.ntx.ojbackendmodel.model.entity.QuestionSubmit;
import com.ntx.ojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.ntx.ojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.ntx.ojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName JudgeServiceImpl
 * @Author ntx
 * @Description 判题服务实现类
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionFeignClient questionFeignClient;
    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //获取题目信息
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        //判断题目状态
        Integer status = questionSubmit.getStatus();
        if (!status.equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        //更改题目状态，防止重复提交
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionFeignClient.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目状态更新错误");
        }
        //调用代码沙箱，获取执行结果
        CodeSandbox codeSandbox = CodeSandBoxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .inputList(inputList)
                .language(language)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        //根据结果，判断信息是否正确
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);

        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);

        //更改题目状态
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        if (judgeInfo.getMessage().equals(JudgeInfoMessageEnum.ACCEPTED.getValue())){
            questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        }else {
            questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.FAILED.getValue());
        }
        update = questionFeignClient.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目状态更新错误");
        }
        questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        return questionSubmit;
    }
}
