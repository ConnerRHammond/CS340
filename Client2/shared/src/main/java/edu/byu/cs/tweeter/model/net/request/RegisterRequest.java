package edu.byu.cs.tweeter.model.net.request;

import java.io.Serializable;

public class RegisterRequest extends AuthenticationRequest implements Serializable {
    private String firstName;
    private String lastName;
    private String image;

    public  RegisterRequest(){

    }
    public RegisterRequest(String username, String password, String firstName,String lastName,String image) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
