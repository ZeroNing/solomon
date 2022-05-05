package com.steven.solomon.context;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.steven.solomon.spring.SpringUtil;
import com.steven.solomon.template.DynamicMongoTemplate;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MongoContext {

    private static final ThreadLocal<MongoClient> MONGO_DB_CLIENT_THREAD_LOCAL = new ThreadLocal<>();

    private static final  Map<String,MongoClient> MONGO_CLIENT_MAP = new ConcurrentHashMap<>();

    @PostConstruct
    public void afterPropertiesSet() {
        List<MongoProperties> mongoClients = new ArrayList<>();
        List<AbstractMongoClientService> mongoClientServices = new ArrayList<>(SpringUtil.getBeansOfType(AbstractMongoClientService.class).values());

        mongoClientServices.forEach(service ->{
            mongoClients.addAll(service.getMongoClientList());
        });

        mongoClients.forEach(mongoProperties -> {
            MongoClient mongoClient = MongoClients.create(mongoProperties.getUri());
            MONGO_CLIENT_MAP.put(mongoProperties.getDatabase(),mongoClient);
        });
    }

    @Bean(name = "mongoTemplate")
    public DynamicMongoTemplate dynamicMongoTemplate() {
        //Iterator<MongoClient> iterator = MONGO_CLIENT_MAP.values().iterator();
        return new DynamicMongoTemplate(MONGO_CLIENT_MAP.values().iterator().next(),MONGO_CLIENT_MAP.keySet().iterator().next());
    }

}
