package edu.byu.cs.tweeter.model.net.response;

import java.io.Serializable;

import edu.byu.cs.tweeter.model.domain.User;

public class GetUserResponse extends Response implements Serializable {
    private User user;

    public GetUserResponse(){

    }
    public GetUserResponse( String message) {
        super(false, message);
    }

    public GetUserResponse(User user) {
        super(true,null);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
