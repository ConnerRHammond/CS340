//package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;
//
//import android.os.Handler;
//import android.os.Message;
//
//import androidx.annotation.NonNull;
//
//import edu.byu.cs.tweeter.client.model.service.MainActivityService;
//import edu.byu.cs.tweeter.client.model.service.Tasks.PostStatusTask;
//
//public class PostStatusHandler extends Handler {
//    private MainActivityService.MainActivityObserver observer;
//
//    public PostStatusHandler(MainActivityService.MainActivityObserver observer){
//        this.observer =observer;
//    }
//    @Override
//    public void handleMessage(@NonNull Message msg) {
//        boolean success = msg.getData().getBoolean(PostStatusTask.SUCCESS_KEY);
//        if (success) {
//            observer.actionSucceded("Successfully Posted!");
//        } else if (msg.getData().containsKey(PostStatusTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(PostStatusTask.MESSAGE_KEY);
//            observer.actionFailed("Failed to post status: " + message);
//        } else if (msg.getData().containsKey(PostStatusTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(PostStatusTask.EXCEPTION_KEY);
//            observer.actionFailed("Failed to post status because of exception: " + ex.getMessage());
//        }
//    }
//}
