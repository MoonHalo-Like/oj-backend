package com.ntx.ojcodesandvox;


import com.ntx.ojcodesandvox.model.ExecuteCodeRequest;
import com.ntx.ojcodesandvox.model.ExecuteCodeResponse;

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
