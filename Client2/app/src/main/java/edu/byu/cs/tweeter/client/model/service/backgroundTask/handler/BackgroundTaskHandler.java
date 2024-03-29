package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.Tasks.BackgroundTask;
import edu.byu.cs.tweeter.client.model.service.Tasks.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.ServiceObserver;

public abstract class BackgroundTaskHandler<T extends ServiceObserver> extends Handler {

    private final T observer;

    public BackgroundTaskHandler(T observer) {
        this.observer = observer;
    }
    public BackgroundTaskHandler(Looper looper, T observer) {
        super(looper);
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(BackgroundTask.SUCCESS_KEY);
        if (success) {
            handleSuccessMessage(observer, msg.getData());
        } else if (msg.getData().containsKey(BackgroundTask.MESSAGE_KEY)) {
            String message = getFailedMessagePrefix() + ": " + msg.getData().getString(GetFollowersCountTask.MESSAGE_KEY);
            observer.actionFailed(message);
        } else if (msg.getData().containsKey(BackgroundTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(BackgroundTask.EXCEPTION_KEY);
            String message = getFailedMessagePrefix() + " because of exception: " + ex.getMessage();
            observer.actionFailed(message);
        }
    }

    protected abstract String getFailedMessagePrefix();

    protected abstract void handleSuccessMessage(T observer, Bundle data);

}
