package edu.byu.cs.tweeter.client.model.service.Tasks;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends AuthenticationTask {

    public LoginTask(LoginRequest request, Handler messageHandler) {
        super(messageHandler, request.getUsername(), request.getPassword());
    }

    @Override
    protected Pair<User, AuthToken> runAuthenticationTask() throws TweeterRemoteException, IOException {
        LoginRequest request = new LoginRequest(username, password);
        LoginResponse response = new ServerFacade().login(request, "/login");
        User loggedInUser = response.getUser();
        AuthToken authToken = response.getAuthToken();
        return new Pair<>(loggedInUser, authToken);
    }
}
