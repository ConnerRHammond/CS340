//package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;
//
//import android.os.Bundle;
//
//import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.ServiceObserver;
//import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleNotificationServiceObserver;
//
//public class FollowHandler extends BackgroundTaskHandler {
//
//    public FollowHandler(ServiceObserver observer) {
//        super((SimpleNotificationServiceObserver) observer);
//    }
//
//    @Override
//    protected String getFailedMessagePrefix() {
//        return "Failed to fFollow";
//    }
//
//    @Override
//    protected void handleSuccessMessage(ServiceObserver observer, Bundle data) {
//
//    }
//
//
//}
////public class FollowHandler extends Handler {
////    private MainActivityService.MainActivityObserver observer;
////    private Boolean unFollow;
////    private AuthToken authToken;
////    private User selectedUser;
////    public FollowHandler(MainActivityService.MainActivityObserver observer, AuthToken authToken, User selectedUser, Boolean unFollow) {
////        this.observer = observer;
////        this.selectedUser = selectedUser;
////        this.authToken = authToken;
////        this.unFollow = unFollow;
////
////    }
////    @Override
////    public void handleMessage(@NonNull Message msg) {
////        String follow = unFollow ? "unfollow" : "follow";
////        boolean success = msg.getData().getBoolean(FollowTask.SUCCESS_KEY);
////        if (success) {
////            observer.updateFollowButton(unFollow);
////        } else if (msg.getData().containsKey(FollowTask.MESSAGE_KEY)) {
////            String message = msg.getData().getString(FollowTask.MESSAGE_KEY);
////            observer.actionFailed("Failed to" + follow + message);
////        } else if (msg.getData().containsKey(FollowTask.EXCEPTION_KEY)) {
////            Exception ex = (Exception) msg.getData().getSerializable(FollowTask.EXCEPTION_KEY);
////            observer.actionFailed("Failed to " + follow + " because of exception: " + ex.getMessage());
////        }
////        observer.enableButton(true);
////    }
////}
