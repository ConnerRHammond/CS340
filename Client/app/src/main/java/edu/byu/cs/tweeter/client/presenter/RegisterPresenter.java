package edu.byu.cs.tweeter.client.presenter;

import android.app.WallpaperInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import edu.byu.cs.tweeter.client.model.service.RegisterService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter implements RegisterService.RegisterObserver {
    private View view;
    public RegisterPresenter(RegisterPresenter.View view){
        this.view = view;
    }
    public interface View{
        void navigateToUser(User user);
        void displayErrorMessage(String Message);
        void clearErrorMessage();

        void displayInfoMessage(String message);
        void clearInfoMessage();
    }

    public void register(String firstName, String lastName, String alias, String password, ImageView imageToUpload){
        String message = validateRegistration(firstName,lastName,alias,password,imageToUpload);

        view.clearErrorMessage();
        view.displayInfoMessage("Registering...");

        // Convert image to byte array.
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
            view.displayErrorMessage("Login failed: " + message);
        }

    }
    public String validateRegistration(String firstName, String lastName, String alias, String password, ImageView imageToUpload) {
        if (firstName.length() == 0) {
            return "First Name cannot be empty.";
        }
        if (lastName.length() == 0) {
            return "Last Name cannot be empty.";
        }
        if (alias.length() == 0) {
            return "Alias cannot be empty.";
        }
        if (alias.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (alias.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }

        if (imageToUpload.getDrawable() == null) {
            return "Profile image must be uploaded.";
        }
        return  null;
    }
    @Override
    public void registerSucceeded(AuthToken authToken, User user) {
        view.navigateToUser(user);
        view.displayInfoMessage("Hello "+ user.getFirstName());
    }

    @Override
    public void registerFailed(String message) {
        view.displayErrorMessage("Login Failed: " + message);
    }

    @Override
    public void registerThrewException(Exception ex) {
        view.displayErrorMessage("Login Failed: " + ex);
    }
}
