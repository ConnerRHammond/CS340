package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.Tasks.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PagesHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PageObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingService extends ServiceBase{

    public void getFollowing(AuthToken authToken, User targetUser,int limit,User lastFollowee,
                             PageObserver<User> observer){
        GetFollowingTask getFollowingTask = new GetFollowingTask(authToken,
                targetUser, limit, lastFollowee, new GetFollowingHandler(observer));
        Execute(getFollowingTask);
    }
    private class GetFollowingHandler extends PagesHandler<User> {
        public GetFollowingHandler(PageObserver<User> observer) { super(observer); }
        @Override
        protected String getFailedMessagePrefix() { return "Followers failed to load:"; }
    }
}
