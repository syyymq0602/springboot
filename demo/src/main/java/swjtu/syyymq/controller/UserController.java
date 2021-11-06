package swjtu.syyymq.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swjtu.syyymq.dto.EditDto;
import swjtu.syyymq.dto.mapper.CustomMapper;
import swjtu.syyymq.entity.User;
import swjtu.syyymq.mapper.UserMapper;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserMapper userMapper;
    private final CustomMapper customMapper;

    @Autowired
    public UserController(UserMapper userMapper,
                          CustomMapper customMapper) {
        this.userMapper = userMapper;
        this.customMapper = customMapper;
    }

    @GetMapping("/all")
    @ApiOperation("所有用户")
    public String user(Model model){
        List<User> users = userMapper.findAll();
        model.addAttribute("users",users);
        return "user/list";
    }

    @GetMapping("/edit")
    @ApiOperation("编辑用户")
    public String edit(@RequestParam(name = "name") String username,
                       Model model){
        User user = userMapper.findByUsername(username);
        EditDto editDto = customMapper.userToEditDto(user);
        model.addAttribute("editDto",editDto);
        return "user/edit";
    }
    @GetMapping("/delete")
    @ApiOperation("删除用户")
    public String delete(@RequestParam(name = "name") String username){
        userMapper.delete(username);
        return "redirect:/user/all";
    }
}
