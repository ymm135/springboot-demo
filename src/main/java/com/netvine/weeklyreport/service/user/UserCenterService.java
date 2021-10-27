package com.netvine.weeklyreport.service.user;

import com.netvine.weeklyreport.pojo.Users;
import com.netvine.weeklyreport.pojo.bo.user.UserInfoBO;

public interface UserCenterService {
    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    Users queryUserInfos(String userId);

    /**
     *
     * @param userId
     * @param userInfoBO
     * @return
     */
    Users updateUserInfo(String userId, UserInfoBO userInfoBO);


    /**
     * 更新用户头像
     * @param userId
     * @param userFacePath
     * @return
     */
    Users updateUserFace(String userId, String userFacePath);

}
