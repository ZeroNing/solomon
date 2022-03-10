package com.steven.solomon.filter;

import com.steven.solomon.utils.logger.LoggerUtils;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.Logger;

@Activate(group = {CommonConstants.PROVIDER}, order = -30000)
public class TestProviderFilter implements Filter {

  private Logger logger = LoggerUtils.logger(getClass());

  @Override
  public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    logger.info("此时为服务者提供接口");
    return invoker.invoke(invocation);
  }

}
