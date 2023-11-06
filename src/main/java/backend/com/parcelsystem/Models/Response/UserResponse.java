package backend.com.parcelsystem.Models.Response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;


import backend.com.parcelsystem.Models.Enums.Role;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String firstname;
    private String surename;
    private List<Role> roles;
    private double longitude;
    private double latitude;
    private String imageurl;
    // private Long customer;
    // private Long owner;
    // private Long courier;
    // public UserResponse(Long id, String username, String firstname, String surename, List<Role> roles, double longitude,
    //         double latitude, String imageurl) {
    //     this.id = id;
    //     this.username = username;
    //     this.firstname = firstname;
    //     this.surename = surename;
    //     this.roles = roles;
    //     this.longitude = longitude;
    //     this.latitude = latitude;
    //     this.imageurl = imageurl;
    // }
    // @Override
    // public String toString() {
    //     return "UserResponse [id=" + id + ", username=" + username + ", firstname=" + firstname + ", surename="
    //             + surename + ", roles=" + roles + ", longitude=" + longitude + ", latitude=" + latitude + ", imageurl="
    //             + imageurl + ", customer=" + customer + ", owner=" + owner + ", courier=" + courier + "]";
    // }
    
 
}
