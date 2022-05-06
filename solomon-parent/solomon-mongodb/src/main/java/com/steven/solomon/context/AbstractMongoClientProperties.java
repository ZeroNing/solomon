package com.steven.solomon.context;

import com.steven.solomon.properties.TenantMongoProperties;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMongoClientProperties implements MongoClientPropertiesService{

    private static List<TenantMongoProperties> mongoClientList = new ArrayList<>();

    public List<TenantMongoProperties> getMongoClientList(){
        return mongoClientList;
    }

    public void setMongoClient(List<TenantMongoProperties> properties){
      AbstractMongoClientProperties.mongoClientList.addAll(properties);
    };
}
