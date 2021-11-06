package swjtu.syyymq.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import swjtu.syyymq.dto.RoleDto;
import swjtu.syyymq.dto.mapper.CustomMapper;
import swjtu.syyymq.entity.Role;
import swjtu.syyymq.mapper.RoleMapper;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    private final RoleMapper roleMapper;
    private final CustomMapper customMapper;

    @Autowired
    public RoleController(RoleMapper roleMapper,
                          CustomMapper customMapper) {
        this.roleMapper = roleMapper;
        this.customMapper = customMapper;
    }
    @GetMapping("/all")
    @ApiOperation("所有角色")
    public String all(Model model){
        List<Role> roles = roleMapper.findAll();
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (Role role : roles) {
            roleDtoList.add(customMapper.roleToRoleDto(role));
        }
        model.addAttribute("roles",roleDtoList);
        return "role/roleList";
    }

    @GetMapping("/delete")
    @ApiOperation("删除角色")
    public String delete(String name){
        roleMapper.delete(name);
        return "redirect:/role/all";
    }

    @PostMapping("/edit")
    @ApiOperation("更新用户")
    public String edit(RoleDto roleDto){
        Role role = customMapper.roleDtoToRole(roleDto);
        roleMapper.update(role);
        return "redirect:/role/all";
    }

    @PostMapping("/add")
    @ApiOperation("添加角色")
    public String add(RoleDto roleDto){
        Role role = customMapper.roleDtoToRole(roleDto);
        roleMapper.add(role);
        return "redirect:/role/all";
    }

    @GetMapping("/edit")
    public String edit(String name,
                       Model model){
        RoleDto roleDto = new RoleDto();
        roleDto.setNameEN(name);
        Role role = customMapper.roleDtoToRole(roleDto);
        Role result = roleMapper.findByEName(role.getNameEN());
        model.addAttribute("role",result);
        return "role/edit";
    }
}
