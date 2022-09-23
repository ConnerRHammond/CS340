package edu.byu.cs.tweeter.client.model.service.Tasks;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.ListRequest;
import edu.byu.cs.tweeter.util.Pair;

public abstract class PagedTask<T>  extends BackgroundTask  {

    public static final String ITEMS_KEY = "items";
    public static final String MORE_PAGES_KEY = "more-pages";

    /**
     * The user whose items are being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */

    /**
     * Maximum number of statuses to return (i.e., page size).
     */



    /**
     * The last status returned in the previous page of results (can be null).
     * This allows the new page to begin where the previous page ended.
     */

    /**
     * The items returned in the current page of results.
     */
    private List<T> items;

    /**
     * Indicates whether there are more pages of items that can be retrieved on subsequent calls.
     */
    private boolean hasMorePages;


    protected PagedTask( Handler messageHandler) {
        super(messageHandler);

    }




    @Override
    protected final void runTask() throws IOException, TweeterRemoteException {
        Pair<List<T>, Boolean> pageOfItems = getItems();

        items = pageOfItems.getFirst();
        hasMorePages = pageOfItems.getSecond();

        for(User user : getUsersForItems(items)) {
            BackgroundTaskUtils.loadImage(user);
        }
    }

    protected abstract Pair<List<T>, Boolean> getItems() throws IOException, TweeterRemoteException;

    protected abstract List<User> getUsersForItems(List<T> items);

    @Override
    protected final void loadBundle(Bundle msgBundle) {
        msgBundle.putSerializable(ITEMS_KEY, (Serializable) items);
        msgBundle.putBoolean(MORE_PAGES_KEY, hasMorePages);
    }
}
