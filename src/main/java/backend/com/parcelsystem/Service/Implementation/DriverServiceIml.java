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
import backend.com.parcelsystem.Models.Driver;
import backend.com.parcelsystem.Models.Enums.Role;
import backend.com.parcelsystem.Models.Response.AuthResponse;
import backend.com.parcelsystem.Repository.DriverRepos;
import backend.com.parcelsystem.Security.SecurityConstant;
import backend.com.parcelsystem.Service.DriverService;
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
import backend.com.parcelsystem.Mapper.DriverMapper;

import backend.com.parcelsystem.Models.Enums.Role;
import backend.com.parcelsystem.Models.Request.PasswordForm;
import backend.com.parcelsystem.Models.Request.DriverSignUp;
import backend.com.parcelsystem.Models.Request.DriverSignIn;
import backend.com.parcelsystem.Models.Response.AuthResponse;
import backend.com.parcelsystem.Models.Response.UserResponse;
import backend.com.parcelsystem.Repository.UserRepos;
import backend.com.parcelsystem.Security.SecurityConstant;
import jakarta.servlet.http.HttpServletResponse;




@Service
public class DriverServiceIml implements DriverService, UserDetailsService {
    @Autowired
    DriverRepos driverRepos;
    @Autowired
    DriverMapper driverMapper;
    @Autowired
    HttpServletResponse response;
    @Autowired
    JavaMailSender mailSender;

        /* @Override
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
    } */
    @Override
    public Driver getDriverByUsername(String userName) {
        Optional<Driver> entity = driverRepos.findByUserName(userName);
        if(!entity.isPresent()) {
         throw new EntityNotFoundException("the username not found");
        }
        Driver driver = entity.get();
        return driver;
    }

    @Override
    public Driver getAuthUser( ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Driver user = getDriverByUsername(username);
        return user;
    }

    @Override
    public AuthResponse saveDriver(DriverSignUp signUp) {
        Optional<Driver> entity = driverRepos.findByUserName(signUp.getUsername());
        if(entity.isPresent()) {
         throw new EntityExistingException("the username already exists");
        }
        Driver driver = new Driver(signUp.getUsername(), new BCryptPasswordEncoder().encode(signUp.getPassword()), signUp.getEmail());
        driver.getRoles().add(Role.DRIVER);
        driverRepos.save(driver);

        List<String> claims = driver.getRoles().stream().map(auth -> auth.getName()).collect(Collectors.toList());
        String token = JWT.create()
        .withSubject(driver.getUserName())
        .withClaim("authorities", claims)
        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.expire_time))
        .sign(Algorithm.HMAC512(SecurityConstant.private_key));
        
      
        response.setStatus(HttpServletResponse.SC_OK); 
        response.setHeader("Authorization", SecurityConstant.authorization + token);
        AuthResponse res = driverMapper.mapDriverToAuthReponse(driver);
        res.setToken(token);
        return res;
    }

    @Override
    public AuthResponse signIn(DriverSignIn driverSignIn) {
       
        Optional<Driver> entity = driverRepos.findByUserName(driverSignIn.getUsername());
        if(!entity.isPresent()) {
           throw new EntityNotFoundException("the driver's username not found");
        }
        Driver driver = entity.get();
        if(driver.isActive() == false) {
            throw new BadResultException("the account was deleted");
        }
        if(!new BCryptPasswordEncoder().matches(driverSignIn.getPassword(), driver.getPassword())) {
            throw new EntityNotFoundException("the password is wrong");
        }

        List<String> claims = driver.getRoles().stream().map(auth -> auth.getName()).collect(Collectors.toList());
        String token = JWT.create()
        .withSubject(driverSignIn.getUsername())
        .withClaim("authorities", claims)
        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.expire_time))
        .sign(Algorithm.HMAC512(SecurityConstant.private_key));
      
        response.setStatus(HttpServletResponse.SC_OK); 
        response.setHeader("Authorization", SecurityConstant.authorization + token);
        AuthResponse res = driverMapper.mapDriverToAuthReponse(driver);
        res.setToken(token);
        System.out.println(res);
        return res;
    
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<Driver> entity = driverRepos.findByUserName(username);
       if(!entity.isPresent()) {
        throw new EntityNotFoundException("the username not found");
       }
       Driver driver = entity.get();
       List<SimpleGrantedAuthority> authorities = driver.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
       User user = new User(username, driver.getPassword(), authorities);
       return user;
    }

}
