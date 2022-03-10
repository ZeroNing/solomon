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

@Activate(group = {CommonConstants.CONSUMER}, order = -30000)
public class TestConsumerFilter implements Filter {

  private Logger logger = LoggerUtils.logger(getClass());

  @Override
  public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    logger.info("此时为消费者调用接口");
    return invoker.invoke(invocation);
  }
}
