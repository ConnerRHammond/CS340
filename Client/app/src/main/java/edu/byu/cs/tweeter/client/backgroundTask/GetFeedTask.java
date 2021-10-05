package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's feed.
 */
public class GetFeedTask extends PageTask<Status> {
    private static final String LOG_TAG = "GetFeedTask";

    public GetFeedTask(AuthToken authToken, User targetUser, int limit, Status lastItem, Handler messageHandler) {
        super(authToken, targetUser, limit, lastItem, messageHandler);
    }

    @Override
    protected boolean runTask() throws IOException {
        Pair<List<Status>, Boolean> pageOfStatus = getFakeData().getPageOfStatus((Status)lastItem, limit);
        this.items = pageOfStatus.getFirst();
        this.hasMorePages = pageOfStatus.getSecond();

        for (Status s : this.items) {
            BackgroundTaskUtils.loadImage(s.getUser());
        }
        return true;
    }

}
