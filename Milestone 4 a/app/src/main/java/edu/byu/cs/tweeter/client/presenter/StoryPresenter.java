package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.services.FeedService;
import edu.byu.cs.tweeter.client.model.service.services.StoryService;
import edu.byu.cs.tweeter.client.model.service.services.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PageObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.UserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends PagedPresenter<Status> {

    @Override
    protected void getItems(AuthToken authToken, User targetUser, int limit, Status lastItem) {
        new StoryService().getStory(authToken,targetUser,PAGE_SIZE,lastItem,this);
    }

    @Override
    protected String getDescription() {
        return "Story";
    }

    public StoryPresenter(PagedView<Status> view,AuthToken authToken, User targetUser){
        super(view,authToken,targetUser);
    }

}
