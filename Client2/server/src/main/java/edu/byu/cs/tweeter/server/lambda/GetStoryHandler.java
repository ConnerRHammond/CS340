package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.StatusRequest;
import edu.byu.cs.tweeter.model.net.response.StatusResponse;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.service.LoginService;
import edu.byu.cs.tweeter.server.service.StatusService;
import edu.byu.cs.tweeter.server.util.JsonSerializer;

public class GetStoryHandler implements RequestHandler<StatusRequest, StatusResponse> {
    @Override
    public StatusResponse handleRequest(StatusRequest request, Context context) {
        StatusService service = new StatusService();
        System.out.print((JsonSerializer.serialize(request)));
        DynamoDAOFactory factory = new DynamoDAOFactory();
        return service.getStory(request,factory);
    }

//    public static void main(String[] args) {
//        LoginService LoginService = new LoginService(new LoginRequest("@allen","a"));
//        LoginResponse respon= LoginService.login();
//        FollowingRequest followingRequest = new FollowingRequest(respon.getAuthToken(),"@allen",0,"@amy");
//        StatusRequest request = new StatusRequest(followingRequest.getAuthToken(),"@allen",10,null);
//        StatusService service = new StatusService();
//        StatusResponse response = service.getStory(request);
//        System.out.print(response.getItems());
//        System.out.print(response.getHasMorePages());
//    }
}
