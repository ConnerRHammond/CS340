package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;


/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends  PageTask <User>{
    private static final String LOG_TAG = "GetFollowingTask";

    public GetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastFollower,
                            Handler messageHandler) {
        super(authToken, targetUser, limit, lastFollower, messageHandler);
    }

    @Override
    protected boolean runTask() throws IOException {
        Pair<List<User>, Boolean> pageOfUsers = getFakeData().getPageOfUsers((User) lastItem, limit, targetUser);
        this.items = pageOfUsers.getFirst();
        this.hasMorePages = pageOfUsers.getSecond();

        for (User u : this.items) {
            BackgroundTaskUtils.loadImage(u);
        }
        return true;
    }
}