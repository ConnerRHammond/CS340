package edu.byu.cs.tweeter.server.dao;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class DynamoBaseDAO {
    public Table table;
    private String statusType;
    public DynamoDB dynamoDB;

    public DynamoBaseDAO(String table) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-west-1")
                .build();
        this.dynamoDB= new DynamoDB(client);
        this.table = dynamoDB.getTable(table);
    }

    public ItemCollection<QueryOutcome> indexQueryExecuter(HashMap<String, String> nameMap, HashMap<String, Object> valueMap, Index table, String Expression, int Max, ArrayList key) {
        QuerySpec querySpec;
        if (Max != 0) {
            if (key != null) {
                querySpec = IndexQuery(nameMap, valueMap, Max, Expression, key);
            } else {
                querySpec = new QuerySpec().withKeyConditionExpression(Expression).withNameMap(nameMap)
                        .withValueMap(valueMap).withMaxResultSize(Max).withScanIndexForward(true);
            }
        } else {
            querySpec = new QuerySpec().withKeyConditionExpression(Expression).withNameMap(nameMap)
                    .withValueMap(valueMap).withScanIndexForward(true);
        }
        ItemCollection<QueryOutcome> items = null;
        try {
            items = table.query(querySpec);
        } catch (Exception e) {
            System.out.println("Failed");
        }
        return items;
    }

    public ItemCollection<QueryOutcome> tableQueryExecuter(HashMap<String, String> nameMap, HashMap<String, Object> valueMap, Table table, String Expression, int Max, ArrayList key,boolean scanIndex) {
        (nameMap,valueMap,table,"#f = :g",0,null,true);
        QuerySpec querySpec;
        if (Max != 0) {
            if (key != null) {
                querySpec = TableQuery(nameMap, valueMap, Max, Expression, key);
            } else {
                querySpec = new QuerySpec().withKeyConditionExpression(Expression).withNameMap(nameMap)
                        .withValueMap(valueMap).withScanIndexForward(scanIndex).withMaxResultSize(Max);
            }
        } else {
            querySpec = new QuerySpec().withKeyConditionExpression(Expression).withNameMap(nameMap)
                    .withValueMap(valueMap).withScanIndexForward(true);
        }
        ItemCollection<QueryOutcome> items = null;
        try {
            items = table.query(querySpec);
        } catch (Exception e) {
            System.out.println("Failed");
        }
        return items;
    }

    public String create(Item item, Table table) {
        try {
            table.putItem(item);
            return "Succeded";
        } catch (Exception e) {
            return e.toString();
        }
    }

    public String delete(DeleteItemSpec deleteItemSpec, Table table) {
        try {
            table.deleteItem(deleteItemSpec);
            return "Succeded";
        } catch (Exception e) {
            return e.toString();
        }
    }

    public String update(UpdateItemSpec updateItemSpec, Table table) {
        try {
            table.updateItem(updateItemSpec);
            return "Succeded";
        } catch (Exception e) {
            return e.toString();
        }
    }

    public QuerySpec TableQuery(HashMap<String, String> nameMap, HashMap<String, Object> valueMap, int Max, String Expression, Map<String, AttributeValue> key) {
        return null;
    }

    public QuerySpec IndexQuery(HashMap<String, String> nameMap, HashMap<String, Object> valueMap, int Max, String Expression, Map<String, AttributeValue> key) {
        return null;
    }

    public QuerySpec TableQuery(HashMap<String, String> nameMap, HashMap<String, Object> valueMap, int Max, String Expression, ArrayList key) {
        return null;
    }

    public  QuerySpec IndexQuery(HashMap<String, String> nameMap, HashMap<String, Object> valueMap, int Max, String Expression, ArrayList key){
        return null;
    }
}

