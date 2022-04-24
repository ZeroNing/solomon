package com.steven.solomon.sentence;

import com.steven.solomon.verification.ValidateUtils;
import org.bson.Document;

public class MongoDbSentence {

  /**
   * 转换固定集合语句
   * @param collectionName 集合名称
   * @param max max则表示集合中文档的最大数量
   * @param size 集合的大小，单位为kb
   */
  public static Document convertToCapped(String collectionName,long max,long size){
    if(ValidateUtils.isEmpty(collectionName)){
      return null;
    }
    Document doc = new Document();
    doc.put("convertToCapped",collectionName);
    doc.put("size",size);
    doc.put("max",max);
    return doc;
  }
}
