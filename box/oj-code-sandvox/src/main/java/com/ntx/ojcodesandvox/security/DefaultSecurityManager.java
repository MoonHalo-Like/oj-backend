package com.ntx.ojcodesandvox.security;

import java.security.Permission;

/**
 * @ClassName DefaultSecurityManager
 * @Author ntx
 * @Description Java安全管理器
 */
public class DefaultSecurityManager extends SecurityManager {
    @Override
    public void checkPermission(Permission perm) {
        System.out.println("默认不做任何限制");
//        super.checkPermission(perm);
//        throw new SecurityException("权限异常" + perm.getActions());
    }

}
