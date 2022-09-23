package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.StatusRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;
import edu.byu.cs.tweeter.model.net.response.StatusResponse;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.service.LoginService;
import edu.byu.cs.tweeter.server.service.StatusService;
import edu.byu.cs.tweeter.server.util.JsonSerializer;

public class GetFeedHandler  implements RequestHandler<StatusRequest, StatusResponse> {
    @Override
    public StatusResponse handleRequest(StatusRequest request, Context context) {
        StatusService service = new StatusService();
        System.out.print("Last Item :" +request.getItem());
        System.out.print((JsonSerializer.serialize(request)));
        DynamoDAOFactory factory = new DynamoDAOFactory();
        return service.getFeed(request,factory);
    }

    public static void main(String[] args) {
//        LoginService LoginService = new LoginService(new LoginRequest("@allen","a"));
//        LoginResponse respon= LoginService.login();
//        StatusRequest request = new StatusRequest(respon.getAuthToken(),"@allen",10,null);
//        StatusService service = new StatusService();
//        StatusResponse response = service.getFeed(request);
//
//        System.out.print(response.getItems()+"\n\n\n");
//        System.out.print(response.getHasMorePages()+"\n\n\n");
//
//        request = new StatusRequest(respon.getAuthToken(),"@allen",10,response.getItems().get(9));
//        service = new StatusService();
//        response = service.getFeed(request);
//
//        System.out.print(response.getItems()+"\n\n\n");
//        System.out.print(response.getHasMorePages()+"\n\n\n");

//        request = new StatusRequest(respon.getAuthToken(),new User("allen","something","@allen","something"),10,response.getItems().get(9));
//        service = new StatusService();
//        response = service.getFeed(request);
//
//        System.out.print(response.getItems()+"\n\n\n");
//        System.out.print(response.getHasMorePages()+"\n\n\n");
    }
}
