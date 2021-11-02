package swjtu.syyymq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import swjtu.syyymq.dto.RegisterDto;
import swjtu.syyymq.entity.User;
import swjtu.syyymq.mapper.UserMapper;
import swjtu.syyymq.service.MailService;
import swjtu.syyymq.utils.MD5Utils;
import swjtu.syyymq.utils.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RegisterController {
    private final UserMapper userMapper;

    private final MailService mailService;

    private final Map<String, Object> resultMap = new HashMap<>();

    @Value("${mail.fromMail.expiredTime}")
    private int expiredTime;

    @Autowired
    public RegisterController(UserMapper userMapper, MailService mailService) {
        this.userMapper = userMapper;
        this.mailService = mailService;
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(RegisterDto register,
                             RedirectAttributes attributes){
        // TODO:注册时验证是否用户存在，验证密码有效性等
        if (resultMap.size() ==0){
            return "register";
        }
        // 判断验证码是否正确
        String requestHash = resultMap.get("hash").toString();
        String tamp = resultMap.get("tamp").toString();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");//当前时间
        Calendar c = Calendar.getInstance();
        String currentTime = sf.format(c.getTime());
        if (tamp.compareTo(currentTime) > 0) {
            // 生成MD5值
            String hash =  MD5Utils.code(register.getIdentify());
            if (requestHash.equalsIgnoreCase(hash)){
                //校验成功
                User user = new User();
                user.setUsername(register.getUsername());
                user.setPassword(new BCryptPasswordEncoder().encode(register.getPassword()).trim());
                userMapper.save(user);
                return "redirect:/toLogin";
            }else {
                //验证码不正确，校验失败
                attributes.addFlashAttribute("message", "验证码输入不正确");
                return "redirect:/register";
            }
        } else {
            // 超时
            attributes.addFlashAttribute("message", "验证码已过期");
            return "redirect:/register";
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
        int code = RandomUtils.getRandom(6);    //随机数生成6位验证码
        String result = mailService.sendMail(email,code,expiredTime);
        if("success".equalsIgnoreCase(result)){
            saveCode(Integer.toString(code));
        }
        return result;
    }

    /**
     * 保存生成的验证码和过期时间
     * @param code 生成的随机数
     */
    private void saveCode(String code){
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, expiredTime);
        String currentTime = sf.format(c.getTime());// 生成3分钟后时间，用户校验是否过期
        String hash = MD5Utils.code(code);//生成MD5值
        resultMap.put("hash", hash);
        resultMap.put("tamp", currentTime);
    }
}
