//package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;
//
//import android.os.Handler;
//import android.os.Message;
//
//import androidx.annotation.NonNull;
//
//import edu.byu.cs.tweeter.client.model.service.MainActivityService;
//import edu.byu.cs.tweeter.client.model.service.Tasks.GetFollowersCountTask;
//
//public class GetFollowersCountHandler extends Handler {
//    private MainActivityService.MainActivityObserver observer;
//
//    public  GetFollowersCountHandler(MainActivityService.MainActivityObserver observer){
//        this.observer =observer;
//    }
//    @Override
//    public void handleMessage(@NonNull Message msg) {
//        boolean success = msg.getData().getBoolean(GetFollowersCountTask.SUCCESS_KEY);
//        if (success) {
//            int count = msg.getData().getInt(GetFollowersCountTask.COUNT_KEY);
//            observer.setFollowerCount(String.valueOf(count));
//        } else if (msg.getData().containsKey(GetFollowersCountTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(GetFollowersCountTask.MESSAGE_KEY);
//            observer.actionFailed("Failed to get followers count: " + message);
//        } else if (msg.getData().containsKey(GetFollowersCountTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(GetFollowersCountTask.EXCEPTION_KEY);
//            observer.actionFailed("Failed to get followers count because of exception: " + ex.getMessage());
//        }
//    }
//}