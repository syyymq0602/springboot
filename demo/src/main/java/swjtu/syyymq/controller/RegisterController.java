package swjtu.syyymq.controller;

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
import swjtu.syyymq.dto.mapper.CustomMapper;
import swjtu.syyymq.entity.User;
import swjtu.syyymq.mapper.UserMapper;
import swjtu.syyymq.service.MailService;
import swjtu.syyymq.utils.MD5Util;
import swjtu.syyymq.utils.RandomUtil;

import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final UserMapper userMapper;

    private final MailService mailService;

    private final RedisTemplate<String,Object> template;

    private final CustomMapper customMapper;

    @Value("${mail.fromMail.expiredTime}")
    private int expiredTime;

    @Autowired
    public RegisterController(UserMapper userMapper,
                              MailService mailService,
                              RedisTemplate<String, Object> template,
                              CustomMapper customMapper) {
        this.userMapper = userMapper;
        this.mailService = mailService;
        this.template = template;
        this.customMapper = customMapper;
    }

    /**
     * 核心注册逻辑代码
     * @param register 注册接收对象，包含用户名、密码、邮箱、验证码
     * @param attributes 重定向参数
     * @return 重定向路径
     */
    @PostMapping("/register")
    public String doRegister(RegisterDto register,
                             RedirectAttributes attributes){
        // TODO:注册时验证是否用户存在，验证密码有效性等
        String requestHash = (String)template.opsForValue().get("hash");
        if (StringUtils.hasText(requestHash)){
            String hash =  MD5Util.code(register.getIdentify());
            // 校验验证码是否正确
            if (requestHash.equalsIgnoreCase(hash)){
                //校验成功
                User user = customMapper.registerDtoToUser(register);
                userMapper.save(user);
                return "redirect:/login/index";
            }else {
                //验证码不正确，校验失败
                attributes.addFlashAttribute("message", "验证码输入不正确");
                return "redirect:/login/register";
            }
        } else {
            // 超时
            attributes.addFlashAttribute("message", "验证码已过期");
            return "redirect:/login/register";
        }
    }

    /**
     *
     * @param email 用户用于验证的邮箱
     * @return 验证结果
     * @throws Exception 验证异常
     */
    @RequestMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(String email) throws Exception {
        int code = RandomUtil.getRandom(6);    //随机数生成6位验证码
        String result = mailService.sendMail(email,code,expiredTime);
        if("success".equalsIgnoreCase(result)){
            String hash = MD5Util.code(Integer.toString(code));//生成MD5值
            assert hash != null;
            template.opsForValue().set("hash",hash,expiredTime, TimeUnit.MINUTES);
        }
        return result;
    }

    /**
     * 保存生成的验证码和过期时间
     * @param code 生成的随机数
     */
    @Deprecated
    private void saveCode(String code){
        String hash = MD5Util.code(code);//生成MD5值
        assert hash != null;
        template.opsForValue().set("hash",hash,expiredTime, TimeUnit.MINUTES);
    }
}
