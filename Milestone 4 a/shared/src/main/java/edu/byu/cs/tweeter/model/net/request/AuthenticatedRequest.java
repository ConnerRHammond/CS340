package edu.byu.cs.tweeter.model.net.request;

import java.io.Serializable;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class AuthenticatedRequest extends AuthTokenRequest implements Serializable {
    private User user;
    public AuthenticatedRequest() {
    }

    public AuthenticatedRequest(AuthToken authToken, User user) {
        super(authToken);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
