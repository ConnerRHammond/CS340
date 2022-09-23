package edu.byu.cs.tweeter.client.model.service.Tasks;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;

public abstract class PagedUserTask extends PagedTask<User> {

    protected PagedUserTask( Handler messageHandler) {
        super(messageHandler);
    }

    @Override
    protected final List<User> getUsersForItems(List<User> items) {
        return items;
    }
}
