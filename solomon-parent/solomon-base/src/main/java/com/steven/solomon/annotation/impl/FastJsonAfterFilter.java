package com.steven.solomon.annotation.impl;

import com.alibaba.fastjson.serializer.AfterFilter;
import com.steven.solomon.annotation.JsonEnum;
import com.steven.solomon.utils.enums.EnumUtils;
import com.steven.solomon.utils.logger.LoggerUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;

/**
 * fastJson转换枚举值到json内
 */
public class FastJsonAfterFilter extends AfterFilter {

  private Logger logger = LoggerUtils.logger(getClass());

  @Override
  public void writeAfter(Object o) {
    Class clazz = o.getClass();
    List<Field> fields = new ArrayList<>();
    Arrays.asList(o.getClass().getDeclaredFields()).forEach(field -> {
      Optional.ofNullable(field).filter(a -> a.isAnnotationPresent(JsonEnum.class)).ifPresent(a -> fields.add(a));
    });
    if(ValidateUtils.isEmpty(fields)){
      return;
    }
    for (Field field : fields) {
      try {
        JsonEnum fastJsonEnum = field.getAnnotation(JsonEnum.class);
        Method   method       = clazz.getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
        Class<? extends Enum> enumClass = fastJsonEnum.enumClass();
        String enumValue = (String) method.invoke(o);
        Enum enums = EnumUtils.codeOf(enumClass, enumValue);
        if(ValidateUtils.isEmpty(enums)){
          logger.info("fastJson 转换枚举为空,值:{},类名:{}",enumValue,enumClass.getName());
          continue;
        }
        String[] methodNames = fastJsonEnum.methodNames();
        String prefix = field.getName();

        for(String  methodName: methodNames){
          String value    = (String) enumClass.getMethod(methodName).invoke(enums);
          super.writeKeyValue(new StringBuilder(prefix).append(methodName).toString(), value);
        }
      } catch (Exception e) {
        logger.info("fastJson 转义注解失败,失败异常为 e:{}",e);
//        throw new BaseException(BaseExceptionCode.BASE_EXCEPTION_CODE);
      }
    }
  }
}
