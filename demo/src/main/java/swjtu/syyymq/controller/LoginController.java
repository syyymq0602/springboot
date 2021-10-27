package swjtu.syyymq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/user")
public class LoginController {

    @GetMapping("/login")
    @ResponseBody
    public String login(){
        return "index";
    }
}
