package edu.byu.cs.tweeter.model.net.request;

import java.io.Serializable;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusRequest implements Serializable {
    public Status status;
    public AuthToken authToken;

    public  PostStatusRequest(){

    }
    public PostStatusRequest(Status Status, AuthToken authToken) {
        this.status = Status;
        this.authToken = authToken;
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
