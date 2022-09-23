package edu.byu.cs.tweeter.server.util;

import com.sun.corba.se.impl.copyobject.FallbackObjectCopierImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.dao.DynamoFeedDAO;
import edu.byu.cs.tweeter.server.dao.DynamoFollowDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.AuthtokenDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.FollowsDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.StatusDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.StoryDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.UsersDAO;
import edu.byu.cs.tweeter.server.service.LoginService;
import edu.byu.cs.tweeter.server.service.PostService;

public class CreateUsersDynamo {


    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    /**
     * Generated users.
     */
    private static final User user1 = new User("Allen", "Anderson", "@allen", MALE_IMAGE_URL);
    private static final User user2 = new User("Amy", "Ames", "@amy", FEMALE_IMAGE_URL);
    private static final User user3 = new User("Bob", "Bobson", "@bob", MALE_IMAGE_URL);
    private static final User user4 = new User("Bonnie", "Beatty", "@bonnie", FEMALE_IMAGE_URL);
    private static final User user5 = new User("Chris", "Colston", "@chris", MALE_IMAGE_URL);
    private static final User user6 = new User("Cindy", "Coats", "@cindy", FEMALE_IMAGE_URL);
    private static final User user7 = new User("Dan", "Donaldson", "@dan", MALE_IMAGE_URL);
    private static final User user8 = new User("Dee", "Dempsey", "@dee", FEMALE_IMAGE_URL);
    private static final User user9 = new User("Elliott", "Enderson", "@elliott", MALE_IMAGE_URL);
    private static final User user10 = new User("Elizabeth", "Engle", "@elizabeth", FEMALE_IMAGE_URL);
    private static final User user11 = new User("Frank", "Frandson", "@frank", MALE_IMAGE_URL);
    private static final User user12 = new User("Fran", "Franklin", "@fran", FEMALE_IMAGE_URL);
    private static final User user13 = new User("Gary", "Gilbert", "@gary", MALE_IMAGE_URL);
    private static final User user14 = new User("Giovanna", "Giles", "@giovanna", FEMALE_IMAGE_URL);
    private static final User user15 = new User("Henry", "Henderson", "@henry", MALE_IMAGE_URL);
    private static final User user16 = new User("Helen", "Hopwell", "@helen", FEMALE_IMAGE_URL);
    private static final User user17 = new User("Igor", "Isaacson", "@igor", MALE_IMAGE_URL);
    private static final User user18 = new User("Isabel", "Isaacson", "@isabel", FEMALE_IMAGE_URL);
    private static final User user19 = new User("Justin", "Jones", "@justin", MALE_IMAGE_URL);
    private static final User user20 = new User("Jill", "Johnson", "@jill", FEMALE_IMAGE_URL);
    private static final User user21 = new User("John", "Brown", "@john", MALE_IMAGE_URL);

    private static final List<User> allUsers = Arrays.asList(
            user2, user3, user4, user5, user6, user7, user8, user9, user10, user11,
            user12, user13, user14, user15, user16, user17, user18, user19, user20, user21
    );

    public static void main(String[] args) {
        DynamoDAOFactory factory = new DynamoDAOFactory();
        Calendar calendar = new GregorianCalendar();
        FollowsDAO followsDAO = new DynamoFollowDAO();
//        usersDAO.createUser("@conner", "conner", "hammond", MALE_IMAGE_URL, "a");
//        followsDAO.Follow("@user0","@amy");
//        followsDAO.Follow( "@amy", "@user0");
        for (User user : allUsers) {
//////            usersDAO.createUser(user.getAlias(),user.getFirstName(),user.getLastName(),user.getImageUrl(),"a");
////            usersDAO.incrementFollowers("@conner");
////            followsDAO.Follow("@conner", user.getAlias());
////            followsDAO.Follow(user.getAlias(), "@allen");
////        }
////    }
////}
            List<String> mentions = Collections.singletonList("@allen");
            String url = "https://byu.edu";
            List<String> urls = Collections.singletonList(url);
            String post = "third post for ya " +
                    "\nMy friend " + "@allen" + " likes this website" +
                    "\n" + url;
            calendar.add(Calendar.MINUTE, 1);
            String datetime = calendar.getTime().toString();
            Status status = new Status(post, user, datetime, urls, mentions);
            LoginService LoginService = new LoginService(new LoginRequest(user.getAlias(), "a"));
            LoginResponse respon = LoginService.login(new DynamoDAOFactory());
            if (respon.isSuccess() && respon.getAuthToken() != null && !respon.getAuthToken().getToken().contains("ail")) {
                System.out.print(respon.getAuthToken().getToken());
                PostStatusRequest input = new PostStatusRequest(status, respon.getAuthToken(), status.getDate());
                new PostService().doPost(input, factory);
////                feedDAO.postOne(JsonSerializer.serialize(status),"@allen",status.getDate());
//            }
//        }
            }
        }
    }
}
//////
//            DynamoDAOFactory factory = new DynamoDAOFactory();
//            UsersDAO usersDAO = factory.getUserDAO();
//            AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();
//            FollowsDAO followsDAO = factory.getFollowDAO();
//            StatusDAO storyDAO = factory.getStoryDAO();
//            StatusDAO feedDAO = factory.getFeedDAO();
//            Calendar calendar = new GregorianCalendar();
////            storyDAO.createStatuses(user1);
//            for (User user : allUsers){
////                usersDAO.createUser(user.getAlias(),user.getFirstName(),user.getLastName(),user.getImageUrl(),"a");
////            followsDAO.Follow("@allen",user.getAlias());
////            followsDAO.Follow(user.getAlias(),"@allen");
////            storyDAO.createStatuses(user);
//                List<String> mentions = Collections.singletonList(user.getAlias());
//                String url = "https://byu.edu";
//                List<String> urls = Collections.singletonList(url);
//                String post = "Hello "+ user.getAlias()+
//                        "I like this website" +
//                        "\n" + url;
//                calendar.add(Calendar.MINUTE, 1);
//                String datetime = calendar.getTime().toString();
//                Status status = new Status(post, user, datetime, urls, mentions);
////                storyDAO.postStatus(JsonSerializer.serialize(status),"@allen",status.getDate());
////                feedDAO.postStatus(status,user.getAlias(),status.getDate());
//        }
//    }

//}
