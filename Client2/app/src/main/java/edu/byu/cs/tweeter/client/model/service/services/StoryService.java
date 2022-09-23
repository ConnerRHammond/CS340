package edu.byu.cs.tweeter.client.model.service.services;

import android.os.Looper;

import edu.byu.cs.tweeter.client.model.service.Tasks.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PagesHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PageObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.StatusRequest;

public class StoryService extends ServiceBase {

    public void getStory(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                         PageObserver<Status>  observer){

        StatusRequest request = new StatusRequest(authToken,targetUser.getAlias(),limit,lastStatus);
        GetStoryTask getStoryTask = new GetStoryTask(request,new GetStoryHandler(Looper.getMainLooper(),observer));
        Execute(getStoryTask);
    }
    private class GetStoryHandler extends PagesHandler<Status> {
        public GetStoryHandler(Looper looper,PageObserver<Status> observer) { super(looper,observer); }
        @Override
        protected String getFailedMessagePrefix() { return "Followers failed to load:"; }
    }
}
