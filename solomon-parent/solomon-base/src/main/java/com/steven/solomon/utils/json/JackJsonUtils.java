package com.steven.solomon.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.steven.solomon.utils.spring.SpringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JackJsonUtils {

  private static final ObjectMapper mapper = SpringUtil.getBean(ObjectMapper.class);

  /**
   * 转换对象
   */
  public static <T> T conversionClass(String json, Class<T> t) throws IOException {
    return mapper.readValue(json, t);
  }

  /**
   * 转换数组对象
   */
  public static <T> List<T> conversionClassList(String json, Class<T> clazz) throws IOException {
    //忽略多余属性
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
    return mapper.readValue(json, listType);
  }

  /**
   * 转换json
   *
   * @param result
   * @return
   * @throws JsonProcessingException
   */
  public static String formatJsonByFilter(Object result) throws JsonProcessingException {
    return formatJsonByFilter(result, null);
  }


  /**
   * 转换json
   *
   * @param result
   * @param filter
   * @return
   * @throws JsonProcessingException
   */
  public static String formatJsonByFilter(Object result, Class<?> filter) throws JsonProcessingException {
    return filter == null ? mapper.writeValueAsString(result) : mapper.writerWithView(filter).writeValueAsString(result);
  }

}
