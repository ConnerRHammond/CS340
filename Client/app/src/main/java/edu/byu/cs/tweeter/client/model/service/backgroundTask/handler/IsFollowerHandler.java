//package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;
//
//import android.os.Handler;
//import android.os.Message;
//
//import androidx.annotation.NonNull;
//
//import edu.byu.cs.tweeter.client.model.service.MainActivityService;
//import edu.byu.cs.tweeter.client.model.service.Tasks.IsFollowerTask;
//
//public class IsFollowerHandler extends Handler {
//    private MainActivityService.MainActivityObserver observer;
//
//    public IsFollowerHandler (MainActivityService.MainActivityObserver observer){
//        this.observer =observer;
//    }
//    @Override
//    public void handleMessage(@NonNull Message msg) {
//        boolean success = msg.getData().getBoolean(IsFollowerTask.SUCCESS_KEY);
//        if (success) {
//            boolean isFollower = msg.getData().getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
//            observer.updateFollowButton(!isFollower);
//        } else if (msg.getData().containsKey(IsFollowerTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(IsFollowerTask.MESSAGE_KEY);
//            observer.actionFailed("Failed to determine following relationship: " + message);
//        } else if (msg.getData().containsKey(IsFollowerTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(IsFollowerTask.EXCEPTION_KEY);
//            observer.actionFailed("Failed to determine following relationship because of exception: " + ex.getMessage());
//        }
//    }
//}
