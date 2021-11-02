package swjtu.syyymq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${mail.fromMail.sender}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;

    public String sendMail(String email,int code,int expired){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(email);
        message.setSubject("注册系统");// 标题
        message.setText("【注册系统】你的验证码为："+code+"，有效时间为"+expired+"分钟(若不是本人操作，可忽略该条邮件)");
        try {
            javaMailSender.send(message);
            logger.info("文本邮件发送成功！");
            return "success";
        }catch (MailSendException e){
            logger.error("目标邮箱不存在");
            return "false";
        } catch (Exception e) {
            logger.error("文本邮件发送异常！", e);
            return "failure";
        }
    }

    public String resetPasswordEmail(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(email);
        message.setSubject("忘记密码");// 标题
        message.setText("【忘记密码】您正在申请重置密码，请点此链接重置密码");
        return null;
    }
}
