package swjtu.syyymq.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swjtu.syyymq.entity.User;
import swjtu.syyymq.mapper.UserMapper;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/all")
    @ApiOperation("所有用户")
    public String user(Model model){
        List<User> users = userMapper.findAll();
        model.addAttribute("users",users);
        return "/emp/list";
    }

    @GetMapping("/edit")
    @ApiOperation("编辑用户")
    public String edit(@RequestParam(name = "name") String username,
                       Model model){
        User user = userMapper.findByUsername(username);
        model.addAttribute("user",user);
        return "/emp/edit";
    }
}
