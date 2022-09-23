package edu.byu.cs.tweeter.client.model.service.services;

import edu.byu.cs.tweeter.client.model.service.Tasks.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PagesHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PageObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;

public class FollowingService extends ServiceBase{

    public void getFollowing(AuthToken authToken, User targetUser,int limit,User lastFollowee,
                             PageObserver<User> observer){
        String lastItem = lastFollowee != null ? lastFollowee.getAlias() : null;
        FollowingRequest request = new FollowingRequest(authToken,targetUser.getAlias(),limit,lastItem);
        GetFollowingTask getFollowingTask = new GetFollowingTask(request, new GetFollowingHandler(observer));
        Execute(getFollowingTask);
    }
    private class GetFollowingHandler extends PagesHandler<User> {
        public GetFollowingHandler(PageObserver<User> observer) { super(observer); }
        @Override
        protected String getFailedMessagePrefix() { return "Followers failed to load:"; }
    }
}
