package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.List;
import java.util.Map;

public class DynamoFeedDAO extends DynamoStatus {

    public DynamoFeedDAO() {
        super("Feed", "feed");
    }

    @Override
    public String postStatus(String status, List<String>  alias, String date) {
        TableWriteItems items = new TableWriteItems("Feed");
        for (String handle : alias) {
            Item item = new Item()
                    .withPrimaryKey("alias", handle)
                    .withString("date", date)
                    .withString("status", status);
            items.addItemToPut(item);
        }
        // Write any leftover items
        loopBatchWrite(items);

        return "Success";
    }

    private void loopBatchWrite (TableWriteItems items) {
        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(items);

        while (outcome.getUnprocessedItems().size() > 0) {
            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
            outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);

        }
    }
}