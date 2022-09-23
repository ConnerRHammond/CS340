package edu.byu.cs.tweeter.client.model.service.Tasks;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;

/**
 * Background task that returns the profile for a specified user.
 */
public class GetUserTask extends AuthorizedTask {

    public static final String USER_KEY = "user";

    /**
     * Alias (or handle) for user whose profile is being retrieved.
     */
    private final String alias;

    private User user;
    private GetUserRequest request;
    public GetUserTask(GetUserRequest request, Handler messageHandler) {
        super(request.getAuthToken(), messageHandler);
        this.alias = request.getAlias();
        this.request = request;
    }

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        User user1 = getUser();
        BackgroundTaskUtils.loadImage(user1);
        user = user1;
    }

    @Override
    protected void loadBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, user);
    }

    private User getUser() throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = new ServerFacade();
        GetUserResponse response = serverFacade.GetUser(request,"/getuser");
        return response.getUser();
    }
}
