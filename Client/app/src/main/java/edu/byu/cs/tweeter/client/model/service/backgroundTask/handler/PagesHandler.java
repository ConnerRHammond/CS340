package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.Tasks.PagedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PageObserver;

public abstract class PagesHandler <T> extends BackgroundTaskHandler <PageObserver<T>> {


    public PagesHandler(PageObserver<T> observer) {
        super(observer);
    }
    @Override
    public void handleSuccessMessage(PageObserver<T> observer, Bundle data){
        List<T> items = (List<T>) data.getSerializable(PagedTask.ITEMS_KEY);
        Boolean hasMorePages = data.getBoolean(PagedTask.MORE_PAGES_KEY);
        T item = (items.size() > 0) ? items.get(items.size() - 1) : null;
        observer.itemSucceeded(items,hasMorePages,item);
    }
}
