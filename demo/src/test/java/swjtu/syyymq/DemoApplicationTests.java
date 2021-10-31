package swjtu.syyymq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import swjtu.syyymq.entity.Role;
import swjtu.syyymq.entity.User;
import swjtu.syyymq.mapper.RoleMapper;
import swjtu.syyymq.mapper.UserMapper;
import swjtu.syyymq.service.UserService;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Test
    void contextLoads() {
        List<User> users = userMapper.findAll();
        System.out.println(users);
    }

    @Test
    void test_findUserByUsername(){
        String name = "admin";
        System.out.println(userMapper.findByUsername(name));
    }

    @Test
    void test_getUserRoleById(){
        List<Role> roles = userMapper.getUserRoleById(2);
        System.out.println(roles);
    }

    @Test
    void test_role_findAll(){
        List<Role> roles = roleMapper.findAll();
        System.out.println(roles);
    }
}
