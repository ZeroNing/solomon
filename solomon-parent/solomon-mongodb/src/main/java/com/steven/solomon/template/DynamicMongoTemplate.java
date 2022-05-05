package com.steven.solomon.template;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.steven.solomon.context.MongoContext;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

public class DynamicMongoTemplate extends MongoTemplate {

    public DynamicMongoTemplate(MongoClient mongoClient, String databaseName) {
        super(mongoClient, databaseName);
    }

    @Override
    protected MongoDatabase doGetDatabase() {
        MongoDbFactory mongoDbFactory = MongoContext.getMongoDbFactory();
        return mongoDbFactory == null ? super.doGetDatabase() : mongoDbFactory.getDb();
        return null;
    }

}
