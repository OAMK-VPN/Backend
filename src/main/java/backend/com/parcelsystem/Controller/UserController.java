package backend.com.parcelsystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.com.parcelsystem.Mapper.UserMapper;
import backend.com.parcelsystem.Models.Users;
import backend.com.parcelsystem.Models.Request.PasswordForm;
import backend.com.parcelsystem.Models.Request.UserSignIn;
import backend.com.parcelsystem.Models.Request.UserSignUp;
import backend.com.parcelsystem.Models.Response.AuthResponse;
import backend.com.parcelsystem.Models.Response.UserResponse;
import backend.com.parcelsystem.Service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getByUsername(@PathVariable String username) {
        UserResponse res = userMapper.mapUserToResponse(userService.getUserByUsername(username));
        return new ResponseEntity<UserResponse>(res, HttpStatus.OK);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponse> getByid(@PathVariable long id) {
        UserResponse res = userMapper.mapUserToResponse(userService.getUserById(id));
        return new ResponseEntity<UserResponse>(res, HttpStatus.OK);
    }
    @PutMapping("/signIn")
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody UserSignIn userSignIn) {
        return new ResponseEntity<AuthResponse>(userService.signIn(userSignIn), HttpStatus.OK);
    }
    //requires token
    @GetMapping("/authUser/getAuthUser")
    public ResponseEntity<UserResponse> getAuthUser() {
        UserResponse res = userMapper.mapUserToResponse(userService.getAuthUser());
        return new ResponseEntity<UserResponse>(res, HttpStatus.OK);
    }
    
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserSignUp userSignup) {
        return new ResponseEntity<AuthResponse>(userService.saveUser(userSignup), HttpStatus.CREATED);
    }

    //requires token
    @PutMapping("/authUser/updatePassword")
    public ResponseEntity<UserResponse> updatePassword(@Valid @RequestBody PasswordForm passwordForm) {
         UserResponse res = userMapper.mapUserToResponse(userService.updatePassword(passwordForm));
        return new ResponseEntity<UserResponse>(res, HttpStatus.OK);
    }
    
    @PutMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        return new ResponseEntity<String>(userService.forgotPassword(email), HttpStatus.OK);
    }
}
