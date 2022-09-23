package edu.byu.cs.tweeter.server.service;

import java.util.Date;

import edu.byu.cs.tweeter.util.FakeData;

public abstract class BaseService {
    protected FakeData getFakeData() {
        return new FakeData();
    }
    public  boolean checkDate(double time){
        if(new Date().getTime() < time + 30000000){
            return true;
        }
        else if (time == -1){
            return true;
        }
        return false;
    }
}
