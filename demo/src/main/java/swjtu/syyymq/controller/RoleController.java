package swjtu.syyymq.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swjtu.syyymq.entity.Role;
import swjtu.syyymq.mapper.RoleMapper;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleMapper roleMapper;

    @Autowired
    public RoleController(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }
    @GetMapping("/all")
    @ApiOperation("所有角色")
    public List<Role> roles(){
        return roleMapper.findAll();
    }
}
