package edu.byu.cs.tweeter.server.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.AuthtokenDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.FollowsDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.StatusDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.UsersDAO;
import edu.byu.cs.tweeter.util.Pair;

public class Create1000Users {
    public static void main(String[] args) {
        DynamoDAOFactory factory = new DynamoDAOFactory();
        FollowsDAO followsDAO = factory.getFollowDAO();
        UsersDAO usersDAO =factory.getUserDAO();
//        int i = 1812;
        int i =100;
        while(i < 1000){
//            usersDAO.createUser("@user"+i,"user"+i,"user"+ i  ,"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png","a");
            followsDAO.Follow("@conner","@user"+i);
            i++;
            System.out.print(i +"\n");
        }
        Pair<List<String>, Boolean> pair=followsDAO.getFollowers("@allen",0,null);
        System.out.print(pair.getFirst().size());
    }
}
