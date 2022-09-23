package edu.byu.cs.tweeter.server.daoInterfaces;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.StatusList;
import edu.byu.cs.tweeter.util.Pair;

public interface StatusDAO {
    String postStatus(String status, List<String> alias,String date);
    Pair<ArrayList<Status>, Boolean>  getStatuses(String alias, ArrayList<String> list);
}
