package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainActivityService {


    public interface MainActivityObserver {

        void actionSucceded(String Message);
        void actionFailed(String Message);
        void enableButton(Boolean enable);
        void updateFollowButton(Boolean value);
        void setFolloweeCount(String count);
        void setFollowerCount(String count);
        void logOutUser();
    }

    public void isFollower(AuthToken authToken, User currentUser, User selectedUser, MainActivityObserver observer){
                    IsFollowerTask isFollowerTask = new IsFollowerTask(Cache.getInstance().getCurrUserAuthToken(),
                    Cache.getInstance().getCurrUser(), selectedUser, new IsFollowerHandler(observer));
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(isFollowerTask);
    }

    public void followTask(AuthToken authToken, User selectedUser,MainActivityObserver observer){
        FollowTask followTask = new FollowTask(authToken,
                selectedUser, new FollowHandler(observer,authToken,selectedUser,false));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followTask);
    }
    public  void unFollowTask(AuthToken authToken, User selectedUser, MainActivityObserver observer){
        UnfollowTask unfollowTask = new UnfollowTask(authToken,
                selectedUser, new FollowHandler(observer,authToken,selectedUser,true));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(unfollowTask);
    }
    public void logOut(AuthToken authToken,MainActivityObserver observer){
        LogoutTask logoutTask = new LogoutTask(authToken, new LogoutHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(logoutTask);
    }

    public void postStatus(String post, User user, String date, List<String> URLS,List<String> containedMentions, MainActivityObserver observer){
        Status newStatus = new Status(post,user, date , URLS, containedMentions);
        PostStatusTask statusTask = new PostStatusTask(Cache.getInstance().getCurrUserAuthToken(),
                newStatus, new PostStatusHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(statusTask);
    }
    public void updateSelectedUserFollowingAndFollowers(AuthToken authToken,User selectedUser,MainActivityObserver observer) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Get count of most recently selected user's followers.
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(authToken,
                selectedUser, new GetFollowersCountHandler(observer));
        executor.execute(followersCountTask);

        // Get count of most recently selected user's followees (who they are following)
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(authToken,
                selectedUser, new GetFollowingCountHandler(observer));
        executor.execute(followingCountTask);
    }


    private class LogoutHandler extends Handler {
        private MainActivityObserver observer;

        public  LogoutHandler(MainActivityObserver observer){
            this.observer =observer;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(LogoutTask.SUCCESS_KEY);
            if (success) {
                observer.logOutUser();
            } else if (msg.getData().containsKey(LogoutTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(LogoutTask.MESSAGE_KEY);
                observer.actionFailed("Failed to logout: " + message);
            } else if (msg.getData().containsKey(LogoutTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(LogoutTask.EXCEPTION_KEY);
                observer.actionFailed("Failed to logout because of exception: " + ex.getMessage());
            }
        }
    }

    private class GetFollowersCountHandler extends Handler {
        private MainActivityObserver observer;

        public  GetFollowersCountHandler(MainActivityObserver observer){
            this.observer =observer;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(GetFollowersCountTask.SUCCESS_KEY);
            if (success) {
                int count = msg.getData().getInt(GetFollowersCountTask.COUNT_KEY);
                observer.setFollowerCount(String.valueOf(count));
            } else if (msg.getData().containsKey(GetFollowersCountTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetFollowersCountTask.MESSAGE_KEY);
                observer.actionFailed("Failed to get followers count: " + message);
            } else if (msg.getData().containsKey(GetFollowersCountTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetFollowersCountTask.EXCEPTION_KEY);
                observer.actionFailed("Failed to get followers count because of exception: " + ex.getMessage());
            }
        }
    }
    private class GetFollowingCountHandler extends Handler {
        private MainActivityObserver observer;

        public   GetFollowingCountHandler(MainActivityObserver observer){
            this.observer =observer;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(GetFollowingCountTask.SUCCESS_KEY);
            if (success) {
                int count = msg.getData().getInt(GetFollowingCountTask.COUNT_KEY);
                observer.setFolloweeCount(String.valueOf(count));
            } else if (msg.getData().containsKey(GetFollowingCountTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetFollowingCountTask.MESSAGE_KEY);
                observer.actionFailed("Failed to get following count: " + message);
            } else if (msg.getData().containsKey(GetFollowingCountTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetFollowingCountTask.EXCEPTION_KEY);
                observer.actionFailed("Failed to get following count because of exception: " + ex.getMessage());
            }
        }
    }
    private class IsFollowerHandler extends Handler {
        private MainActivityObserver observer;

        public IsFollowerHandler (MainActivityObserver observer){
            this.observer =observer;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(IsFollowerTask.SUCCESS_KEY);
            if (success) {
                boolean isFollower = msg.getData().getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
                observer.updateFollowButton(!isFollower);
            } else if (msg.getData().containsKey(IsFollowerTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(IsFollowerTask.MESSAGE_KEY);
                observer.actionFailed("Failed to determine following relationship: " + message);
            } else if (msg.getData().containsKey(IsFollowerTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(IsFollowerTask.EXCEPTION_KEY);
                observer.actionFailed("Failed to determine following relationship because of exception: " + ex.getMessage());
            }
        }
    }


    private class FollowHandler extends Handler {
        private MainActivityObserver observer;
        private Boolean unFollow;
        private AuthToken authToken;
        private User selectedUser;
        public FollowHandler(MainActivityObserver observer, AuthToken authToken, User selectedUser, Boolean unFollow) {
            this.observer = observer;
            this.selectedUser = selectedUser;
            this.authToken = authToken;
            this.unFollow = unFollow;

        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            String follow = unFollow ? "unfollow" : "follow";
            boolean success = msg.getData().getBoolean(FollowTask.SUCCESS_KEY);
            if (success) {
                updateSelectedUserFollowingAndFollowers(authToken,
                        selectedUser, observer);
                observer.updateFollowButton(unFollow);

            } else if (msg.getData().containsKey(FollowTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(FollowTask.MESSAGE_KEY);
                observer.actionFailed("Failed to" + follow + message);
            } else if (msg.getData().containsKey(FollowTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(FollowTask.EXCEPTION_KEY);
                observer.actionFailed("Failed to " + follow + " because of exception: " + ex.getMessage());
            }
            observer.enableButton(true);
        }
    }
    private class PostStatusHandler extends Handler {
        private MainActivityObserver observer;

        public PostStatusHandler(MainActivityObserver observer){
            this.observer =observer;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(PostStatusTask.SUCCESS_KEY);
            if (success) {
                observer.actionSucceded("Successfully Posted!");
            } else if (msg.getData().containsKey(PostStatusTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(PostStatusTask.MESSAGE_KEY);
                observer.actionFailed("Failed to post status: " + message);
            } else if (msg.getData().containsKey(PostStatusTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(PostStatusTask.EXCEPTION_KEY);
                observer.actionFailed("Failed to post status because of exception: " + ex.getMessage());
            }
        }
    }
}
