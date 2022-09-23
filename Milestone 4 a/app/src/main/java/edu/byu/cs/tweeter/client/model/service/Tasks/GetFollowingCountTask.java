package edu.byu.cs.tweeter.client.model.service.Tasks;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.AuthenticatedRequest;

/**
 * Background task that queries how many other users a specified user is following.
 */
public class GetFollowingCountTask extends GetCountTask {

    private AuthenticatedRequest request;
    public GetFollowingCountTask(AuthenticatedRequest request, Handler messageHandler) {
        super(request.getAuthToken(),request.getUser(), messageHandler);
        this.request = request;
    }

    @Override
    protected int runCountTask() throws IOException, TweeterRemoteException {
        int count =new ServerFacade().GetFollowingCount(request,"/getfollowingcount").getCount();
        return count;
    }
}
