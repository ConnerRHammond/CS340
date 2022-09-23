package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.daoInterfaces.StatusDAO;
import edu.byu.cs.tweeter.server.util.JsonSerializer;
import edu.byu.cs.tweeter.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class DynamoStatus extends DynamoBaseDAO implements StatusDAO {
    private String statusType;
    public DynamoStatus(String table,String statusType){
        super(table);
        this.statusType = statusType;
    }

    @Override
    abstract  public String postStatus(String status, List<String> alias, String date);


    @Override
    public Pair<ArrayList<Status>, Boolean> getStatuses(String alias, ArrayList<String> list) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#f", "alias");
        HashMap<String, Object> valueMap = new HashMap<String,Object>();
        valueMap.put(":g",alias);
        ItemCollection<QueryOutcome> items = tableQueryExecuter(nameMap,valueMap,table,"#f = :g",10,list,false);
        Item item = null;
        Iterator<Item> iterator = null;
        ArrayList<Status> statuses = new ArrayList<>();
        boolean hasMorePages = false;
        try{

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                String json = item.getString("status");
                statuses.add(JsonSerializer.deserialize(json, Status.class));
            }
            try {
                hasMorePages = items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey() != null;
            }catch (Exception e){
                hasMorePages = false;
            }

        }catch (Exception e){
            System.out.println("Failed");
        }

        return new Pair<>(statuses,hasMorePages);
    }


    @Override
    public QuerySpec TableQuery(HashMap<String, String> nameMap, HashMap<String, Object> valueMap, int Max, String Expression, ArrayList key) {
        String alias = (String) valueMap.get(":g");
        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(Expression).withNameMap(nameMap)
                .withValueMap(valueMap).withScanIndexForward(false).withMaxResultSize(Max).withExclusiveStartKey("alias",alias,"date",key.get(0));
        return querySpec;
    }

}

