package swjtu.syyymq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import swjtu.syyymq.entity.User;
import swjtu.syyymq.mapper.UserMapper;
import swjtu.syyymq.service.MailService;
import swjtu.syyymq.utils.RandomUtils;

@Controller
public class ForgetController {
    @Value("${mail.fromMail.expiredTime}")
    private int expiredTime;

    private final MailService mailService;
    private final UserMapper userMapper;
    @Autowired
    public ForgetController(MailService mailService, UserMapper userMapper) {
        this.mailService = mailService;
        this.userMapper = userMapper;
    }

    @PostMapping("/forget")
    @ResponseBody
    public String forgetPassword(String name,String email, RedirectAttributes attributes) throws Exception {
        User user = userMapper.findByUsername(name);
        if(user==null){
            attributes.addFlashAttribute("message","用户不存在，请注册！");
            return "false";
        }
        int code = RandomUtils.getRandom(6);
        String resetPasswordEmail = mailService.resetPasswordEmail(email,code,expiredTime);
        if("success".equalsIgnoreCase(resetPasswordEmail)){
            System.out.println(resetPasswordEmail);
            // TODO:向redis存入验证码以及过期时间
        }
        return resetPasswordEmail;
    }
}
