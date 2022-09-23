package edu.byu.cs.tweeter.client.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.services.MainActivityService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainActivityPresenter extends presenter <MainActivityPresenter.View>implements MainActivityService.MainActivityObserver, MainActivityService.FollowButtonObserver {

    private AuthToken authToken;
    private User selectedUser;
    private boolean doStuff;
    public  interface  View extends edu.byu.cs.tweeter.client.model.service.backgroundTask.Views.View {
        void updateFollowButton(boolean removed);
        void setEnabled(Boolean enabled);
        void setFollowerCount(String count);
        void setFolloweeCount(String count);
        void logOutUser();
        void setVisibulity(Boolean value);
    }
    public int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }
    public List<String> parseURLs(String post) throws MalformedURLException {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }
    public List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);
                containedMentions.add(word);
            }
        }
        return containedMentions;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void postStatus(String post){
        try {
            view.displayInfoMessage("Posting");
            Status newStatus = new Status(post,Cache.getInstance().getCurrUser(), getFormattedDateTime() , parseURLs(post), parseMentions(post));
            getService().postStatus(authToken,newStatus,this);
        }catch (Exception e){
            view.displayInfoMessage("Error: "+ e.getMessage());
        }
    }
    public  void logOut(){
        new MainActivityService().logOut(authToken,this);
    }
    public void follow(){
        new MainActivityService().followTask(authToken,selectedUser,this);
    }
    public  void unFollow(){
        new MainActivityService().unFollowTask(authToken,selectedUser,this);
    }
    public MainActivityPresenter(MainActivityPresenter.View view, User selectedUser,AuthToken authToken, boolean doStuff){
        super(view);
        this.doStuff = doStuff;
        this.authToken = authToken;
        this.selectedUser = selectedUser;
    }
    public void checkSelectedTarget(){
        User currentUser = Cache.getInstance().getCurrUser();
        if (selectedUser.compareTo(currentUser) == 0) {
            view.setVisibulity(true);
        } else {
            view.setVisibulity(false);
            new MainActivityService().isFollower(authToken,currentUser,selectedUser,this);
        }
    }
    public MainActivityService getService(){
        return new MainActivityService();
    }
    public void updateSelectedUserFollowingAndFollowers(){ if (doStuff){new MainActivityService().updateSelectedUserFollowingAndFollowers(authToken,selectedUser,this);} }

    @Override
    public void actionFailed(String Message) {
        view.displayInfoMessage(Message);
    }
    @Override
    public void enableButton(Boolean enable) { view.setEnabled(enable); }
    @Override
    public void updateFollowButton(Boolean value) {
        new MainActivityService().updateSelectedUserFollowingAndFollowers(authToken,selectedUser,this);
        view.updateFollowButton(value); }
    @Override
    public void setFolloweeCount(String count) {
        view.setFolloweeCount(count);
    }
    @Override
    public void setFollowerCount(String count) {
        view.setFollowerCount(count);
    }

    @Override
    public void actionSucceded() { view.displayInfoMessage("Successfully posted"); }

    @Override
    public void logOutUser() {
        view.logOutUser();
    }
}
