package edu.byu.cs.tweeter.model.net.request;

import java.io.Serializable;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class FollowRequest implements Serializable {
    private String authToken;
    private String target;
    private int limit;
    private String lastThingy;

    public FollowRequest(){

    }
    public FollowRequest(String authToken, String target, int limit, String lastItem) {
       this.authToken = authToken;
       this.target = target;
       this.limit = limit;
       this.lastThingy = lastItem;
    }

    public String getToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getLastItem() {
        return lastThingy;
    }

    public void setLastFollower(String lastFollower) {
        this.lastThingy = lastFollower;
    }
}
