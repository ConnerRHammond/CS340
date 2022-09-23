package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.*;

public class DynamoStoryDAO extends DynamoStatus{
    public DynamoStoryDAO() {
        super("Story","story");
    }


    @Override
    StatusList createStatusList(User user) {
        StatusList statuses = new StatusList();
        Date date = new Date();
        statuses.getArray().add(new Status("This is my first post", user,date.toString(),new ArrayList<>(),new ArrayList<>()));
        return statuses;
    }
}
