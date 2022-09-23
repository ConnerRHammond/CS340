package edu.byu.cs.tweeter.client.model.service;


import edu.byu.cs.tweeter.client.model.service.Tasks.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PagesHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PageObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedService extends ServiceBase {


    public void getFeed(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                         PageObserver<Status> observer){
        GetFeedTask getFeedTask = new GetFeedTask(authToken,
                targetUser, limit, lastStatus, new GetFeedHandler(observer));
        Execute(getFeedTask);

    }
    private class GetFeedHandler extends PagesHandler<Status> {
        public GetFeedHandler(PageObserver<Status> observer) { super(observer); }
        @Override
        protected String getFailedMessagePrefix() { return "Feed failed to load:"; }
    }


}
