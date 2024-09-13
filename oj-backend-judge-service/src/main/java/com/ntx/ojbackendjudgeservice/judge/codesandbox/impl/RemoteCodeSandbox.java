package com.ntx.ojbackendjudgeservice.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.ntx.ojbackendcommon.common.ErrorCode;
import com.ntx.ojbackendcommon.exception.BusinessException;
import com.ntx.ojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.ntx.ojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.ntx.ojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName RemoteCodeSandbox
 * @Author ntx
 * @Description 远程代码沙箱
 */
public class RemoteCodeSandbox implements CodeSandbox {
    //鉴权
    private static final String AUTH_REQUEST_HEADER = "auth";
    private static final String AUTH_REQUEST_SECRET = "secretKet";
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://localhost:8082/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER,AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "远程代码沙箱服务错误,错误信息：" + responseStr);
        }

        return JSONUtil.toBean(responseStr,ExecuteCodeResponse.class);
    }
}
