//package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;
//
//import android.os.Handler;
//import android.os.Message;
//
//import androidx.annotation.NonNull;
//
//import java.util.List;
//
//import edu.byu.cs.tweeter.client.model.service.FeedService;
//import edu.byu.cs.tweeter.client.model.service.Tasks.GetFeedTask;
//import edu.byu.cs.tweeter.client.model.service.Tasks.GetStoryTask;
//import edu.byu.cs.tweeter.model.domain.Status;
//
//public class GetFeedHandler extends Handler {
//    private FeedService.FeedObserver observer;
//    public GetFeedHandler(FeedService.FeedObserver observer){
//        this.observer = observer;
//    }
//    @Override
//    public void handleMessage(@NonNull Message msg) {
//        boolean success = msg.getData().getBoolean(GetFeedTask.SUCCESS_KEY);
//        if (success) {
//            List<Status> statuses = (List<Status>) msg.getData().getSerializable(GetStoryTask.ITEMS_KEY);
//            Boolean hasMorePages = msg.getData().getBoolean(GetStoryTask.MORE_PAGES_KEY);
//
//            Status lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
//            observer.FeedSucceeded(statuses,hasMorePages,lastStatus);
//        } else if (msg.getData().containsKey(GetFeedTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(GetStoryTask.MESSAGE_KEY);
//            observer.FeedFailed(message);
//        } else if (msg.getData().containsKey(GetFeedTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(GetStoryTask.EXCEPTION_KEY);
//            observer.FeedThrewException(ex);            }
//    }
//}