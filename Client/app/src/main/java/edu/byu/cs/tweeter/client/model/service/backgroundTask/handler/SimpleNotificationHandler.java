//package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;
//
//import android.os.Bundle;
//
//import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleNotificationServiceObserver;
//
//public abstract  class SimpleNotificationHandler<T extends SimpleNotificationServiceObserver> extends BackgroundTaskHandler <T> {
//
//    public SimpleNotificationHandler(T observer) {
//        super(observer);
//    }
//
//    @Override
//    protected void handleSuccessMessage(T observer, Bundle data) {
//        observer.actionSucceded("Success");
//    }
//}