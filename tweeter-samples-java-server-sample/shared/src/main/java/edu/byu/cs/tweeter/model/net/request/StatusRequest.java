package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedRequest extends ListRequest<Status>{
    public FeedRequest(AuthToken authToken, User target, int limit, Status lastFollower) {
        super(authToken, target, limit, lastFollower);
    }
}
