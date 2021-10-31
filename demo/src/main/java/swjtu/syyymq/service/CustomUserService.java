package swjtu.syyymq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import swjtu.syyymq.entity.User;
import swjtu.syyymq.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("admin"));
//        String encode = new BCryptPasswordEncoder().encode(user.getPassword());
//        user.setPassword(encode);
//        user.setRoles(userMapper.getUserRoleById(user.getId()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),simpleGrantedAuthorities);
    }
}
