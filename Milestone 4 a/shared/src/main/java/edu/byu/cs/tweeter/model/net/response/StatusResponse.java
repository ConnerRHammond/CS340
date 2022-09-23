package edu.byu.cs.tweeter.model.net.response;

import java.util.List;


import edu.byu.cs.tweeter.model.domain.Status;


public class StatusResponse extends PagedResponse<Status>{

    public StatusResponse(){

    }
    public StatusResponse(List<Status> statuses, boolean hasMorePages) {
        super(statuses, hasMorePages);

    }
    public  StatusResponse(boolean success , String message){
        super(success,message);
    }

}
