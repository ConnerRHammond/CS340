package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.AuthenticatedRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.CountResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.service.FollowTaskService;
import edu.byu.cs.tweeter.server.service.GetCountService;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.service.LoginService;

public class FollowHandler implements RequestHandler<AuthenticatedRequest, Response> {
    @Override
    public Response handleRequest(AuthenticatedRequest request, Context context) {
        DynamoDAOFactory factory = new DynamoDAOFactory();
        FollowTaskService service = new FollowTaskService();
        return service.Follow(request,factory);
    }
    public static void main(String[] args) {
//        LoginService LoginService = new LoginService(new LoginRequest("@allen","a"));
//        LoginResponse response= LoginService.login();
//        AuthenticatedRequest request = new AuthenticatedRequest();
//        request.setAuthToken(new AuthToken(response.getAuthToken().getToken()));
//        request.setUser( new User("amy","something","@amy","something"));
//        FollowTaskService service = new FollowTaskService();
//        Response response1 = service.Follow(request);
//        System.out.print(response1);
    }
}
