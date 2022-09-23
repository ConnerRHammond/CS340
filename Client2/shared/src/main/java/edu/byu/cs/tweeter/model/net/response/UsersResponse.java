package edu.byu.cs.tweeter.model.net.response;

import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;

/**
 * A paged response for a {@link FollowingRequest}.
 */
public class UsersResponse extends PagedResponse<User>{

    public UsersResponse() {

    }

    public UsersResponse(List<User> followees, boolean hasMorePages) {
        super(followees, hasMorePages);
    }


}
