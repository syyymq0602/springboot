package swjtu.syyymq.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/index")
    @ApiOperation("登录界面")
    public String login(){
        return "login";
    }

    @RequestMapping("/success")
    public String passLogin(){
        return "dashboard";
    }

    @GetMapping("/login2")
    public String test1(){
        return "login2";
    }

    @GetMapping("/forget")
    public String forgetRedirect(){
        return "forget";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

}
