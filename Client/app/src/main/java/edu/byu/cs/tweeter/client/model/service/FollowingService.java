package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingService {

    public interface  GetFollowingObserver{
        void getFollowingSucceeded(List<User> users, Boolean hasMorePages, User user);
        void getFollowingFailed(String message);
        void getFollowingThrewException(Exception ex);
    }

    public void getFollowing(AuthToken authToken, User targetUser,int limit,User lastFollowee,
                             GetFollowingObserver observer){

        GetFollowingTask getFollowingTask = new GetFollowingTask(authToken,
                targetUser, limit, lastFollowee, new GetFollowingHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowingTask);
    }
    private class GetFollowingHandler extends Handler {
        private GetFollowingObserver observer;
        public GetFollowingHandler(GetFollowingObserver observer){
            this.observer = observer;
        }
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(GetFollowingTask.SUCCESS_KEY);
            if (success) {
                List<User> followees = (List<User>) msg.getData().getSerializable(GetFollowingTask.FOLLOWEES_KEY);
                boolean hasMorePages = msg.getData().getBoolean(GetFollowingTask.MORE_PAGES_KEY);

                User lastFollowee = (followees.size() > 0) ? followees.get(followees.size() - 1) : null;
                observer.getFollowingSucceeded(followees, hasMorePages,lastFollowee);
            } else if (msg.getData().containsKey(GetFollowingTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetFollowingTask.MESSAGE_KEY);
               observer.getFollowingFailed(message);
            } else if (msg.getData().containsKey(GetFollowingTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetFollowingTask.EXCEPTION_KEY);
                observer.getFollowingThrewException(ex);
            }
        }
    }
}