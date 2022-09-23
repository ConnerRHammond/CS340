package edu.byu.cs.tweeter.server.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.dao.DynamoFollowDAO;
import edu.byu.cs.tweeter.server.service.LoginService;
import edu.byu.cs.tweeter.server.service.PostService;
import edu.byu.cs.tweeter.server.service.SQSFollowerService;

public class PossStatusTest {
    public static void main(String[] args) {
        LoginService LoginService = new LoginService(new LoginRequest("@conner", "a"));
        LoginResponse respon = LoginService.login(new DynamoDAOFactory());
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MINUTE, 1);
        Status status = new Status("about time to test my 1000", new User("conner", "hammond", "@conner", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"),
                calendar.getTime().toString(), new ArrayList<String>(), new ArrayList<String>());

        PostStatusRequest request = new PostStatusRequest(status, respon.getAuthToken(), status.getDate());
        Response response = new PostService().doPost(request, new DynamoDAOFactory());
        System.out.print(response);
        System.out.print(response.getMessage());
        System.out.print(response.isSuccess());
//        SQSFollowerService service = new SQSFollowerService();
//        service.gatherFollowersForFeed(JsonSerializer.serialize(status), "@conner", status.getDate(), new DynamoFollowDAO());
    }
}
