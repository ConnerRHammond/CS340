package edu.byu.cs.tweeter.client.model.service.Tasks;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.AuthTokenRequest;

/**
 * Background task that logs out a user (i.e., ends a session).
 */
public class LogoutTask extends AuthorizedTask {
    private  AuthTokenRequest input;
    public LogoutTask(AuthTokenRequest input, Handler messageHandler) {
        super(input.getAuthToken(), messageHandler);
        this.input = input;
    }

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        new ServerFacade().LogOut(input,"/logout");
        // We could do this from the presenter, without a task and handler, but we will
        // eventually remove the auth token from  the DB and will need this then.
    }

    @Override
    protected void loadBundle(Bundle msgBundle) {
        // Nothing to load
    }
}
