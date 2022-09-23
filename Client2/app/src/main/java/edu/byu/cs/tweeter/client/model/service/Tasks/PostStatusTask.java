package edu.byu.cs.tweeter.client.model.service.Tasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.net.JsonSerializer;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.AuthenticatedRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;

/**
 * Background task that posts a new status sent by a user.
 */
public class PostStatusTask extends AuthorizedTask {

    /**
     * The new status being sent. Contains all properties of the status,
     * including the identity of the user sending the status.
     */
    private final Status status;
    private AuthToken authToken;

    public PostStatusTask(AuthToken authToken, Status status, Handler messageHandler) {
        super(authToken, messageHandler);
        this.status = status;
        this.authToken = authToken;
    }

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
//        status.getUser().setImageBytes(null);
        PostStatusRequest request = new PostStatusRequest(status, authToken,status.getDate());
        ServerFacade fascade = new ServerFacade();
        fascade.Post(request,"/poststatus");
        // We could do this from the presenter, without a task and handler, but we will
        // eventually access the database from here when we aren't using dummy data.
    }
    @Override
    protected void loadBundle(Bundle msgBundle) {
        // Nothing to load
    }
}
