package com.ntx.ojbackendjudgeservice.judge.codesandbox;


import com.ntx.ojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.ntx.ojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * @ClassName CodeSandbox
 * @Author ntx
 * @Description 代码沙箱接口定义
 */
public interface CodeSandbox {
    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
