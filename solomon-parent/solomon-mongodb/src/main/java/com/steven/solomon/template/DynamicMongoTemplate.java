package com.steven.solomon.template;

import com.mongodb.ReadPreference;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.steven.solomon.annotation.MongoDBCapped;
import com.steven.solomon.config.MongoConfig;
import java.util.Map;
import org.bson.Document;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

public class DynamicMongoTemplate extends MongoTemplate {

  public DynamicMongoTemplate(MongoDatabaseFactory mongoDbFactory) {
    super(mongoDbFactory);
  }

  @Override
  protected MongoDatabase doGetDatabase() {
    MongoDatabaseFactory mongoDbFactory = MongoConfig.getMongoDbFactory();
    return mongoDbFactory == null ? super.doGetDatabase() : mongoDbFactory.getMongoDatabase();
  }

  @Override
  public MongoDatabaseFactory getMongoDbFactory() {
    MongoDatabaseFactory mongoDbFactory = MongoConfig.getMongoDbFactory();
    return mongoDbFactory == null ? super.getMongoDbFactory() : mongoDbFactory;
  }

  @Override
  protected MongoCollection<Document> doCreateCollection(String collectionName, Document collectionOptions) {
    MongoCollection<Document> createCollection = super.doCreateCollection(collectionName, collectionOptions);

    Map<String,Class<?>> cappedCollectionNameMap = MongoConfig.getCappedCollectionNameMap();
    if(cappedCollectionNameMap.containsKey(collectionName)){
      Document command = new Document("collStats", collectionName);
      Boolean isCapped = this.doGetDatabase().runCommand(command, ReadPreference.primary()).getBoolean("capped");
      if (!isCapped) {
        Class<?> clazz = cappedCollectionNameMap.get(collectionName);
        MongoDBCapped mongoDBCapped = AnnotationUtils.getAnnotation(clazz, MongoDBCapped.class);
        command = new Document("convertToCapped", collectionName).append("maxSize", mongoDBCapped.size()).append("max",mongoDBCapped.maxDocuments()).append("capped",true);
        this.doGetDatabase().runCommand(command, ReadPreference.primary());
      }
    }
    return createCollection;
  }
}
