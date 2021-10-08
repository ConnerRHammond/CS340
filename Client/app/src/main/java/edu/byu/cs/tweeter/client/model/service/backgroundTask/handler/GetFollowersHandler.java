//package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;
//
//import android.os.Handler;
//import android.os.Message;
//
//import androidx.annotation.NonNull;
//
//import java.util.List;
//
//import edu.byu.cs.tweeter.client.model.service.FollowerService;
//import edu.byu.cs.tweeter.client.model.service.Tasks.GetFollowersTask;
//import edu.byu.cs.tweeter.model.domain.User;
//
//public class GetFollowersHandler extends Handler {
//    private FollowerService.GetFollowerObserver observer;
//
//    public GetFollowersHandler(FollowerService.GetFollowerObserver observer){
//        this.observer = observer;
//    }
//    @Override
//    public void handleMessage(@NonNull Message msg) {
//
//        boolean success = msg.getData().getBoolean(GetFollowersTask.SUCCESS_KEY);
//        if (success) {
//            List<User> followers = (List<User>) msg.getData().getSerializable(GetFollowersTask.ITEMS_KEY);
//            Boolean hasMorePages = msg.getData().getBoolean(GetFollowersTask.MORE_PAGES_KEY);
//
//            User lastFollower = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;
//
//            observer.getFollowerSucceeded(followers, hasMorePages,lastFollower);
//        } else if (msg.getData().containsKey(GetFollowersTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(GetFollowersTask.MESSAGE_KEY);
//            observer.getFollowerFailed(message);
//        } else if (msg.getData().containsKey(GetFollowersTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(GetFollowersTask.EXCEPTION_KEY);
//            observer.getFollowerThrewException(ex);
//        }
//    }
//}