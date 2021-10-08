//package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;
//
//import android.os.Handler;
//import android.os.Message;
//
//import androidx.annotation.NonNull;
//
//import java.util.List;
//
//import edu.byu.cs.tweeter.client.model.service.StoryService;
//import edu.byu.cs.tweeter.client.model.service.Tasks.GetStoryTask;
//import edu.byu.cs.tweeter.model.domain.Status;
//
//public class GetStoryHandler extends Handler {
//    private StoryService.StoryObserver observer;
//    public GetStoryHandler(StoryService.StoryObserver observer){
//        this.observer = observer;
//    }
//    @Override
//    public void handleMessage(@NonNull Message msg) {
//
//        boolean success = msg.getData().getBoolean(GetStoryTask.SUCCESS_KEY);
//        if (success) {
//            List<Status> statuses = (List<Status>) msg.getData().getSerializable(GetStoryTask.ITEMS_KEY);
//            Boolean hasMorePages = msg.getData().getBoolean(GetStoryTask.MORE_PAGES_KEY);
//
//            Status lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
//            observer.StorySucceeded(statuses,hasMorePages,lastStatus);
//        } else if (msg.getData().containsKey(GetStoryTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(GetStoryTask.MESSAGE_KEY);
//            observer.StoryFailed(message);
//        } else if (msg.getData().containsKey(GetStoryTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(GetStoryTask.EXCEPTION_KEY);
//            observer.StoryThrewException(ex);
//        }
//    }
//}