package swjtu.syyymq.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swjtu.syyymq.dto.EditDto;
import swjtu.syyymq.dto.RoleDto;
import swjtu.syyymq.dto.UpdateEditDto;
import swjtu.syyymq.dto.mapper.CustomMapper;
import swjtu.syyymq.entity.Role;
import swjtu.syyymq.entity.User;
import swjtu.syyymq.mapper.RoleMapper;
import swjtu.syyymq.mapper.UserMapper;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserMapper userMapper;
    private final CustomMapper customMapper;
    private final RoleMapper roleMapper;

    @Autowired
    public UserController(UserMapper userMapper,
                          CustomMapper customMapper,
                          RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.customMapper = customMapper;
        this.roleMapper = roleMapper;
    }

    @GetMapping("/all")
    @ApiOperation("所有用户")
    public String user(Model model){
        List<User> users = userMapper.findAll();
        model.addAttribute("users",users);
        return "user/list";
    }

    @GetMapping("/edit")
    @ApiOperation("跳转编辑页面")
    public String edit(@RequestParam(name = "name") String username,
                       Model model){
        User user = userMapper.findByUsername(username);
        EditDto editDto = customMapper.userToEditDto(user);
        List<Role> roles = roleMapper.findAll();
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (Role role : roles) {
            roleDtoList.add(customMapper.roleToRoleDto(role));
        }
        model.addAttribute("roleDto",roleDtoList);
        model.addAttribute("editDto",editDto);
        return "user/edit";
    }
    @GetMapping("/delete")
    @ApiOperation("删除用户")
    public String delete(@RequestParam(name = "name") String username){
        userMapper.delete(username);
        return "redirect:/user/all";
    }

    @PostMapping("/edit")
    @ApiOperation("更新用户信息")
    public String update(UpdateEditDto dto){
        User user = customMapper.updateDtoToUser(dto);
        List<String> roles = dto.getRoles();
        List<Role> userRoles = new ArrayList<>();
        for (String role : roles) {
            userRoles.add(roleMapper.findByZName(role));
        }
        user.setRoles(userRoles);
        // TODO:表的联合更新
//        userMapper.updateUser(user);
        System.out.println(user);
        return "redirect:/user/all";
    }
}
