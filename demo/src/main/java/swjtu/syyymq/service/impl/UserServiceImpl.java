package swjtu.syyymq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import swjtu.syyymq.entity.User;
import swjtu.syyymq.mapper.UserMapper;
import swjtu.syyymq.service.UserService;

import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findByUsername(String name) {
        return userMapper.findByUsername(name);
    }

    @Override
    public User getUserRoleById(String username) {
        User user = userMapper.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        user.setRoles(userMapper.getUserRoleById(user.getId()));
        return user;
    }
}
