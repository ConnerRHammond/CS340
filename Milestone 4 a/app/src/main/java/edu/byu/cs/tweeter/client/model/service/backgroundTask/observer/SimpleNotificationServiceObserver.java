package edu.byu.cs.tweeter.client.model.service.backgroundTask.observer;

public interface SimpleNotificationServiceObserver extends ServiceObserver{
    void actionSucceded(String Message);
}
