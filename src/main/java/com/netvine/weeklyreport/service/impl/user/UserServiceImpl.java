package com.netvine.weeklyreport.service.impl.user;


import com.netvine.weeklyreport.common.enums.Sex;
import com.netvine.weeklyreport.mapper.UsersMapper;
import com.netvine.weeklyreport.pojo.Users;
import com.netvine.weeklyreport.pojo.bo.user.UserBO;
import com.netvine.weeklyreport.service.user.UserService;
import com.netvine.weeklyreport.utils.DateUtil;
import com.netvine.weeklyreport.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    //默认头像
    private final String DEFAULT_USER_FACE = "";

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);

        Users users = usersMapper.selectOneByExample(userExample);
        return users != null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Users addUser(UserBO userBO) {
        //首选校验用户名与密码是否一致

        //密码加密
        String md5Pwd = null;
        try {
            md5Pwd = MD5Utils.getMD5Str(userBO.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //唯一userid
        String userId = sid.nextShort();

        //增加更新时间、创建时间
        Users users = new Users();

        users.setId(userId);
        users.setUsername(userBO.getUsername());
        users.setNickname(userBO.getUsername());
        users.setPassword(md5Pwd);
        users.setFace(DEFAULT_USER_FACE);
        users.setBirthday(DateUtil.stringToDate("1990-01-01"));
        users.setSex(Sex.secret.type);

        users.setUpdatedTime(DateUtil.getCurrentDateTime());
        users.setCreatedTime(DateUtil.getCurrentDateTime());

        usersMapper.insert(users);

        return users;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users login(String username, String password) {

        //查询用户名和密码是否匹配
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password",password);

        Users users = usersMapper.selectOneByExample(example);

        return users;
    }
}
