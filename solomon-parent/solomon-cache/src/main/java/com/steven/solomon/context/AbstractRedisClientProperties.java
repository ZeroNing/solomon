package com.steven.solomon.context;

import com.steven.solomon.profile.TenantRedisProperties;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRedisClientProperties implements RedisClientPropertiesService{

    private static List<TenantRedisProperties> redisPropertiesList = new ArrayList<>();

    public List<TenantRedisProperties> getMongoClientList(){
        return redisPropertiesList;
    }

    public void setMongoClient(List<TenantRedisProperties> properties){
        AbstractRedisClientProperties.redisPropertiesList.addAll(properties);
    };
}
