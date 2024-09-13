package com.ntx.ojbackendjudgeservice.judge.codesandbox;


import com.ntx.ojbackendjudgeservice.judge.codesandbox.impl.ExampleCodeSandbox;
import com.ntx.ojbackendjudgeservice.judge.codesandbox.impl.RemoteCodeSandbox;
import com.ntx.ojbackendjudgeservice.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * @ClassName CodeSandBoxFactory
 * @Author ntx
 * @Description 代码沙箱工厂（根据字符串创建不同代码沙箱实例）
 */
public class CodeSandBoxFactory {
    /**
     * 创建代码沙箱示例
     *
     * @param type
     * @return
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
