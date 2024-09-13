package com.ntx.ojbackendjudgeservice.judge.codesandbox.impl;


import com.ntx.ojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.ntx.ojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.ntx.ojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * @ClassName RemoteCodeSandbox
 * @Author ntx
 * @Description 第三方代码沙箱
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        return null;
    }
}
