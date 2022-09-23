package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Represents a status (or tweet) posted by a user.
 */
public class Status implements Serializable {
    /**
     * Text for the status.
     */
    public String post;

    /**
     * User who sent the status.
     */
    public User user;
    /**
     * String representation of the date/time at which the status was sent.
     */
    public String date;
    /**
     * URLs contained in the post text.
     */
    public List<String> urls;
    /**
     * User mentions contained in the post text.
     */
    public List<String> mentions;


    public Status() {
    }

    public Status(String post, User user, String date, List<String> urls, List<String> mentions) {
        this.post = post;
        this.user = user;
        this.date = date;
        this.urls = urls;
        this.mentions = mentions;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String datetime) {
        this.date = datetime;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getMentions() {
        return mentions;
    }

    public void setMentions(List<String> mentions) {
        this.mentions = mentions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equals(post, status.post) &&
                Objects.equals(user, status.user) &&
                Objects.equals(date, status.date) &&
                Objects.equals(mentions, status.mentions) &&
                Objects.equals(urls, status.urls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, user, date, mentions, urls);
    }

    @Override
    public String toString() {
        return "Status{" +
                "post='" + post + '\'' +
                ", user=" + user +
                ", datetime=" + date +
                ", mentions=" + mentions +
                ", urls=" + urls +
                '}';
    }
}
