package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.daoInterfaces.StatusDAO;
import edu.byu.cs.tweeter.server.util.JsonSerializer;
import java.util.HashMap;
import java.util.Iterator;

public abstract class DynamoStatus extends DynamoBaseDAO implements StatusDAO {
    private String statusType;
    public DynamoStatus(String table,String statusType){
        super(table);
        this.statusType = statusType;
    }

    @Override
    public String postStatus(Status status, String alias) {
        StatusList statuses = getStatuses(alias);
        statuses.getArray().add(0,status);
        String json = JsonSerializer.serialize(statuses);
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("alias", alias).withUpdateExpression("set " +statusType +" = :val")
                .withValueMap(new ValueMap().withString(":val", json));
        update(updateItemSpec,table);
        return "Succeded";
    }

    @Override
    public String createStatuses(User user) {
        StatusList statuses = createStatusList(user);
        String json = JsonSerializer.serialize(statuses);
        return create(new Item().withPrimaryKey("alias",user.getAlias()).withString(statusType,json),table);
    }

    @Override
    public StatusList getStatuses(String alias) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#f", "alias");
        HashMap<String, Object> valueMap = new HashMap<String,Object>();
        valueMap.put(":g",alias);
        ItemCollection<QueryOutcome> items = tableQueryExecuter(nameMap,valueMap,table,"#f = :g",0,null);
        Item item = null;
        Iterator<Item> iterator = null;
        StatusList statuses = null;
        try{
            iterator = items.iterator();
            item = iterator.next();
            String json = item.getString(statusType);
            statuses = JsonSerializer.deserialize(json, StatusList.class);

        }catch (Exception e){
            System.out.println("Failed");
        }
        return statuses;
    }
    abstract StatusList createStatusList(User user);
}

