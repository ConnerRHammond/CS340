package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PageObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.UserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter extends PagedPresenter<Status>  {

    @Override
    protected void getItems(AuthToken authToken, User targetUser, int limit, Status lastItem) {
        new FeedService().getFeed(authToken,targetUser,PAGE_SIZE,lastItem,this);
    }

    @Override
    protected String getDescription() {
        return "Feed";
    }

    public FeedPresenter(PagedView<Status> view,AuthToken authToken, User targetUser){
        super(view,authToken,targetUser);
    }

    public void selectedFolder(String name){
        view.displayInfoMessage("You selected '" + name + "'.");
    }

}
