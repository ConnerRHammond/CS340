package edu.byu.cs.tweeter.server.dao;


import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import edu.byu.cs.tweeter.model.net.response.CountResponse;
import edu.byu.cs.tweeter.server.daoInterfaces.FollowsDAO;
import edu.byu.cs.tweeter.util.Pair;


import java.util.*;

public class DynamoFollowDAO extends DynamoBaseDAO implements FollowsDAO {
    /*
    followee is someone who is being followed by a follower
     */

    public DynamoFollowDAO() {
        super("follows");
    }


    @Override
    public boolean isFollower(String follower, String followee) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#f", "follower_handle");
        nameMap.put("#h", "followee_handle");
        HashMap<String, Object> valueMap = new HashMap<String,Object>();
        valueMap.put(":g", follower);
        valueMap.put(":t",followee);
        ItemCollection<QueryOutcome> items = tableQueryExecuter(nameMap,valueMap,table,"#f = :g and #h = :t",0,null);
        Iterator<Item> iterator = null;
        iterator = items.iterator();
        boolean isFollower  = iterator.hasNext() ;
        try{
            if(isFollower) {
                System.out.print(iterator.next());
            }
        }catch (Exception e){

        }
        return isFollower;
    }

    @Override
    public String Follow(String follower,String alias) {
        return create(new Item().withPrimaryKey("follower_handle", alias)
                .withString("followee_handle",follower), table);

    }

    @Override
    public String unFollow(String followee,String follower) {
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("follower_handle", follower, "followee_handle",followee ));
        return delete(deleteItemSpec,table);
    }

    @Override
    public CountResponse getFollowerCount(String Alias){
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#f", "follower_handle");
        HashMap<String, Object> valueMap = new HashMap<String,Object>();
        valueMap.put(":g", Alias);

        ItemCollection<QueryOutcome> items = tableQueryExecuter(nameMap,valueMap,table,"#f = :g",0,null);
        return new CountResponse(numberOfMatches(items));
    }
    @Override
    public CountResponse getFollowingCount(String Alias){
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#f", "followee_handle");
        HashMap<String, Object> valueMap = new HashMap<String,Object>();
        valueMap.put(":g",Alias);
        ItemCollection<QueryOutcome> items = indexQueryExecuter(nameMap,valueMap,table.getIndex("followee_handle"),"#f = :g",0,null);
        return new CountResponse(numberOfMatches(items));
    }

    @Override
    public Pair<List<String>, Boolean> getFollowers(String Alias, ArrayList key) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#f", "followee_handle");
        HashMap<String, Object> valueMap = new HashMap<String,Object>();
        valueMap.put(":g",Alias);
        ItemCollection<QueryOutcome> items = indexQueryExecuter(nameMap,valueMap,table.getIndex("followee_handle"),"#f = :g",10,key);
        Iterator<Item> iterator = null;
        Item item = null;
        ArrayList<String> users = new ArrayList<>();
        boolean hasMorePages = false;
        try{
            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                users.add(item.getString("follower_handle"));
            }
            try {
                hasMorePages = items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey() != null;
            }catch (Exception e){
                hasMorePages = false;
            }
            return new Pair<List<String>, Boolean>(users,hasMorePages);
        }catch (Exception e){
            System.out.println("Failed");
        }
        return new Pair<List<String>, Boolean>(users,hasMorePages);
    }

    @Override
    public Pair<List<String>, Boolean> getFollowing(String Alias,int max, ArrayList key) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#f", "follower_handle");

        HashMap<String, Object> valueMap = new HashMap<String,Object>();
        valueMap.put(":g", Alias);

        ItemCollection<QueryOutcome> items = tableQueryExecuter(nameMap,valueMap,table,"#f = :g",max,key);
        Iterator<Item> iterator = null;
        Item item = null;
        ArrayList<String> users = new ArrayList<>();
        boolean hasMorePages = true;
        try{
            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                users.add(item.getString("followee_handle"));
            }
            try {
                hasMorePages = items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey() != null;
            }catch (Exception e){
                hasMorePages = false;
            }
            return new Pair<List<String>, Boolean>(users,hasMorePages);
        }catch (Exception e){
            System.out.println("Failed");
        }
        return new Pair<List<String>, Boolean>(users,false);
    }

    public int numberOfMatches(ItemCollection<QueryOutcome> items){
        Iterator<Item> iterator = null;
        int numItems = 0;
        Item item = null;
        try{
            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.toString());
                numItems += 1;
            }
        }catch (Exception e){
            System.out.println("Failed");
        }
        return  numItems;
    }



    @Override
    public QuerySpec TableQuery(HashMap<String, String> nameMap, HashMap<String, Object> valueMap, int Max, String Expression, ArrayList key) {
        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(Expression).withNameMap(nameMap)
                .withValueMap(valueMap).withScanIndexForward(true).withMaxResultSize(Max).withExclusiveStartKey("follower_handle",key.get(0),"followee_handle",key.get(1));
        return querySpec;
    }

    @Override
    public QuerySpec IndexQuery(HashMap<String, String> nameMap, HashMap<String, Object> valueMap, int Max, String Expression, ArrayList key) {
        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#f = :g").withNameMap(nameMap)
                .withValueMap(valueMap).withScanIndexForward(true).withMaxResultSize(10).withExclusiveStartKey("followee_handle",key.get(0),"follower_handle",key.get(1));
        return querySpec;
    }
}
