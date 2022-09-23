package edu.byu.cs.tweeter.client.presenter;

import org.junit.Before;
import org.junit.*;
import static org.junit.Assert.*;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.MainActivityService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenterTest {
    public MainActivityPresenter.View mockView;
    public MainActivityService mockMainService;
    public Cache mockCache;
    public   MainActivityPresenter mainPresenterSpy;

    @Before
    public  void setUp(){
        mockView = Mockito.mock(MainActivityPresenter.View.class);
        mockMainService = Mockito.mock(MainActivityService.class);
        mockCache = Mockito.mock(Cache.class);

        User user = new User();
        AuthToken authToken = new AuthToken();
        mainPresenterSpy = Mockito.spy(new MainActivityPresenter(mockView,user,authToken,false));
        Mockito.doReturn(mockMainService).when(mainPresenterSpy).getService();
        Cache.setInstance(mockCache);
    }

    @Test
    public void testpostSucceeds(){
        Answer<Void> postSucceded = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Status status = invocation.getArgument(1);
                assertEquals(status.getPost(),"Test Status");
                MainActivityService.MainActivityObserver observer = invocation.getArgument(2);
                observer.actionSucceded();
                return null;
            }
        };
       Mockito.doAnswer(postSucceded).when(mockMainService).postStatus(Mockito.any(),Mockito.any(),Mockito.any());

      mainPresenterSpy.postStatus("Test Status");
      Mockito.verify(mockView).displayInfoMessage("Posting");
      Mockito.verify(mockView).displayInfoMessage("Successfully posted");

    }
    @Test
    public void testFailed(){
        Answer<Void> postFailed = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Status status = invocation.getArgument(1);
                assertEquals(status.getPost(),"Test Status");
                MainActivityService.MainActivityObserver observer = invocation.getArgument(2);
                observer.actionFailed("Didn't work");
                return null;
            }
        };
        Mockito.doAnswer(postFailed).when(mockMainService).postStatus(Mockito.any(),Mockito.any(),Mockito.any());

        mainPresenterSpy.postStatus("Test Status");
        Mockito.verify(mockView).displayInfoMessage("Posting");
        Mockito.verify(mockView).displayInfoMessage("Didn't work");

    }

    @Test
    public void testThrowException(){
            Answer<Void> postException = new Answer<Void>() {
                @Override
                public Void answer(InvocationOnMock invocation) throws Throwable {
                    Status status = invocation.getArgument(1);
                    assertEquals(status.getPost(),"Test Status");
                    MainActivityService.MainActivityObserver observer = invocation.getArgument(2);
                    observer.actionFailed("Didn't work");
                    return null;
                }
            };
            Mockito.doAnswer(postException).when(mockMainService).postStatus(Mockito.any(),Mockito.any(),Mockito.any());

            mainPresenterSpy.postStatus("Test Status");
            Mockito.verify(mockView).displayInfoMessage("Posting");
            Mockito.verify(mockView).displayInfoMessage("Didn't work");

        }
}

