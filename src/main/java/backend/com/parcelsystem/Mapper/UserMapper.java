package backend.com.parcelsystem.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import backend.com.parcelsystem.Models.Users;
import backend.com.parcelsystem.Models.Response.AuthResponse;
import backend.com.parcelsystem.Models.Response.UserResponse;

@Component
public class UserMapper {

    @Autowired
    ModelMapper modelMapper;

    public UserResponse mapUserToResponse(Users user) {
      UserResponse res = modelMapper.map(user, UserResponse.class);
      System.out.println("res: " + res);
      return res;
    }

    public AuthResponse mapUserToAuthReponse(Users user) {
      AuthResponse res = modelMapper.map(user, AuthResponse.class);
      return res;
    }
}
