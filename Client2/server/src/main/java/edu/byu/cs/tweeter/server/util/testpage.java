package edu.byu.cs.tweeter.server.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FeedSQSMessage;
import edu.byu.cs.tweeter.server.dao.DynamoFeedDAO;
import edu.byu.cs.tweeter.server.dao.DynamoFollowDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.FollowsDAO;
import edu.byu.cs.tweeter.server.service.SQSFeedService;
import edu.byu.cs.tweeter.util.Pair;

public class testpage {
    public static void main(String[] args) {

        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MINUTE, 1);
        Status status = new Status("Brand new post cross your fingers", new User("conner", "hammond", "@conner", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"),
                calendar.getTime().toString(), new ArrayList<String>(), new ArrayList<String>());

        String json = JsonSerializer.serialize(status);
        status = JsonSerializer.deserialize(json,Status.class);
        SQSFeedService service = new SQSFeedService();
        FollowsDAO dao = new DynamoFollowDAO();
        String alias = status.getUser().getAlias();
        boolean hasMorePages = false;
        ArrayList key = null;
        do {
            Pair<List<String>, Boolean> pair = dao.getFollowers(alias, 25, key);
            hasMorePages = pair.getSecond();
            if(hasMorePages){
                if(key == null){
                    key = new ArrayList();
                }
                key.add(0,alias);
                key.add(1,pair.getFirst().get(pair.getFirst().size()-1));
            }
            service.batchWrite(new FeedSQSMessage(pair.getFirst(),json,status.getDate()),new DynamoFeedDAO());
        }while (hasMorePages);
    }
}


