package com.steven.solomon.context;

import com.mongodb.client.MongoClients;
import com.steven.solomon.condition.MongoCondition;
import com.steven.solomon.enums.MongoModeEnum;
import com.steven.solomon.logger.LoggerUtils;
import com.steven.solomon.properties.MongoProfile;
import com.steven.solomon.properties.TenantMongoProperties;
import com.steven.solomon.spring.SpringUtil;
import com.steven.solomon.template.DynamicMongoTemplate;
import com.steven.solomon.verification.ValidateUtils;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.DependsOn;
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
public class MongoContext {

    private Logger logger = LoggerUtils.logger(getClass());

    private static final ThreadLocal<SimpleMongoClientDatabaseFactory> MONGO_DB_FACTORY_THREAD_LOCAL = new ThreadLocal<>();

    private static final  Map<String,SimpleMongoClientDatabaseFactory> MONGO_FACTORY_MAP = new ConcurrentHashMap<>();

    private static List<TenantMongoProperties> mongoClientList = new ArrayList<>();

    @Resource
    private MongoProperties mongoProperties;

    @Resource
    private MongoProfile mongoProfile;

    public static List<TenantMongoProperties> getMongoClientList(){
        return mongoClientList;
    }

    public static void setMongoClient(TenantMongoProperties properties){
        MongoContext.mongoClientList.add(properties);
    };

    public static void setMongoFactoryMap(Map<String, SimpleMongoClientDatabaseFactory> mongoFactoryMap){
        MongoContext.MONGO_FACTORY_MAP.putAll(mongoFactoryMap);
    }

    public static Map<String,SimpleMongoClientDatabaseFactory> getMongoFactoryMap(){
        return MongoContext.MONGO_FACTORY_MAP;
    }

    @PostConstruct
    public void afterPropertiesSet() {
        List<TenantMongoProperties>         mongoClients                          = new ArrayList<>();
        List<MongoClientPropertiesService> abstractMongoClientPropertiesServices = new ArrayList<>(SpringUtil.getBeansOfType(
                MongoClientPropertiesService.class).values());

        if(ValidateUtils.isNotEmpty(mongoProfile) && MongoModeEnum.NORMAL.toString().equalsIgnoreCase(mongoProfile.getMode())){
            MONGO_FACTORY_MAP.put("default", new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoProperties.getUri()),mongoProperties.getDatabase()));
        }

        abstractMongoClientPropertiesServices.forEach(service ->{
            service.setMongoClient();
            mongoClients.addAll(MongoContext.mongoClientList);
        });

        mongoClients.forEach(mongoProperties -> {
            MONGO_FACTORY_MAP.put(mongoProperties.getTenantCode(),new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoProperties.getUri()),mongoProperties.getTenantCode()));
        });
    }

    @Bean(name = "mongoTemplate")
    @Conditional(value = MongoCondition.class)
    public DynamicMongoTemplate dynamicMongoTemplate() {
        return new DynamicMongoTemplate(MONGO_FACTORY_MAP.values().iterator().next());
    }

    @Bean(name = "mongoDbFactory")
    @Conditional(value = MongoCondition.class)
    public MongoDatabaseFactory mongoDbFactory() {
        return MONGO_FACTORY_MAP.values().iterator().next();
    }

    public static void setMongoDbFactory(String name) {
        MONGO_DB_FACTORY_THREAD_LOCAL.set(MONGO_FACTORY_MAP.get(name));
    }

    public static MongoDatabaseFactory getMongoDbFactory() {
        return MONGO_DB_FACTORY_THREAD_LOCAL.get();
    }

    public static void removeMongoDbFactory(){
        MONGO_DB_FACTORY_THREAD_LOCAL.remove();
    }

}
