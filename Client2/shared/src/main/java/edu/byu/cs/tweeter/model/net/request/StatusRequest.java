package edu.byu.cs.tweeter.model.net.request;

import java.io.Serializable;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusRequest  implements Serializable {
    public AuthToken authToken;
    public String target;
    public int limit;
    public Status Item;

    public void setItem(Status item) {
        Item = item;
    }

    public StatusRequest(){

    }
    public StatusRequest(AuthToken authToken,String target, int limit, Status Item) {
        this.authToken = authToken;
        this.target = target;
        this.limit = limit;
        this.Item = Item;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
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

    public Status getItem() {
        return Item;
    }

    public void setLastFollower(Status lastFollower) {
        this.Item = lastFollower;
    }
}
