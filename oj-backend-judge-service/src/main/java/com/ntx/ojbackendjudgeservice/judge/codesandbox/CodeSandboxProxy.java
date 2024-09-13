package com.ntx.ojbackendjudgeservice.judge.codesandbox;


import com.ntx.ojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.ntx.ojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName CodeSandboxProxy
 * @Author ntx
 * @Description 沙箱代理类
 */
@Slf4j
@AllArgsConstructor
public class CodeSandboxProxy implements CodeSandbox {

    private CodeSandbox codeSandbox;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：{}", executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息：{}", executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
