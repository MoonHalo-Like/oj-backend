package com.ntx.ojbackendmodel.model.dto.question;

import lombok.Data;

/**
 * @ClassName QuestionjudgeCase
 * @Author ntx
 * @Description 判题用例
 */
@Data
public class JudgeCase {
    /**
     * 输入用例
     */
    private String input;
    /**
     * 输出用例
     */
    private String output;
}
