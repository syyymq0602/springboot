package swjtu.syyymq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import swjtu.syyymq.dto.RegisterDto;
import swjtu.syyymq.entity.User;
import swjtu.syyymq.mapper.UserMapper;
import swjtu.syyymq.service.MailService;
import swjtu.syyymq.utils.MD5Util;
import swjtu.syyymq.utils.RandomUtil;

import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/validate")
public class ForgetController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
        int code = RandomUtil.getRandom(6);
        String resetPasswordEmail = mailService.resetPasswordEmail(email,code,expiredTime);
        if("success".equalsIgnoreCase(resetPasswordEmail)){
            String hash = MD5Util.code(Integer.toString(code));//生成MD5值
            assert hash != null;
            template.opsForValue().set("hash",hash,expiredTime, TimeUnit.MINUTES);
        }
        return resetPasswordEmail;
    }

    @PostMapping("/update")
    public String update(RegisterDto register){
        String requestHash = (String)template.opsForValue().get("hash");
        if (StringUtils.hasText(requestHash)){
            String hash =  MD5Util.code(register.getIdentify());
            // 校验验证码是否正确
            if (requestHash.equalsIgnoreCase(hash)){
                // 校验成功
                User user = new User();
                user.setUsername(register.getUsername());
                user.setPassword(new BCryptPasswordEncoder().encode(register.getPassword()).trim());
                userMapper.updatePassword(user);
                return "redirect:/login/index";
            }else {
                // 验证码不正确，校验失败
                logger.error("验证码输入不正确");
                return "redirect:/login/forget";
            }
        } else {
            // 超时
            logger.error("验证码已过期");
            return "redirect:/login/forget";
        }
    }
}
