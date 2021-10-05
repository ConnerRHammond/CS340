package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.util.FakeData;

public abstract class BackgroundTask  implements Runnable{
    private static final String LOG_TAG = "FollowTask";
    public static final String SUCCESS_KEY = "success";
    public static final String MESSAGE_KEY = "message";
    public static final String EXCEPTION_KEY = "exception";

    protected String errorMessage;
    protected Handler messageqHandler;

    protected BackgroundTask (Handler messageqHandler){this.messageqHandler = messageqHandler;}
    @Override
    public void run() {
        try {
            if(runTask()){
                sendSuccessMessage();
            }
            else{
                sendFailedMessage();
            }

        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
    }
    private void sendSuccessMessage(){
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY,true);
        loadSuccessBundle(msgBundle);
        sendMessage(msgBundle);
    }

    private  void sendFailedMessage(){
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY,true);
        msgBundle.putString(MESSAGE_KEY,this.errorMessage);
        sendMessage(msgBundle);
    }
    private  void sendExceptionMessage(Exception exception){
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY,false);
        msgBundle.putSerializable(EXCEPTION_KEY,exception);
        sendMessage(msgBundle);

    }
    protected void  sendMessage(Bundle msgBundle){
        Message msg = Message.obtain();
        msg.setData(msgBundle);
        messageqHandler.sendMessage(msg);
    }

    protected FakeData getFakeData(){return new FakeData(); }

    protected abstract boolean runTask() throws IOException;

    protected  void loadSuccessBundle(Bundle msgBundle){
        return;
    }



}
