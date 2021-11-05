package swjtu.syyymq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import swjtu.syyymq.dto.EditDto;
import swjtu.syyymq.dto.RegisterDto;
import swjtu.syyymq.dto.mapper.CustomMapper;
import swjtu.syyymq.entity.Role;
import swjtu.syyymq.entity.User;
import swjtu.syyymq.mapper.RoleMapper;
import swjtu.syyymq.mapper.UserMapper;
import swjtu.syyymq.utils.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RedisTemplate<String,Object> template;
    @Autowired
    private CustomMapper customMapper;
    @Test
    void contextLoads() {
        List<User> users = userMapper.findAll();
        for (User user : users) {
            System.out.println(user);
        }
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
        map.put("phone",123124);
        template.opsForHash().putAll("user",map);
        template.opsForValue().set("qq",123,30, TimeUnit.SECONDS);
        System.out.println(template.opsForValue().get("key1"));
    }

    @Test
    void test_redis2(){
//        System.out.println(template.opsForValue().get("key1"));
//        System.out.println(template.opsForHash().entries("user"));
//        System.out.println(template.opsForHash().get("user","id"));
        System.out.println(template.opsForValue().get("hash"));
    }

    @Test
    void test_date(){
        System.out.println(DateUtil.getNowTimeStamp());
        System.out.println(DateUtil.date2timeStamp(new Date()));
    }

    @Test
    void test_user(){
        User root = userMapper.findByUsername("root");
        System.out.println(root.getAuthorities());
    }
    @Test
    void shouldMapRegisterToDto(){
        RegisterDto dto = new RegisterDto("1","123","1","1");
        User user = customMapper.registerDtoToUser(dto);
        System.out.println(user);
    }

    @Test
    void EditConverter(){
        User root = userMapper.findByUsername("root");
        EditDto dto = customMapper.userToEditDto(root);
        System.out.println(dto);
    }

    @Test
    void test_delete(){
        userMapper.delete("mmm");
    }
}
