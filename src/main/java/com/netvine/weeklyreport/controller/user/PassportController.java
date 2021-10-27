package com.netvine.weeklyreport.controller.user;


import com.netvine.weeklyreport.common.Common;
import com.netvine.weeklyreport.pojo.Users;
import com.netvine.weeklyreport.pojo.bo.user.UserBO;
import com.netvine.weeklyreport.pojo.vo.user.UserLoginVO;
import com.netvine.weeklyreport.pojo.vo.user.UserVO;
import com.netvine.weeklyreport.service.user.UserService;
import com.netvine.weeklyreport.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Api(value = "注册登录", tags = "注册登录的接口")
@RestController
@RequestMapping(path = "passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "判断用户是否存在", notes = "用户是否存在", httpMethod = "GET")
    @GetMapping("usernameIsExist")
    public JSONResult usernameIsExist(@RequestParam String username) {

        //1.判断用户名不能为空
        if (StringUtils.isEmpty(username)) {
            return JSONResult.errorMsg("用户名不能为空");
        }

        //2.查找用于名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);

        if (isExist) {
            return JSONResult.errorMsg("用户名已经存在");
        }

        //3.请求成功
        return JSONResult.ok();

    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public JSONResult regist(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        if (StringUtils.isEmpty(username) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(confirmPassword)
        ) {
            return JSONResult.errorMsg("用户名或密码不能为空");
        }

        if (password.length() < 6) {
            return JSONResult.errorMsg("密码不能少于6位");
        }

        if (!password.equals(confirmPassword)) {
            return JSONResult.errorMsg("两次输入的密码不一致");
        }

        //校验通过，注册用户
        Users users = userService.addUser(userBO);

        //Redis会话
        UserVO userVO = getUserVO(users);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userVO), true);

        return JSONResult.ok(users);
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public JSONResult login(@RequestBody UserLoginVO userLoginVO, HttpServletRequest request, HttpServletResponse response) {

        String userName = userLoginVO.getUsername();
        String password = userLoginVO.getPassword();

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return JSONResult.errorMsg("用户名或密码不能为空");
        }

        Users users = null;
        try {
            System.out.println("密码:" + MD5Utils.getMD5Str("test"));
            users = userService.login(userName, MD5Utils.getMD5Str(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (users == null) {
            return JSONResult.errorMsg("用户名或密码不正确");
        }


        //生成用户TOKEN，存入Redis会话，
        UserVO userVO = getUserVO(users);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userVO), true);

        return JSONResult.ok(users);
    }

    private UserVO getUserVO(Users users) {
        String uuid = UUID.randomUUID().toString().trim();
        redisOperator.set(Common.REDIS_USER_TOKEN + ":" + users.getId(), uuid);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(users, userVO);
        userVO.setUserUniqueToken(uuid);
        return userVO;
    }

    @ApiOperation(value = "退出登录", notes = "退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public JSONResult logout(@RequestParam String userId, HttpServletRequest request, HttpServletResponse response) {

        //退出时，需要删除uerId
        CookieUtils.deleteCookie(request, response, "user");

        // 分布式系统中，清除用户数据
        redisOperator.del(Common.REDIS_USER_TOKEN + ":" + userId);

        return JSONResult.ok();
    }

}
