//package edu.byu.cs.tweeter.server.service;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.util.Arrays;
//
//import edu.byu.cs.tweeter.model.domain.AuthToken;
//import edu.byu.cs.tweeter.model.domain.User;
//import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
//import edu.byu.cs.tweeter.model.net.response.UsersResponse;
//import edu.byu.cs.tweeter.server.dao.FollowDAO;
//
//public class FollowServiceTest {
//
//    private FollowingRequest request;
//    private UsersResponse expectedResponse;
//    private FollowDAO mockFollowDAO;
//    private UserService userServiceSpy;
//
//    @BeforeEach
//    public void setup() {
//        AuthToken authToken = new AuthToken();
//
//        User currentUser = new User("FirstName", "LastName", null);
//
//        User resultUser1 = new User("FirstName1", "LastName1",
//                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//        User resultUser2 = new User("FirstName2", "LastName2",
//                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
//        User resultUser3 = new User("FirstName3", "LastName3",
//                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
//
//        // Setup a request object to use in the tests
//        request = new FollowingRequest(authToken, currentUser.getAlias(), 3, null);
//
//        // Setup a mock FollowDAO that will return known responses
//        expectedResponse = new UsersResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
//        mockFollowDAO = Mockito.mock(FollowDAO.class);
//        Mockito.when(mockFollowDAO.getFollowees(request)).thenReturn(expectedResponse);
//
//        userServiceSpy = Mockito.spy(UserService.class);
//        //Mockito.when(userServiceSpy.getFollowingDAO()).thenReturn(mockFollowDAO);
//    }
//
//    /**
//     * Verify that the {@link UserService#getFollowees(FollowingRequest)}
//     * method returns the same result as the {@link FollowDAO} class.
//     */
////    @Test
////    public void testGetFollowees_validRequest_correctResponse() {
////        UsersResponse response = userServiceSpy.getFollowees(request);
////        Assertions.assertEquals(expectedResponse, response);
////    }
//}
