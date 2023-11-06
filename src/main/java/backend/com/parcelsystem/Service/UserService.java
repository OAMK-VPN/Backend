package backend.com.parcelsystem.Service;

import backend.com.parcelsystem.Models.Request.PasswordForm;
import backend.com.parcelsystem.Models.Request.UserSignIn;
import backend.com.parcelsystem.Models.Request.UserSignUp;

import backend.com.parcelsystem.Models.Users;
import backend.com.parcelsystem.Models.Response.UserResponse;

public interface UserService {
    Users getUserById(Long id);
    Users getUserByUsername(String username);
    Users getUserByEmail(String email);
    UserResponse saveUser(UserSignUp userSignup);
    UserResponse signIn(UserSignIn userSignIn);
    Users getAuthUser();
    Users updatePassword(PasswordForm passwordForm);
    String forgotPassword(String email);
}
