package com.ntx.ojbackendjudgeservice.judge;


import com.ntx.ojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.ntx.ojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.ntx.ojbackendjudgeservice.judge.strategy.JudgeContext;
import com.ntx.ojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.ntx.ojbackendmodel.model.dto.questionsubmit.JudgeInfo;
import org.springframework.stereotype.Service;

/**
 * @ClassName JudgeManager
 * @Author ntx
 * @Description 判题管理器
 */
@Service
public class JudgeManager {
    JudgeInfo doJudge(JudgeContext judgeContext){
        String language = judgeContext.getQuestionSubmit().getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)){
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
