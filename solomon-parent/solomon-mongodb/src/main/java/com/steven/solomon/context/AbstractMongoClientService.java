package com.steven.solomon.context;

import com.steven.solomon.verification.ValidateUtils;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMongoClientService {

    private List<MongoProperties> mongoClientList = new ArrayList<>();

    public abstract void setMongoClient(List<MongoProperties> mongoClientList);

    public List<MongoProperties> getMongoClientList(){
        if(ValidateUtils.isEmpty(mongoClientList)){
            return new ArrayList<>();
        }
        return mongoClientList;
    }
}
