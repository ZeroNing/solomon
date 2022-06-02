package com.steven.solomon.config;

import com.mongodb.ReadPreference;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.steven.solomon.annotation.MongoDBCapped;
import com.steven.solomon.condition.MongoCondition;
import com.steven.solomon.enums.MongoModeEnum;
import com.steven.solomon.logger.LoggerUtils;
import com.steven.solomon.properties.MongoProfile;
import com.steven.solomon.properties.TenantMongoProperties;
import com.steven.solomon.spring.SpringUtil;
import com.steven.solomon.template.DynamicMongoTemplate;
import com.steven.solomon.verification.ValidateUtils;
import java.util.HashMap;
import org.bson.Document;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Order(2)
@DependsOn("springUtil")
public class MongoConfig {

    private Logger logger = LoggerUtils.logger(getClass());

    @Resource
    private MongoProperties mongoProperties;

    @Resource
    private MongoProfile mongoProfile;

    @PostConstruct
    public void afterPropertiesSet() {
        List<TenantMongoProperties>         mongoClients                          = new ArrayList<>();
        List<MongoClientPropertiesService> abstractMongoClientPropertiesServices = new ArrayList<>(SpringUtil.getBeansOfType(
                MongoClientPropertiesService.class).values());
        Map<String,SimpleMongoClientDatabaseFactory> factoryMap = new ConcurrentHashMap<>();
        if(ValidateUtils.isNotEmpty(mongoProfile) && MongoModeEnum.NORMAL.toString().equalsIgnoreCase(mongoProfile.getMode())){
            factoryMap.put("default", new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoProperties.getUri()),mongoProperties.getDatabase()));
        }

        abstractMongoClientPropertiesServices.forEach(service ->{
            service.setMongoClient();
            service.setCappedCollectionNameMap();
            mongoClients.addAll(MongoTenantsHandler.getProperties());
        });

        mongoClients.forEach(mongoProperties -> {
            SimpleMongoClientDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoProperties.getUri()),mongoProperties.getTenantCode());
            factoryMap.put(mongoProperties.getTenantCode(),factory);

            List<String> collectionNameList = new ArrayList<>();
            MongoDatabase mongoDatabase = factory.getMongoDatabase();
            mongoDatabase.listCollectionNames().forEach(name->{
                collectionNameList.add(name);
            });

            MongoTenantsHandler.getCappedCollectionNameMap().forEach((key,value)->{
                boolean isCreate = collectionNameList.contains(key);
                if(!isCreate){
                    MongoDBCapped mongoDBCapped = AnnotationUtils.getAnnotation(value, MongoDBCapped.class);
                    if(ValidateUtils.isNotEmpty(mongoDBCapped)){
                        mongoDatabase.createCollection(key,new CreateCollectionOptions().capped(true).maxDocuments(mongoDBCapped.maxDocuments()).sizeInBytes(mongoDBCapped.size()));
                    } else {
                        mongoDatabase.createCollection(key);
                    }
                } else {
                    MongoDBCapped mongoDBCapped = AnnotationUtils.getAnnotation(value, MongoDBCapped.class);
                    if(ValidateUtils.isNotEmpty(mongoDBCapped)){
                        Document command  = new Document("collStats", key);
                        Boolean  isCapped = mongoDatabase.runCommand(command, ReadPreference.primary()).getBoolean("capped");
                        if(!isCapped){
                            command = new Document("convertToCapped", key).append("maxSize", mongoDBCapped.size()).append("max",mongoDBCapped.maxDocuments()).append("capped",true);
                            mongoDatabase.runCommand(command, ReadPreference.primary());
                        }
                    }
                }
            });
        });
        MongoTenantsHandler.setFactoryMap(factoryMap);
    }

    @Bean(name = "mongoTemplate")
    @Conditional(value = MongoCondition.class)
    public DynamicMongoTemplate dynamicMongoTemplate() {
        return new DynamicMongoTemplate(MongoTenantsHandler.getFactoryMap().values().iterator().next());
    }

    @Bean(name = "mongoDbFactory")
    @Conditional(value = MongoCondition.class)
    public MongoDatabaseFactory mongoDbFactory() {
        return MongoTenantsHandler.getFactoryMap().values().iterator().next();
    }


}
