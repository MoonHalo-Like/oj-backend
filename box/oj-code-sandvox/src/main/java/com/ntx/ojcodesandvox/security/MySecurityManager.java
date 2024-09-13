package com.ntx.ojcodesandvox.security;

import java.security.Permission;

/**
 * @ClassName DefaultSecurityManager
 * @Author ntx
 * @Description Java安全管理器
 */
public class MySecurityManager extends SecurityManager {
    /**
     * 所有权限
     *
     * @param perm
     */
    @Override
    public void checkPermission(Permission perm) {
//        super.checkPermission(perm);
//        throw new SecurityException("权限异常" + perm.getActions());
    }

    /**
     * 检验执行命令
     *
     * @param cmd the specified system command.
     */
    @Override
    public void checkExec(String cmd) {
        throw new SecurityException("权限异常" + cmd);
    }

    /**
     * 是否可读
     *
     * @param file    the system-dependent filename.
     * @param context a system-dependent security context.
     */
    @Override
    public void checkRead(String file, Object context) {
        throw new SecurityException("权限异常" + file);
    }

    /**
     * 是否可写
     *
     * @param file the system-dependent filename.
     */
    @Override
    public void checkWrite(String file) {
        throw new SecurityException("权限异常" + file);
    }

    /**
     * 是否可删除
     *
     * @param file the system-dependent filename.
     */
    @Override
    public void checkDelete(String file) {
        throw new SecurityException("权限异常" + file);
    }

    /**
     * 是否可联网
     *
     * @param host the host name port to connect to.
     * @param port the protocol port to connect to.
     */
    @Override
    public void checkConnect(String host, int port) {
        throw new SecurityException("权限异常" + host + ":" + port);
    }
}
