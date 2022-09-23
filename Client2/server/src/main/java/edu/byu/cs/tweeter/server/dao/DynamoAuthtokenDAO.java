package edu.byu.cs.tweeter.server.dao;


import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import edu.byu.cs.tweeter.server.daoInterfaces.AuthtokenDAO;
import edu.byu.cs.tweeter.util.Pair;

public class DynamoAuthtokenDAO extends DynamoBaseDAO implements AuthtokenDAO {
    public DynamoAuthtokenDAO() {
        super("authtoken");
    }

    @Override
    public double getTokenTime(String authtoken) {
        double authToken = 0.0;
        Item item = getToken(authtoken);
        authToken = item.getLong("date");
        return authToken;
    }
    @Override
    public String getTokenAlias(String authtoken) {
        String alias = null;
        Item item = getToken(authtoken);
        alias = item.getString("alias");
        return alias;
    }

    @Override
    public Pair<String,Double> getAliasToken(String authtoken) {
        String alias = null;
        Item item = getToken(authtoken);
        alias = item.getString("alias");
        double token = item.getLong("date");
        return new Pair<String, Double>(alias,token);
    }


    @Override
    public String updateToken(String alias,String authtoken) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("authtoken", alias).withUpdateExpression("set authtoken = :val")
                .withValueMap(new ValueMap().withString(":val",authtoken));
        update(updateItemSpec,table);
        return "Succeded";

    }

    @Override
    public String createToken(String alias,String authtoken) {
        create(new Item().withPrimaryKey("authtoken", authtoken).withString("alias",alias).withLong("date",new Date().getTime()), table);
        return "Succeded";
    }

    @Override
    public String removeToken(String authtoken) {
        DeleteItemSpec spec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("authtoken", authtoken));
        delete(spec,table);
        return "Succeded";
    }
    public Item getToken(String authtoken){
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#f", "authtoken");
        HashMap<String, Object> valueMap = new HashMap<String,Object>();
        valueMap.put(":g",authtoken);
        ItemCollection<QueryOutcome> items = tableQueryExecuter(nameMap,valueMap,table,"#f = :g",0,null,true);
        Item item = null;
        Iterator<Item> iterator = null;
        iterator = items.iterator();
        if(iterator.hasNext()) {
            item = iterator.next();
            return item;
        }
        return null;
    }


}
