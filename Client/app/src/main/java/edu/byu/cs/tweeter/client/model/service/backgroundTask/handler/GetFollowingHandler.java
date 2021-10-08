//package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;
//
//import android.os.Handler;
//import android.os.Message;
//
//import androidx.annotation.NonNull;
//
//import java.util.List;
//
//import edu.byu.cs.tweeter.client.model.service.FollowingService;
//import edu.byu.cs.tweeter.client.model.service.Tasks.GetFollowingTask;
//import edu.byu.cs.tweeter.model.domain.User;
//
//public class GetFollowingHandler extends Handler {
//    private FollowingService.GetFollowingObserver observer;
//    public GetFollowingHandler(FollowingService.GetFollowingObserver observer){
//        this.observer = observer;
//    }
//    public void handleMessage(@NonNull Message msg) {
//        boolean success = msg.getData().getBoolean(GetFollowingTask.SUCCESS_KEY);
//        if (success) {
//            List<User> followees = (List<User>) msg.getData().getSerializable(GetFollowingTask.ITEMS_KEY);
//            boolean hasMorePages = msg.getData().getBoolean(GetFollowingTask.MORE_PAGES_KEY);
//
//            User lastFollowee = (followees.size() > 0) ? followees.get(followees.size() - 1) : null;
//            observer.getFollowingSucceeded(followees, hasMorePages,lastFollowee);
//        } else if (msg.getData().containsKey(GetFollowingTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(GetFollowingTask.MESSAGE_KEY);
//            observer.getFollowingFailed(message);
//        } else if (msg.getData().containsKey(GetFollowingTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(GetFollowingTask.EXCEPTION_KEY);
//            observer.getFollowingThrewException(ex);
//        }
//    }
//}
