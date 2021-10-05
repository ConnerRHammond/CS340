package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.Serializable;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PageTask <T> extends AuthenticatedTask {
    public static final String ITEMS_KEY = "items";
    public static final String MORE_PAGES_KEY = "more-pages";
    protected User targetUser;
    protected  int limit;
    protected  T lastItem;
    protected List<T> items;
    protected  boolean hasMorePages;

    public PageTask(AuthToken authToken, User targetUser, int limit, T lastItem, Handler messageHandler) {
        super(authToken, messageHandler);

        this.targetUser = targetUser;
        this.limit = limit;
        this.lastItem = lastItem;
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle){
        msgBundle.putSerializable(ITEMS_KEY,(Serializable)this.items);
        msgBundle.putBoolean(MORE_PAGES_KEY,this.hasMorePages);
    }
}
