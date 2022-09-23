package edu.byu.cs.tweeter.client.model.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PageObserver;
import edu.byu.cs.tweeter.client.model.service.services.StoryService;
import edu.byu.cs.tweeter.client.presenter.MainActivityPresenter;
import edu.byu.cs.tweeter.client.presenter.PagedPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.AuthenticatedRequest;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.CountResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.UsersResponse;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MileStone3Test {
    private  ServerFacade fascade ;
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private User user1;
    private CountDownLatch countDownLatch;
    @Before
    public void setUp(){
        fascade = new ServerFacade();
        user1 = new User("Allen", "Anderson", "@allen", MALE_IMAGE_URL);
                resetCountDownLatch();
    }

    private void resetCountDownLatch() {
        countDownLatch = new CountDownLatch(1);
    }

    private void awaitCountDownLatch() throws InterruptedException {
        countDownLatch.await();
        resetCountDownLatch();
    }
    @Test
    public void RegisterTest() {
        try {
            RegisterRequest request = new RegisterRequest("h", "J", "h", "j", "s");
            LoginResponse response = fascade.Register(request, "/register");
            Assert.assertEquals(response.getUser().getLastName(),user1.getLastName());
            Assert.assertEquals(response.getUser().getFirstName(),user1.getFirstName());
        }
        catch (Exception e){

        }
    }
    @Test
    public void TestGetFollowers() throws IOException, TweeterRemoteException {
        FakeData data = new FakeData();
        Pair<List<User>, Boolean> pair=  data.getPageOfUsers(user1, 10,null);

        FollowRequest request = new FollowRequest(new AuthToken(),user1,10,null);
        UsersResponse response = fascade.GetUsers(request,"/getfollowers");
        Assert.assertEquals(pair.getFirst().get(0),response.getItems().get(0));
    }
    @Test
    public void TestGetFollowingCount() throws IOException, TweeterRemoteException {
        AuthenticatedRequest request = new AuthenticatedRequest(new AuthToken(),user1);
        CountResponse response = fascade.GetFollowersCount(request,"/getfollowerscount");
        Assert.assertEquals(20,response.getCount());
    }
    @Test
    public void TestStatusService() throws InterruptedException {
        Tester observer = new Tester();
        Tester mockObserver = Mockito.spy(observer);
        StoryService storyService = new StoryService();
        storyService.getStory(new AuthToken(),user1,10,null,mockObserver);
        awaitCountDownLatch();
        Mockito.verify(mockObserver).itemSucceeded(Mockito.any(),Mockito.any(),Mockito.any());
    }

    private class Tester implements PageObserver {

        @Override
        public void itemSucceeded(List items, Boolean hasMorePages, Object item) {
            assertNotNull(items);
            countDownLatch.countDown();
        }

        @Override
        public void actionFailed(String message) {

        }
    }

}
