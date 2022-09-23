package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;

import java.util.List;

public class DynamoStoryDAO extends DynamoStatus{
    public DynamoStoryDAO() {
        super("story","story");
    }


    @Override
    public String postStatus(String status, List<String> alias, String date) {
        create(new Item().withPrimaryKey("alias", alias.get(0),"date",date)
                .withString("status", status),table);
        return "Succeeded";
    }

}
