package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.Tasks.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PagesHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PageObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryService extends ServiceBase {

    public void getStory(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                         PageObserver<Status>  observer){

        GetStoryTask getStoryTask = new GetStoryTask(authToken,
                targetUser, limit, lastStatus, new GetStoryHandler(observer));
        Execute(getStoryTask);
    }
    private class GetStoryHandler extends PagesHandler<Status> {
        public GetStoryHandler(PageObserver<Status> observer) { super(observer); }
        @Override
        protected String getFailedMessagePrefix() { return "Followers failed to load:"; }
    }
}
