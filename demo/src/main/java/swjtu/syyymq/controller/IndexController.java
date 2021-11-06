package swjtu.syyymq.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"/","index"})
    @ApiOperation("欢迎界面")
    public String index(){
        return "index";
    }

    @GetMapping("/role/add")
    @ApiOperation("添加角色")
    public String add(){
        return "role/add";
    }


}
