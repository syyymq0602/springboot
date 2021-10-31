package swjtu.syyymq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import swjtu.syyymq.service.CustomUserService;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserService customUserService;

    // 链式编程
    // 授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 放行主界面和登录界面
                .antMatchers("/","/toLogin","/register").permitAll()
                // 放行静态资源
                .antMatchers("/css/**","/js/**","/images/**").permitAll()
                // 根据授权访问对应资源
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/root/**").hasRole("root")
                        .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/toLogin")
                    .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/toLogin");


        http.csrf().disable();

        http.logout().logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
