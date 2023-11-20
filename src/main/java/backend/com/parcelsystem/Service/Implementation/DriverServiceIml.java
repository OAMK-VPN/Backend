package backend.com.parcelsystem.Service.Implementation;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import backend.com.parcelsystem.Exception.BadResultException;
import backend.com.parcelsystem.Exception.EntityExistingException;
import backend.com.parcelsystem.Exception.EntityNotFoundException;
import backend.com.parcelsystem.Mapper.UserMapper;
import backend.com.parcelsystem.Models.Driver;
import backend.com.parcelsystem.Models.Users;
import backend.com.parcelsystem.Models.Enums.Role;
import backend.com.parcelsystem.Models.Request.UserSignIn;
import backend.com.parcelsystem.Models.Request.UserSignUp;
import backend.com.parcelsystem.Models.Response.AuthResponse;
import backend.com.parcelsystem.Repository.DriverRepos;
import backend.com.parcelsystem.Repository.UserRepos;
import backend.com.parcelsystem.Security.SecurityConstant;
import backend.com.parcelsystem.Service.DriverService;
import backend.com.parcelsystem.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;


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
import backend.com.parcelsystem.Models.Users;
import backend.com.parcelsystem.Models.Enums.Role;
import backend.com.parcelsystem.Models.Request.PasswordForm;
import backend.com.parcelsystem.Models.Request.UserSignIn;
import backend.com.parcelsystem.Models.Request.UserSignUp;
import backend.com.parcelsystem.Models.Response.AuthResponse;
import backend.com.parcelsystem.Models.Response.UserResponse;
import backend.com.parcelsystem.Repository.UserRepos;
import backend.com.parcelsystem.Security.SecurityConstant;
import backend.com.parcelsystem.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;


@Service
public class DriverServiceIml implements DriverService, UserDetailsService {
    @Autowired
    DriverRepos driverRepos;
    @Autowired
    UserService userService;

    @Autowired
    UserRepos userRepos;
    @Autowired
    UserMapper userMapper;
    @Autowired
    HttpServletResponse response;
    @Autowired
    JavaMailSender mailSender;

    @Override
    public Driver getByAuthenticatedUser() {
        Users authUser = userService.getAuthUser();
        Optional<Driver> entity = driverRepos.findByUser(authUser);
        if(!entity.isPresent()) {
            Driver driver = new Driver(authUser);
            return driverRepos.save(driver);
        } else {
            Driver driver = entity.get();
            return driver;
        }
    }

    @Override
    public Driver getById(Long id) {
        Optional<Driver> entity = driverRepos.findById(id);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the courier not found");
        } else {
            Driver courier = entity.get();
            return courier;
        }
    }
    

    
    @Override
    public AuthResponse saveUser(UserSignUp signUp) {
        Optional<Users> entity = userRepos.findByUsername(signUp.getUsername());
        if(entity.isPresent()) {
         throw new EntityExistingException("the username exists");
        }
        Users user = new Users(signUp.getUsername(), new BCryptPasswordEncoder().encode(signUp.getPassword()), signUp.getFullname(), signUp.getEmail(), signUp.getCity(), signUp.getAddress(), signUp.getZipcode());
        user.getRoles().add(Role.USER);
        userRepos.save(user);

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
    public AuthResponse signIn(UserSignIn userSignIn) {
       
        Optional<Users> entity = userRepos.findByUsername(userSignIn.getUsername());
        if(!entity.isPresent()) {
           throw new EntityNotFoundException("the username not found");
        }
        Users user = entity.get();
        if(user.isActive() == false) {
            throw new BadResultException("the account was deleted");
        }
        if(!new BCryptPasswordEncoder().matches(userSignIn.getPassword(), user.getPassword())) {
            throw new EntityNotFoundException("the password is wrong");
        }

        List<String> claims = user.getRoles().stream().map(auth -> auth.getName()).collect(Collectors.toList());
        String token = JWT.create()
        .withSubject(userSignIn.getUsername())
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



}
