package swjtu.syyymq.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import swjtu.syyymq.entity.User;
import swjtu.syyymq.mapper.UserMapper;

@Controller
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping({"/","/index"})
    @ApiOperation("欢迎界面")
    public String index(){
        return "index";
    }

    @GetMapping("/toLogin")
    @ApiOperation("登录界面")
    public String login(){
        return "login";
    }

    @RequestMapping("/success")
    public String passLogin(){
        return "dashboard";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(User user){
        // TODO:注册时验证是否用户存在，验证密码有效性等
        String encode = new BCryptPasswordEncoder().encode(user.getPassword()).trim();
        user.setPassword(encode);
        userMapper.save(user);
        return "index";
    }

    @GetMapping("/admin/{id}")
    @ResponseBody
    public String test1(@PathVariable("id") String id){
        return "admin"+id;
    }

    @GetMapping("/root/{id}")
    @ResponseBody
    public String test2(@PathVariable("id") String id){
        return "root"+id;
    }

}
