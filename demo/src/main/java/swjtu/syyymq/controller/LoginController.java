package swjtu.syyymq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class LoginController {

    @PostMapping("/login")
    public String login(){
        return "dashboard";
    }
}
