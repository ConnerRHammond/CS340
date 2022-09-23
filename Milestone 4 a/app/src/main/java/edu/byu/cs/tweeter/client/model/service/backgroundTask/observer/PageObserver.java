package edu.byu.cs.tweeter.client.model.service.backgroundTask.observer;

import java.util.List;

public interface PageObserver<T> extends ServiceObserver {
    void itemSucceeded(List<T> items, Boolean hasMorePages, T item);
}
