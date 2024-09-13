package com.ntx.ojbackendmodel.model.codesandbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName ExecuteCodeRequest
 * @Author ntx
 * @Description 请求体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteCodeRequest {
    /**
     * 输入列表
     */
    private List<String> inputList;
    /**
     * 代码
     */
    private String code;
    /**
     * 编程语言
     */
    private String language;
}
