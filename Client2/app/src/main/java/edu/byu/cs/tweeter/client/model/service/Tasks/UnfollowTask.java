package edu.byu.cs.tweeter.client.model.service.Tasks;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.AuthenticatedRequest;
import edu.byu.cs.tweeter.model.net.response.Response;

/**
 * Background task that removes a following relationship between two users.
 */
public class UnfollowTask extends AuthorizedTask {

    /**
     * The user that is being followed.
     */
    private final User followee;
    AuthenticatedRequest request;

    public UnfollowTask(AuthenticatedRequest request, Handler messageHandler) {
        super(request.getAuthToken(), messageHandler);
        this.request = request;
        this.followee = request.getUser();
    }

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        Response response = new ServerFacade().Follow(request, "/unfollow");
        // We could do this from the presenter, without a task and handler, but we will
        // eventually access the database from here when we aren't using dummy data.
    }

    @Override
    protected void loadBundle(Bundle msgBundle) {
        // Nothing to load
    }
}
