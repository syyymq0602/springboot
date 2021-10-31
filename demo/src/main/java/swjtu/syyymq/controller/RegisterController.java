package swjtu.syyymq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import swjtu.syyymq.utils.MD5Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class RegisterController {
    @Autowired
    private UserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${mail.fromMail.sender}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;

    private final Map<String, Object> resultMap = new HashMap<>();

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
            String hash =  MD5Utils.code(register.getIdentify());//生成MD5值
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

    @RequestMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        String code = VerifyCode();    //随机数生成6位验证码
        message.setFrom(sender);
        message.setTo(email);
        message.setSubject("博客系统");// 标题
        message.setText("【博客系统】你的验证码为："+code+"，有效时间为3分钟(若不是本人操作，可忽略该条邮件)");// 内容
        try {
            javaMailSender.send(message);
            logger.info("文本邮件发送成功！");
            saveCode(code);
            return "success";
        }catch (MailSendException e){
            logger.error("目标邮箱不存在");
            return "false";
        } catch (Exception e) {
            logger.error("文本邮件发送异常！", e);
            return "failure";
        }
    }

    // 确定验证码是否正确
    private String VerifyCode(){
        Random r = new Random();
        StringBuilder sb =new StringBuilder();
        for(int i = 0;i < 6;i ++){
            int ran1 = r.nextInt(10);
            sb.append(ran1);
        }
        return sb.toString();
    }

    // 保存验证码和时间
    private void saveCode(String code){
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 3);
        String currentTime = sf.format(c.getTime());// 生成3分钟后时间，用户校验是否过期
        String hash =  MD5Utils.code(code);//生成MD5值
        resultMap.put("hash", hash);
        resultMap.put("tamp", currentTime);
    }
}
