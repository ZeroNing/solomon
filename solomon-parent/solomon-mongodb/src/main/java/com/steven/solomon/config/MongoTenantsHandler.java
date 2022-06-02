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

public class MongoTenantsHandler {

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

  public static  SimpleMongoClientDatabaseFactory getFactory() {
    return MONGO_DB_FACTORY_THREAD_LOCAL.get();
  }

  public static  void removeFactory() {
    MONGO_DB_FACTORY_THREAD_LOCAL.remove();
  }

  public static  void setFactory(String name) {
    MONGO_DB_FACTORY_THREAD_LOCAL.set(MONGO_FACTORY_MAP.get(name));
  }

  
  public static  void setProperties(TenantMongoProperties properties) {
    MongoTenantsHandler.mongoClientList.add(properties);
  }

  public static  List<TenantMongoProperties> getProperties() {
    return MongoTenantsHandler.mongoClientList;
  }

  public static  Map<String, SimpleMongoClientDatabaseFactory> getFactoryMap() {
    return MongoTenantsHandler.MONGO_FACTORY_MAP;
  }

  public static  void setFactoryMap(Map<String, SimpleMongoClientDatabaseFactory> factoryMap) {
    MongoTenantsHandler.MONGO_FACTORY_MAP.putAll(factoryMap);
  }

}
