package swjtu.syyymq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import swjtu.syyymq.entity.Role;
import swjtu.syyymq.entity.User;
import swjtu.syyymq.mapper.RoleMapper;
import swjtu.syyymq.mapper.UserMapper;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RedisTemplate<String,Object> template;
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

    @Test
    void test_redis(){
        template.opsForValue().set("key1",2);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("id",1);
        map.put("age",21);
        template.opsForHash().putAll("user",map);
        System.out.println(template.opsForValue().get("key1"));
    }

    @Test
    void test_redis2(){
        System.out.println(template.opsForValue().get("key1"));
        System.out.println(template.opsForHash().entries("user"));
        System.out.println(template.opsForHash().get("user","id"));
    }
}
