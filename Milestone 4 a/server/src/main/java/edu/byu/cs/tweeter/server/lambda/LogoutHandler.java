package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.AuthTokenRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.service.LogoutService;

public class LogoutHandler implements RequestHandler<AuthTokenRequest, Response> {

    @Override
    public Response handleRequest(AuthTokenRequest input, Context context) {
        LogoutService service = new LogoutService();
        DynamoDAOFactory factory = new DynamoDAOFactory();
        return service.logOut(input,factory);
    }
}

