package backend.com.parcelsystem.Service.Implementation;


import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import backend.com.parcelsystem.Exception.BadResultException;
import backend.com.parcelsystem.Exception.EntityExistingException;
import backend.com.parcelsystem.Exception.EntityNotFoundException;
import backend.com.parcelsystem.Mapper.UserMapper;
import backend.com.parcelsystem.Models.Driver;
import backend.com.parcelsystem.Models.Receiver;
import backend.com.parcelsystem.Models.Sender;
import backend.com.parcelsystem.Models.Users;
import backend.com.parcelsystem.Models.Enums.Role;
import backend.com.parcelsystem.Models.Request.PasswordForm;
import backend.com.parcelsystem.Models.Request.UserSignIn;
import backend.com.parcelsystem.Models.Request.UserSignUp;
import backend.com.parcelsystem.Models.Response.AuthResponse;
import backend.com.parcelsystem.Models.Response.UserResponse;
import backend.com.parcelsystem.Repository.DriverRepos;
import backend.com.parcelsystem.Repository.ReceiverRepos;
import backend.com.parcelsystem.Repository.SenderRepos;
import backend.com.parcelsystem.Repository.UserRepos;
import backend.com.parcelsystem.Security.SecurityConstant;
import backend.com.parcelsystem.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServiceIml implements UserService, UserDetailsService {
    
    @Autowired
    UserRepos userRepos;
    @Autowired
    UserMapper userMapper;
    @Autowired
    HttpServletResponse response;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    DriverRepos driverRepos;
    @Autowired
    SenderRepos senderRepos;
    @Autowired
    ReceiverRepos receiverRepos;
 
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<Users> entity = userRepos.findByUsername(username);
       if(!entity.isPresent()) {
        throw new EntityNotFoundException("the username not found");
       }
       Users users = entity.get();
       List<SimpleGrantedAuthority> authorities = users.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
       User user = new User(username, users.getPassword(), authorities);
       return user;
    }

    @Override
    public Users getUserByUsername(String username) {
        // Users authUser = getAuthUser();
        Optional<Users> entity = userRepos.findByUsername(username);
        if(!entity.isPresent()) {
         throw new EntityNotFoundException("the username not found");
        }
        Users users = entity.get();
        // if(!authUser.getEmail().equals(users.getEmail())) {
        //     throw new BadResultException("are not authorized");
        // }
        return users;
    }

    @Override
    public Users getUserByEmail(String email) {
        Optional<Users> entity = userRepos.findByEmail(email);
        if(!entity.isPresent()) {
         throw new EntityNotFoundException("the email not found");
        }
        Users users = entity.get();
        return users;
    }

    @Override
    public Users getUserById(Long id) {
        Optional<Users> entity = userRepos.findById(id);
        if(!entity.isPresent()) {
         throw new EntityNotFoundException("the username not found");
        }
        Users users = entity.get();
        return users;
    }

    @Override
    public AuthResponse saveUser(UserSignUp signUp, boolean isDriver) {
        Optional<Users> entity = userRepos.findByEmail(signUp.getEmail());
        if(entity.isPresent()) {
         throw new EntityExistingException("the email exists");
        }
        Users user = new Users(signUp.getUsername(), new BCryptPasswordEncoder().encode(signUp.getPassword()), signUp.getFullname(), signUp.getEmail(), signUp.getCity(), signUp.getAddress(), signUp.getZipcode());
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.RECEIVER);
        user.getRoles().add(Role.SENDER);
        userRepos.save(user);

        // automatically create the customer and receiver for the signup user;
        Sender sender = new Sender(user);
        senderRepos.save(sender);

        Receiver receiver = new Receiver(user);
        receiverRepos.save(receiver);

        // check the driver code to create the driver 
        if(isDriver == true && signUp.getDriverCode().equals("123456789")) {
            user.getRoles().add(Role.DRIVER);
            userRepos.save(user);
            Driver driver = new Driver(user);
            driverRepos.save(driver);
        } 

        List<String> claims = user.getRoles().stream().map(auth -> auth.getName()).collect(Collectors.toList());
        String token = JWT.create()
        .withSubject(user.getUsername())
        .withClaim("authorities", claims)
        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.expire_time))
        .sign(Algorithm.HMAC512(SecurityConstant.private_key));
        
      
        response.setStatus(HttpServletResponse.SC_OK); 
        response.setHeader("Authorization", SecurityConstant.authorization + token);
        AuthResponse res = userMapper.mapUserToAuthReponse(user);
        res.setToken(token);
        return res;
    }

    @Override
    public AuthResponse saveDriver(UserSignUp userSignup) {
        System.out.println(userSignup);
        if(!userSignup.getDriverCode().equals("123456789")) {
            System.out.println("the driver code is not correct, you cannot register as an driver");
           throw new BadResultException("the driver code is not correct, you cannot register as an driver");
        } 
        return saveUser(userSignup, true);
    }

    @Override
    public AuthResponse signIn(UserSignIn userSignIn, boolean isDriver) {
        System.out.println(userSignIn.getEmail());
        Optional<Users> entity = userRepos.findByEmail(userSignIn.getEmail());
        
        if(!entity.isPresent()) {
           throw new EntityNotFoundException("the email not found");
        }
        Users user = entity.get();
        if(!new BCryptPasswordEncoder().matches(userSignIn.getPassword(), user.getPassword())) {
            throw new EntityNotFoundException("the password is wrong");
        }
        // check driver login or normal user login
        if(isDriver == true) {
            Optional<Driver> driverEntity = driverRepos.findByUser(user);
            if(!driverEntity.isPresent()) {
                throw new BadResultException("are not authorized to login as an driver");
            }
        }

        List<String> claims = user.getRoles().stream().map(auth -> auth.getName()).collect(Collectors.toList());
        String token = JWT.create()
        .withSubject(user.getUsername())
        .withClaim("authorities", claims)
        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.expire_time))
        .sign(Algorithm.HMAC512(SecurityConstant.private_key));
      
        response.setStatus(HttpServletResponse.SC_OK); 
        response.setHeader("Authorization", SecurityConstant.authorization + token);
        AuthResponse res = userMapper.mapUserToAuthReponse(user);
        res.setToken(token);
        System.out.println(res);
        return res;
    
    }

   

    @Override
    public Users getAuthUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = getUserByUsername(username);
        if(user.isActive() == false) {
            throw new BadResultException("the account was deactivated");
        }
        return user;
    }

    @Override
    public Users updatePassword(PasswordForm passwordForm) {
        Users user = getAuthUser();
        if(!passwordForm.getNewPassword().equals(passwordForm.getConfirmPassword())) {
         throw new BadResultException("new password dont match");
        }
        if(!new BCryptPasswordEncoder().matches(passwordForm.getPassword(), user.getPassword())) {
         throw new BadResultException("password is wrong");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(passwordForm.getNewPassword()));
        return userRepos.save(user);
    }


    @Override
    public String forgotPassword(String email) {
        Users user = getUserByEmail(email);
        
        String password = generateAutoPassword();
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        userRepos.save(user);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("BookingApp");
        message.setTo(email);
        message.setSubject("reset password");
        message.setText("your new password is " + password + ", please use this password to login and change your password");
        mailSender.send(message);
        return "The new password are sent to your email successfully";
    }

     private String generateAutoPassword() {
        List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        String password = "";
        int n = 0;
        while(n < 6) {
            int randomIndex = (int)(Math.random() * (10));
            System.out.println(randomIndex);
            password += list.get(randomIndex);
            n++;
        }
        System.out.println(password);
        return password;
    }

    @Override
    public Users updateProfile(String email, String fullname, String city, String address, String zipcode) {
       Users authUsers = getAuthUser();

       if (authUsers == null) {
            throw new EntityNotFoundException("User not found");
        }

        // Update only non-null properties
        if (email != null) {
            boolean isDuplicated = checkEmailDuplication(email);
            if(isDuplicated == true) {
                throw new BadResultException("the email is existent");
            }
            authUsers.setEmail(email);;
        }
        if (fullname != null) {
            authUsers.setFullname(fullname);
        }
        if (city != null) {
            authUsers.setCity(city);
        }
        if (address != null) {
            authUsers.setAddress(address);
        }
        if (zipcode != null) {
            authUsers.setZipcode(zipcode);
        }

        return userRepos.save(authUsers);
    }

    @Override
    public Users deactiveAccount() {
        Users authUsers = getAuthUser();
        System.out.println(authUsers);
        if (authUsers == null) {
                throw new EntityNotFoundException("User not found");
            }

        authUsers.setActive(false);
        authUsers = userRepos.save(authUsers);
        System.out.println(authUsers);
        return authUsers;
    }

    @Override
    public Users reactiveAccount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = getUserByUsername(username);
        user.setActive(true);
        return userRepos.save(user);
    }

    public Boolean checkEmailDuplication(String email) {
        Optional<Users> entity = userRepos.findByEmail(email);
        if(!entity.isPresent()) {
         return false;
        }
        return true;
    }
}
