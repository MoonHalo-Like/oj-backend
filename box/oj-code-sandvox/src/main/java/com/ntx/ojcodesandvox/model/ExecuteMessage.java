package com.ntx.ojcodesandvox.model;

import lombok.Data;

/**
 * @ClassName ExecuteMessage
 * @Author ntx
 * @Description 进程执行信息
 */
@Data
public class ExecuteMessage {

    private Integer exitValue;

    private String message;

    private String errorMessage;

    private Long time;

    private Long memory;
}
