package swjtu.syyymq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import swjtu.syyymq.entity.User;
import swjtu.syyymq.mapper.UserMapper;

@Service
public class CustomUserService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO：丰富校验逻辑
        User user = userMapper.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        user.setRoles(userMapper.getUserRoleById(user.getId()));
        return user;
    }
}
