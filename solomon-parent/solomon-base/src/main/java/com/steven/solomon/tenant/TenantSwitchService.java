package com.steven.solomon.tenant;

import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 切换数据库接口,实现交由每个服务实现
 */
public interface TenantSwitchService {
    /**
     * dubbo切换租户数据库
     */
    Result dubboTenantSwitch(Invoker<?> invoker, Invocation invocation);

    /**
     * spring AOP切面切换租户数据库
     */
    Object springAopTenantSwitch(ProceedingJoinPoint point);

    /**
     * 保存dubbo隐形传参
     */
    void saveDubboAttachment();
}
