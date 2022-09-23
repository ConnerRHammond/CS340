package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.AuthenticationRequest;
import edu.byu.cs.tweeter.util.Pair;

public abstract class AuthenticationService extends BaseService {
    public String username;
    public String password;

    AuthenticationService(AuthenticationRequest request){
        this.username = request.getUsername();
        this.password = request.getPassword();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
