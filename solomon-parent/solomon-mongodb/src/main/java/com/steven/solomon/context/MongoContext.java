package com.steven.solomon.context;

import com.mongodb.client.MongoClients;
import com.steven.solomon.condition.MongoCondition;
import com.steven.solomon.logger.LoggerUtils;
import com.steven.solomon.properties.TenantMongoProperties;
import com.steven.solomon.spring.SpringUtil;
import com.steven.solomon.template.DynamicMongoTemplate;
import com.steven.solomon.verification.ValidateUtils;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    public static void setMongoFactoryMap(Map<String, SimpleMongoClientDatabaseFactory> mongoFactoryMap){
        MongoContext.MONGO_FACTORY_MAP.putAll(mongoFactoryMap);
    }

    public static Map<String,SimpleMongoClientDatabaseFactory> getMongoFactoryMap(){
        return MongoContext.MONGO_FACTORY_MAP;
    }

    @PostConstruct
    public void afterPropertiesSet() {
        List<TenantMongoProperties>         mongoClients                          = new ArrayList<>();
        List<AbstractMongoClientProperties> abstractMongoClientPropertiesServices = new ArrayList<>(SpringUtil.getBeansOfType(
            AbstractMongoClientProperties.class).values());
        if(ValidateUtils.isEmpty(abstractMongoClientPropertiesServices)){
            logger.info("不存在需要的用到的多租户mongodb配置");
            return;
        }

        abstractMongoClientPropertiesServices.forEach(service ->{
            service.setMongoClient();
            mongoClients.addAll(service.getMongoClientList());
        });

        mongoClients.forEach(mongoProperties -> {
            com.mongodb.client.MongoClient   mongoClient = MongoClients.create(mongoProperties.getUri());
            SimpleMongoClientDatabaseFactory factory     = new SimpleMongoClientDatabaseFactory(mongoClient,mongoProperties.getTenantCode());
            MONGO_FACTORY_MAP.put(mongoProperties.getTenantCode(),factory);
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
