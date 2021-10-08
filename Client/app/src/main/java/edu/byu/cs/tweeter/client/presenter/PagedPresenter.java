package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.Views.View;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PageObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.UserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<T> extends presenter<PagedPresenter.PagedView<T>>implements PageObserver,UserObserver {
    private  static  final  String LOG_TAG = "PagedPresenter";
    protected   static final int PAGE_SIZE = 10;
    private UserService userService;
    protected User targetUser;
    protected AuthToken authToken;

    protected T lastItem;
    protected boolean hasMorePages = true;
    protected  boolean isLoading = false;
    protected boolean isGettingUser = false;


    public interface  PagedView <U> extends View {
        void setLoading(Boolean value);
        void addItems(List<U> newItems);
        void navigateToUrl(String clickable);
    }

    protected PagedPresenter(PagedView<T> view, AuthToken authToken,User targetUser){
        super(view);
        this.userService = new UserService();
        this.authToken = authToken;
        this.targetUser = targetUser;
    }

    protected PagedView <T> getPagedView(){ return (PagedView<T>) view;}

    private  PagedView getView(){return (PagedView) view;}

    public void loadMoreItems(){
        if(!isLoading && hasMorePages){
            isLoading = true;
            getView().setLoading(true);

            getItems(authToken,targetUser,PAGE_SIZE,lastItem);
        }
    }
    protected abstract void getItems(AuthToken authToken, User targetUser,int limit,T lastItem);

    public void getUser(String alias){
        if(!isGettingUser){
            isGettingUser = true;

            userService.getUser(Cache.getInstance().getCurrUserAuthToken(), alias, this);
        }
    }

    protected abstract String getDescription();

    @Override
    public void actionFailed(String message) {
        String errorMessage = "Failed to retrieve" + getDescription()+ message;
        view.setLoading(false);
        view.displayInfoMessage(errorMessage);
        this.isLoading = false;
    }
    @Override
    public void userSucceeded(AuthToken authToken,User user) {
        view.navigateToUser(user);
    }
    @Override
    public void itemSucceeded(List items, Boolean hasMorePages, Object item) {
        this.lastItem = (T) item;
        this.hasMorePages = hasMorePages;
        view.setLoading(false);
        view.addItems(items);
        this.isLoading = false;
    }
    public void clicked(String clickable){
        if (clickable.contains("http")) {
            view.navigateToUrl(clickable);
        } else {
            new UserService().getUser(authToken,clickable,this);
            view.displayInfoMessage("Getting user's profile...");
        }
    }
}
