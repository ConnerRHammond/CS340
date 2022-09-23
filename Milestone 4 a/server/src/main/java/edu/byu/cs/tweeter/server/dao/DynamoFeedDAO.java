package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DynamoFeedDAO extends DynamoStatus {

    public DynamoFeedDAO() {
        super("feed", "feed");
    }

    @Override
    StatusList createStatusList(User user) {
        StatusList statuses = new StatusList();
        return statuses;
    }
}