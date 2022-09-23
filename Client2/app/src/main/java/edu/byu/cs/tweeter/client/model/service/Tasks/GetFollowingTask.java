package edu.byu.cs.tweeter.client.model.service.Tasks;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.UsersResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends PagedUserTask {

    FollowingRequest request;
    public GetFollowingTask(FollowingRequest request,Handler messageHandler) {
        super(messageHandler);
        this.request = request;
    }

    @Override
    protected Pair<List<User>, Boolean> getItems() throws IOException, TweeterRemoteException {
        UsersResponse response = new ServerFacade().GetUsers(request,"/getfollowers");
        List<User>  users = response.getItems();
        Pair<List<User>, Boolean> pair = new Pair(users,response.getHasMorePages());
//        Pair<List<User>, Boolean> pair = getFakeData().getPageOfUsers(getLastItem(), getLimit(), getTargetUser());
        return pair;
    }
}
