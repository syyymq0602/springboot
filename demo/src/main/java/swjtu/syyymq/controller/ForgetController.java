package swjtu.syyymq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import swjtu.syyymq.entity.User;
import swjtu.syyymq.mapper.UserMapper;
import swjtu.syyymq.service.MailService;
import swjtu.syyymq.utils.MD5Utils;
import swjtu.syyymq.utils.RandomUtils;

import java.util.concurrent.TimeUnit;

@Controller
public class ForgetController {
    @Value("${mail.fromMail.expiredTime}")
    private int expiredTime;

    private final RedisTemplate<String,Object> template;
    private final MailService mailService;
    private final UserMapper userMapper;

    @Autowired
    public ForgetController(MailService mailService,
                            UserMapper userMapper,
                            RedisTemplate<String, Object> redisTemplate) {
        this.mailService = mailService;
        this.userMapper = userMapper;
        this.template = redisTemplate;
    }

    @PostMapping("/forget")
    @ResponseBody
    public String forgetPassword(String username,String email, RedirectAttributes attributes) throws Exception {
        User user = userMapper.findByUsername(username);
        if(user==null){
            attributes.addFlashAttribute("message","用户不存在，请注册！");
            return "false";
        }
        int code = RandomUtils.getRandom(6);
        String resetPasswordEmail = mailService.resetPasswordEmail(email,code,expiredTime);
        if("success".equalsIgnoreCase(resetPasswordEmail)){
            String hash = MD5Utils.code(Integer.toString(code));//生成MD5值
            assert hash != null;
            template.opsForValue().set("hash",hash,expiredTime, TimeUnit.MINUTES);
        }
        return resetPasswordEmail;
    }
}
