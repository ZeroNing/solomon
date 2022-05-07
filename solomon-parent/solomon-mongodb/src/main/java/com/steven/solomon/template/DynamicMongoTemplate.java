package com.steven.solomon.template;

import com.mongodb.client.MongoDatabase;
import com.steven.solomon.context.MongoContext;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

public class DynamicMongoTemplate extends MongoTemplate {

  public DynamicMongoTemplate(MongoDatabaseFactory mongoDbFactory) {
    super(mongoDbFactory);
  }

  @Override
  protected MongoDatabase doGetDatabase() {
    MongoDatabaseFactory mongoDbFactory = MongoContext.getMongoDbFactory();
    return mongoDbFactory == null ? super.doGetDatabase() : mongoDbFactory.getMongoDatabase();
  }

  @Override
  public MongoDatabaseFactory getMongoDbFactory() {
    MongoDatabaseFactory mongoDbFactory = MongoContext.getMongoDbFactory();
    return mongoDbFactory == null ? super.getMongoDbFactory() : mongoDbFactory;
  }

}
