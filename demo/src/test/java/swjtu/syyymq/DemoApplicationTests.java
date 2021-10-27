package swjtu.syyymq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import swjtu.syyymq.entity.User;
import swjtu.syyymq.service.UserService;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserService userService;
    @Test
    void contextLoads() {
        List<User> users = userService.findAll();
        System.out.println(users);
    }

    @Test
    void test_findUserByUsername(){
        String name = "admin";
        System.out.println(userService.findByUsername(name));
    }

}
