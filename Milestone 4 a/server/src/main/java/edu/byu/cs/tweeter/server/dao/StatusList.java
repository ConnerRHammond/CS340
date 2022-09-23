package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.Status;

import java.util.ArrayList;

public class StatusList  {
    private ArrayList<Status> array = new ArrayList<>();
    StatusList(){
    }


    public ArrayList<Status> getArray() {
        return array;
    }

    public void setArray(ArrayList<Status> array) {
        this.array = array;
    }
}
