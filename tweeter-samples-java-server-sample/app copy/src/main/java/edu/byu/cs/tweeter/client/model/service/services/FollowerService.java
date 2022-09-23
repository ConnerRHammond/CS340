package edu.byu.cs.tweeter.client.model.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.Tasks.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PagesHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PageObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowerService extends ServiceBase {

    public void getFollowers(AuthToken authToken, User targetUser, int limit, User lastFollower, PageObserver<User> observer){
        GetFollowersTask getFollowersTask = new GetFollowersTask(authToken,
                targetUser, limit,lastFollower, new GetFollowersHandler(observer));
        Execute(getFollowersTask);
    }
    private class GetFollowersHandler extends PagesHandler<User> {
        public GetFollowersHandler(PageObserver<User> observer) { super(observer); }
        @Override
        protected String getFailedMessagePrefix() { return "Followers failed to load:"; }
    }
}
