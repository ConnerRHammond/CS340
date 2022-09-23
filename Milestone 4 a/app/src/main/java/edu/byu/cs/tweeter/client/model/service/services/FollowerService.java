package edu.byu.cs.tweeter.client.model.service.services;

import edu.byu.cs.tweeter.client.model.service.Tasks.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PagesHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PageObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;

public class FollowerService extends ServiceBase {

    public void getFollowers(AuthToken authToken, User targetUser, int limit, User lastFollower, PageObserver<User> observer){
        String lastItem = lastFollower != null ? lastFollower.getAlias() : null;
        FollowingRequest request = new FollowingRequest(authToken,targetUser.getAlias(),limit,lastItem);
        GetFollowersTask getFollowersTask = new GetFollowersTask(request, new GetFollowersHandler(observer));
        Execute(getFollowersTask);
    }
    private class GetFollowersHandler extends PagesHandler<User> {
        public GetFollowersHandler(PageObserver<User> observer) { super(observer); }
        @Override
        protected String getFailedMessagePrefix() { return "Followers failed to load:"; }
    }
}
