package swjtu.syyymq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import swjtu.syyymq.service.CustomUserService;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserService customUserService;

    // 链式编程
    // 授权
    // 放行部分资源，定义登陆校验页以及登陆成功跳转
    // 关闭csrf保护
    // 定义注销后默认跳转到登录页
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 放行主界面、登录界面、注册页面、邮箱验证请求
                .antMatchers("/","/login/**","/register/**","/login**","/validate/**","/user/**").permitAll()
                // 放行静态资源
                .antMatchers("/css/**","/js/**","/images/**","/assets/**").permitAll()
                // 根据授权访问对应资源
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/root/**").hasRole("root")
                        .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login/index")
                    .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/login/success");


        http.csrf().disable();

        http.logout().logoutSuccessUrl("/login/index");
    }

    @Override
    // 注册自定义登录验证逻辑
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService);
    }

    @Bean
    // 使Security内部默认使用BCrypt加密解密
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
