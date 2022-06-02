package com.steven.solomon.config;

import com.steven.solomon.handler.AbstractTenantsHandler;
import com.steven.solomon.properties.TenantMongoProperties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoTenantsHandler extends AbstractTenantsHandler<SimpleMongoClientDatabaseFactory,TenantMongoProperties> {

  private static final ThreadLocal<SimpleMongoClientDatabaseFactory> MONGO_DB_FACTORY_THREAD_LOCAL = new ThreadLocal<>();

  private static final Map<String,SimpleMongoClientDatabaseFactory> MONGO_FACTORY_MAP = new ConcurrentHashMap<>();

  private static List<TenantMongoProperties> mongoClientList = new ArrayList<>();

  private static Map<String,Class<?>> CAPPED_COLLECTION_NAME_MAP = new HashMap<>();


  public static void setCappedCollectionNameMap(Map<String,Class<?>> cappedCollectionNameMap){
    MongoTenantsHandler.CAPPED_COLLECTION_NAME_MAP.putAll(cappedCollectionNameMap);
  }

  public static Map<String,Class<?>> getCappedCollectionNameMap(){
    return MongoTenantsHandler.CAPPED_COLLECTION_NAME_MAP;
  }

  @Override
  public SimpleMongoClientDatabaseFactory getFactory() {
    return MONGO_DB_FACTORY_THREAD_LOCAL.get();
  }

  @Override
  public void removeFactory() {
    MONGO_DB_FACTORY_THREAD_LOCAL.remove();
  }

  @Override
  public void setFactory(String name) {
    MONGO_DB_FACTORY_THREAD_LOCAL.set(MONGO_FACTORY_MAP.get(name));
  }

  @Override
  public void setProperties(TenantMongoProperties properties) {
    MongoTenantsHandler.mongoClientList.add(properties);
  }

  @Override
  public List<TenantMongoProperties> getProperties() {
    return MongoTenantsHandler.mongoClientList;
  }

  @Override
  public Map<String, SimpleMongoClientDatabaseFactory> getFactoryMap() {
    return MongoTenantsHandler.MONGO_FACTORY_MAP;
  }

  @Override
  public void setFactoryMap(Map<String, SimpleMongoClientDatabaseFactory> factoryMap) {
    MongoTenantsHandler.MONGO_FACTORY_MAP.putAll(factoryMap);
  }

}
