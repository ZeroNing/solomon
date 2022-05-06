package com.steven.solomon.context;

import com.steven.solomon.properties.TenantMongoProperties;
import java.util.ArrayList;
import java.util.List;

public abstract class MongoClient {

    private List<TenantMongoProperties> mongoClientList = new ArrayList<>();

    public abstract void setMongoClient();

    public List<TenantMongoProperties> getMongoClientList(){
        return mongoClientList;
    }

    public void setMongoClient(List<TenantMongoProperties> properties){
        this.mongoClientList.addAll(properties);
    };
}
