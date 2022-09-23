package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.AuthenticatedRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.service.LoginService;
import edu.byu.cs.tweeter.server.service.PostService;
import edu.byu.cs.tweeter.server.util.JsonSerializer;

public class PostHandler implements RequestHandler<PostStatusRequest, Response> {

    @Override
    public Response handleRequest(PostStatusRequest input, Context context) {
        System.out.print((JsonSerializer.serialize(input)));
        DynamoDAOFactory factory = new DynamoDAOFactory();
        return new PostService().doPost(input,factory);
    }

//    public static void main(String[] args) {
//        LoginService LoginService = new LoginService(new LoginRequest("@allen","a"));
//        LoginResponse respon= LoginService.login();
//        PostStatusRequest req = new PostStatusRequest();
//        req.setAuthToken(respon.getAuthToken());
//        Calendar calendar = new GregorianCalendar();
//        calendar.add(Calendar.MINUTE, 1);
//        req.setStatus( new Status("I need to check to see if posts works so this might show up a bunch of times sorry TA's",new User("Allen", "Anderson", "@allen", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"),
//                calendar.getTime().toString(),new ArrayList<String>(), new ArrayList<String>()));
//        Response response = new PostService().doPost(req);
//        System.out.print(response);
//        System.out.print(response.getMessage());
//        System.out.print(response.isSuccess());
//    }
}
