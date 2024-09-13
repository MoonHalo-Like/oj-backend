package com.ntx.ojbackendjudgeservice.judge.strategy;


import com.ntx.ojbackendmodel.model.dto.question.JudgeCase;
import com.ntx.ojbackendmodel.model.dto.questionsubmit.JudgeInfo;
import com.ntx.ojbackendmodel.model.entity.Question;
import com.ntx.ojbackendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * @ClassName judgeContext
 * @Author ntx
 * @Description 上下文
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;
}
