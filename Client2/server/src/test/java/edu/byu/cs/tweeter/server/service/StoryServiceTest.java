package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.dynamodbv2.xspec.S;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.StatusRequest;
import edu.byu.cs.tweeter.model.net.response.StatusResponse;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.AuthtokenDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.DAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.FollowsDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.StatusDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.UsersDAO;
import edu.byu.cs.tweeter.util.Pair;

public class StoryServiceTest {
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
    public   ArrayList<Status> statuses;
    @BeforeEach
    public void setUp(){
        Calendar calendar = new GregorianCalendar();
        statuses= new ArrayList<Status>();
        for (User user : allUsers) {
            List<String> mentions = Collections.singletonList("@allen");
            String url = "https://byu.edu";
            List<String> urls = Collections.singletonList(url);
            String post = "third post for ya " +
                    "\nMy friend " + "@allen" + " likes this website" +
                    "\n" + url;
            calendar.add(Calendar.MINUTE, 1);
            String datetime = calendar.getTime().toString();
            Status status = new Status(post, user, datetime, urls, mentions);
            statuses.add(status);
        }
    }
    @Test
    public void testStoryService(){
        authTokenDAO authTokenDAO = new authTokenDAO();
        mockDAO mock = new mockDAO();
        DynamoDAOFactory storyServiceSpy = Mockito.spy(DynamoDAOFactory.class);

        Mockito.doReturn(authTokenDAO).when(storyServiceSpy).getAuthtokenDAO();
        Mockito.doReturn(mock).when(storyServiceSpy).getStoryDAO();

        StatusService service = new StatusService();
        StatusRequest request = new StatusRequest(new AuthToken("asdas"),"random",10,null);
        StatusResponse  response = service.getStory(request,storyServiceSpy);

        Assertions.assertTrue(response.getHasMorePages());
        String post1 = response.getItems().get(0).getPost();
        String post2 =statuses.get(0).getPost();
        Assertions.assertEquals(post1,post2);

        request = new StatusRequest(new AuthToken("asdas"),"random",10,response.getItems().get(9));
        response = service.getStory(request,storyServiceSpy);

        Assertions.assertFalse(response.getHasMorePages());
        post1 = response.getItems().get(response.getItems().size()-1).getPost();
        post2 =statuses.get(statuses.size()-1).getPost();
        Assertions.assertEquals(post1,post2);
    }

    public  class mockDAO implements StatusDAO{

        public mockDAO() {
            super();
        }

        @Override
        public String postStatus(String status, List<String> alias, String date) {
            return null;
        }

        @Override
        public Pair<ArrayList<Status>, Boolean> getStatuses(String alias, ArrayList<String> list) {
            if (list == null){
                ArrayList<Status> newList = new ArrayList<>();
                newList.addAll(statuses.subList(0,10));
                return new Pair<ArrayList<Status>, Boolean> (newList,true);
            }
            else{
                ArrayList<Status> newList = new ArrayList<>();
                newList.addAll(statuses.subList(10,statuses.size()-1));
                return new Pair<ArrayList<Status>, Boolean> (newList,false);
            }
        }
    }
    public  class  authTokenDAO implements AuthtokenDAO{

        public authTokenDAO() {
            super();
        }

        @Override
        public double getTokenTime(String authtoken) {
            return -1;
        }

        @Override
        public String updateToken(String alias, String authtoken) {
            return null;
        }

        @Override
        public String createToken(String alias, String authtoken) {
            return null;
        }

        @Override
        public String removeToken(String authtoken) {
            return null;
        }

        @Override
        public String getTokenAlias(String authtoken) {
            return null;
        }

        @Override
        public Pair<String, Double> getAliasToken(String authtoken) {
            return null;
        }
    }
}
