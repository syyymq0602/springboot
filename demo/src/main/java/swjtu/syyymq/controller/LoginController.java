package swjtu.syyymq.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {

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


    @GetMapping("/login2")
    public String test1(){
        return "login2";
    }

    @GetMapping("/root/{id}")
    @ResponseBody
    public String test2(@PathVariable("id") String id){
        return "root"+id;
    }

}
