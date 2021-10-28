package swjtu.syyymq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/user")
@CrossOrigin
public class LoginController {

    @RequestMapping({"/","/index"})
    public String index(){
        return "index";
    }

    @RequestMapping("/toLogin")
    public String login(){
        return "login";
    }

    @RequestMapping("/success")
    public String passLogin(){
        return "dashboard";
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
