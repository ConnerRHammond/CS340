


package edu.byu.cs.tweeter.client.model.service.services;

import android.os.Bundle;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.Tasks.FollowTask;
import edu.byu.cs.tweeter.client.model.service.Tasks.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.Tasks.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.Tasks.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.Tasks.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.Tasks.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.Tasks.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.AuthTokenRequest;
import edu.byu.cs.tweeter.model.net.request.AuthenticatedRequest;

public class MainActivityService extends ServiceBase {

    public interface FollowButtonObserver extends ServiceObserver{
        void enableButton(Boolean enable);
        void updateFollowButton(Boolean value);
        void setFolloweeCount(String count);
        void setFollowerCount(String count);
    }

    public interface MainActivityObserver extends ServiceObserver{
        void actionSucceded();
        void logOutUser();
    }

    public void isFollower(AuthToken authToken, User currentUser, User selectedUser, FollowButtonObserver observer){
        IsFollowerTask isFollowerTask = new IsFollowerTask(Cache.getInstance().getCurrUserAuthToken(),Cache.getInstance().getCurrUser(), selectedUser, new IsFollowerHandler(observer));
        Execute(isFollowerTask);
    }

    public void followTask(AuthToken authToken, User selectedUser,FollowButtonObserver observer){
        AuthenticatedRequest request = new AuthenticatedRequest(authToken,selectedUser);
        FollowTask followTask = new FollowTask(request, new FollowHandler(observer,false));
        Execute(followTask);
        //updateSelectedUserFollowingAndFollowers(authToken,selectedUser,observer);
    }
    public  void unFollowTask(AuthToken authToken, User selectedUser, FollowButtonObserver observer){
        AuthenticatedRequest request = new AuthenticatedRequest(authToken,selectedUser);
        UnfollowTask unfollowTask = new UnfollowTask(request, new FollowHandler(observer,true));
        Execute(unfollowTask);
        //updateSelectedUserFollowingAndFollowers(authToken,selectedUser,observer);
    }
    public void logOut(AuthToken authToken,MainActivityObserver observer){
        AuthTokenRequest input = new AuthTokenRequest(authToken);
        LogoutTask logoutTask = new LogoutTask(input, new LogoutHandler(observer));
        Execute(logoutTask);
    }

    public void postStatus(AuthToken authToken,Status newStatus,  MainActivityObserver observer){
        PostStatusTask statusTask = new PostStatusTask(authToken,
                newStatus, new PostStatusHandler(Looper.getMainLooper(),observer));
        Execute(statusTask);
    }
    public void updateSelectedUserFollowingAndFollowers(AuthToken authToken,User selectedUser,FollowButtonObserver observer) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Get count of most recently selected user's followers.
        AuthenticatedRequest request = new AuthenticatedRequest(authToken,selectedUser);
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(request,new GetFollowersCountHandler(observer));
        executor.execute(followersCountTask);

        // Get count of most recently selected user's followees (who they are following)
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(request,new GetFollowingCountHandler(observer));
        executor.execute(followingCountTask);
    }


    private class LogoutHandler extends BackgroundTaskHandler<MainActivityObserver> {

        public  LogoutHandler(MainActivityObserver observer){
            super(observer);
        }
        @Override
        protected String getFailedMessagePrefix() {return "Failed to logout: "; }
        @Override
        protected void handleSuccessMessage(MainActivityObserver observer, Bundle data){observer.logOutUser(); }
    }

    private class GetFollowersCountHandler extends BackgroundTaskHandler<FollowButtonObserver> {
        public GetFollowersCountHandler(FollowButtonObserver observer) {
            super(observer);
        }
        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to get followers count:";
        }

        @Override
        protected void handleSuccessMessage(FollowButtonObserver observer, Bundle data) {
            observer.setFollowerCount(String.valueOf(data.getInt(GetFollowersCountTask.COUNT_KEY)));
        }
    }

    private class GetFollowingCountHandler extends BackgroundTaskHandler<FollowButtonObserver> {
        public GetFollowingCountHandler(FollowButtonObserver observer) {
            super(observer);
        }
        @Override
        protected String getFailedMessagePrefix() { return "Failed to get followers count:"; }
        @Override
        protected void handleSuccessMessage(FollowButtonObserver observer, Bundle data) { observer.setFolloweeCount(String.valueOf(data.getInt(GetFollowingCountTask.COUNT_KEY))); }
    }

    private class IsFollowerHandler extends BackgroundTaskHandler<FollowButtonObserver> {
        public IsFollowerHandler(FollowButtonObserver observer) {
            super(observer);
        }
        @Override
        protected String getFailedMessagePrefix() { return "Failed to determine following relationship:"; }
        @Override
        protected void handleSuccessMessage(FollowButtonObserver observer, Bundle data) {
            boolean isFollower = data.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
            observer.updateFollowButton(!isFollower);
        }
    }


    private class FollowHandler extends BackgroundTaskHandler<FollowButtonObserver> {
        private Boolean unFollow;
        public FollowHandler(FollowButtonObserver observer, boolean unFollow) {
            super(observer);
            this.unFollow = unFollow;
        }
        @Override
        protected String getFailedMessagePrefix() { return  unFollow ? " Failed to unfollow" : " Failed to follow"; }
        @Override
        protected void handleSuccessMessage(FollowButtonObserver observer, Bundle data) { observer.updateFollowButton(unFollow);observer.enableButton(true); }
    }

    private class PostStatusHandler extends BackgroundTaskHandler<MainActivityObserver> {
        public PostStatusHandler(Looper looper ,MainActivityObserver observer){
            super(looper,observer);
        }
        @Override
        protected String getFailedMessagePrefix() { return "Failed to post status: "; }
        @Override
        protected void handleSuccessMessage(MainActivityObserver observer, Bundle data) { observer.actionSucceded(); }

    }
}
