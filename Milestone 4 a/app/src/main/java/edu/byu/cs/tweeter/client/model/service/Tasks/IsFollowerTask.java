package edu.byu.cs.tweeter.client.model.service.Tasks;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.util.Random;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.model.net.response.StatusResponse;

/**
 * Background task that determines if one user is following another.
 */
public class IsFollowerTask extends AuthorizedTask {

    public static final String IS_FOLLOWER_KEY = "is-follower";

    /**
     * The alleged follower.
     */
    private final User follower;

    /**
     * The alleged followee.
     */
    private final User followee;

    private boolean isFollower;
    private FollowingRequest request;

    public IsFollowerTask(AuthToken authToken, User follower, User followee, Handler messageHandler) {
        super(authToken, messageHandler);
        this.follower = follower;
        this.followee = followee;
        this.request = new FollowingRequest(authToken,follower.getAlias(),0,followee.getAlias());
    }

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        IsFollowerResponse response = new ServerFacade().isFollower(request,"/isfollowing");
        isFollower = response.isFollower();
    }

    @Override
    protected void loadBundle(Bundle msgBundle) {
        msgBundle.putBoolean(IS_FOLLOWER_KEY, isFollower);
    }
}
