package edu.byu.cs.tweeter.client.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import edu.byu.cs.tweeter.client.model.service.RegisterService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.Views.View;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.UserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter extends RegisterLoginPresenter implements UserObserver {

    public RegisterPresenter(edu.byu.cs.tweeter.client.model.service.backgroundTask.Views.View view) {
        super(view);
    }

    public void register(String firstName, String lastName, String alias, String password, ImageView imageToUpload){
        String message = validateRegistration(firstName,lastName,alias,password,imageToUpload);

        view.displayInfoMessage("Registering...");
        // Convert image to byte array
        if(message == null) {
            Bitmap image = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] imageBytes = bos.toByteArray();
            String imageBytesBase64 = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
            view.displayInfoMessage("Loggin in ....");
            new RegisterService().registerUser(firstName, lastName, alias, password, imageBytesBase64, this);
        }
        else {
            view.displayInfoMessage("Login failed: " + message);
        }

    }
    public String validateRegistration(String firstName, String lastName, String alias, String password, ImageView imageToUpload) {
        if (firstName.length() == 0) {
            return "First Name cannot be empty.";
        }
        if (lastName.length() == 0) {
            return "Last Name cannot be empty.";
        }
        if (imageToUpload.getDrawable() == null) {
            return "Profile image must be uploaded.";
        }
        String message = validateAliasPassword(alias,password);
        return  message;
    }

}
