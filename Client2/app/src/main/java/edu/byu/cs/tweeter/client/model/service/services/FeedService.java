package edu.byu.cs.tweeter.client.model.service.services;


import edu.byu.cs.tweeter.client.model.service.Tasks.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PagesHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PageObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.StatusRequest;

public class FeedService extends ServiceBase {


    public void getFeed(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                         PageObserver<Status> observer){
        StatusRequest request = new StatusRequest(authToken,targetUser.getAlias(),limit,lastStatus);
        GetFeedTask getFeedTask = new GetFeedTask(request, new GetFeedHandler(observer));
        Execute(getFeedTask);

    }
    private class GetFeedHandler extends PagesHandler<Status> {
        public GetFeedHandler(PageObserver<Status> observer) { super(observer); }
        @Override
        protected String getFailedMessagePrefix() { return "Feed failed to load:"; }
    }


}
