package edu.byu.cs.tweeter.client.presenter;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.StatusRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.model.net.response.StatusResponse;

public class PostStatusTest {
    public MainActivityPresenter.View mockView;
    public   MainActivityPresenter mainPresenterSpy;

    @Test
    public  void  postStatus() throws IOException, TweeterRemoteException {
        mockView = Mockito.mock(MainActivityPresenter.View.class);
        ServerFacade facade = new ServerFacade();
        LoginRequest request = new LoginRequest("@user0","a");
        LoginResponse response = facade.login(request,"/login");
        Assert.assertTrue(response.isSuccess());

        mainPresenterSpy = Mockito.spy(new MainActivityPresenter(mockView,response.getUser(),response.getAuthToken(),false));

        String post = "This is my test status";
        mainPresenterSpy.postStatus(post);

        Mockito.verify(mockView).displayInfoMessage("Posting");

        StatusRequest request2  = new StatusRequest(response.getAuthToken(),"@user0",10,null);
        StatusResponse response2 = new ServerFacade().GetStatus(request2,"/getstory");

        for (Status status : response2.getItems()){
            if(status.getPost().equals(post)){
                Mockito.verify(mockView).displayInfoMessage("Successfully posted");
                Assert.assertTrue(true);
                return;
            }
        }
        Assert.assertTrue(false);
        return;

    }
}
