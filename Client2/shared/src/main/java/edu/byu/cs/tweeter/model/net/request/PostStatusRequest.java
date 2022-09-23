package edu.byu.cs.tweeter.model.net.request;

import java.io.Serializable;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusRequest implements Serializable {
    public Status status;
    public AuthToken authToken;
    public String date;
    public  PostStatusRequest(){

    }
    public PostStatusRequest(Status status, AuthToken authToken,String date) {
        this.status = status;
        this.authToken = authToken;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
