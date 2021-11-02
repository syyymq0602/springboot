package swjtu.syyymq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${mail.fromMail.sender}")
    private String sender;

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String sendMail(String email,int code,int expired){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSentDate(new Date());
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

    // TODO:后续需要发送对应链接
    public String resetPasswordEmail(String email,int code,int expired){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSentDate(new Date());
            helper.setFrom(sender);
            helper.setTo(email);
            helper.setSubject("忘记密码"); // 标题
//            StringBuilder sb = new StringBuilder();
//            sb.append("<html><head></head>");
//            sb.append("<body><h1>点击下面的链接重置密码</h1>" +
//                    "<a href = "+appUrl +"/validate/resetPassword?token="+validateDao.getResetToken()+">"+appUrl +"/validate/resetPassword?token=" +validateDao.getResetToken()+"</a></body>");
//            sb.append("</html>");
//            helper.setText(sb.toString(),true);
            helper.setText("【忘记密码】你的验证码为："+code+"，有效时间为"+expired+"分钟(若不是本人操作，可忽略该条邮件)",true);
            javaMailSender.send(mimeMessage);
            logger.info("邮件发送成功!");
            return "success";
        }catch (MessagingException e){
            logger.error("发送邮件时产生异常!");
            return "failure";
        }
    }
}
