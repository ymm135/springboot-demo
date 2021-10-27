package com.netvine.weeklyreport.service.user;


import com.netvine.weeklyreport.pojo.Users;
import com.netvine.weeklyreport.pojo.bo.user.UserBO;

public interface UserService {

    boolean queryUsernameIsExist(String username);
    Users addUser(UserBO userBO);
    Users login(String username,String password);

}
