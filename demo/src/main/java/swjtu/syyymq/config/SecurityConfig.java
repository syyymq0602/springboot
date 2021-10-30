package swjtu.syyymq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import swjtu.syyymq.entity.User;
import swjtu.syyymq.repository.UserDetailsRepository;
import swjtu.syyymq.service.UserService;

import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    // 链式编程
    // 授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 放行主界面和登录界面
                .antMatchers("/","/toLogin").permitAll()
                // 放行静态资源
                .antMatchers("/css/**","/js/**","/images/**").permitAll()
                // 根据授权访问对应资源
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/root/**").hasRole("root")
                        .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/toLogin")
                    .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/success");


        http.csrf().disable();

        http.logout().logoutSuccessUrl("/");
    }

    // 认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        List<User> users = userService.findAll();
        if (users==null||users.size()==0){
            throw new UsernameNotFoundException("用户未找到");
        }
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin").password(new BCryptPasswordEncoder().encode("123")).roles("admin")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("123")).roles("root");
    }

    @Bean
    public UserDetailsRepository userDetailsRepository(){
        UserDetailsRepository userDetailsRepository = new UserDetailsRepository();

        // 测试是否可以登录运行
        UserDetails test = org.springframework.security.core.userdetails.User.withUsername("test")
                .password("{noop}123").authorities(AuthorityUtils.NO_AUTHORITIES).build();
        userDetailsRepository.createUser(test);
        return userDetailsRepository;
    }

    @Bean
    public UserDetailsManager userDetailsManager(UserDetailsRepository userDetailsRepository){
        return new UserDetailsManager() {
            @Override
            public void createUser(UserDetails user) {
                userDetailsRepository.createUser(user);
            }

            @Override
            public void updateUser(UserDetails user) {
                userDetailsRepository.updateUser(user);
            }

            @Override
            public void deleteUser(String username) {
                userDetailsRepository.deleteUser(username);
            }

            @Override
            public void changePassword(String oldPassword, String newPassword) {
                userDetailsRepository.changePassword(oldPassword,newPassword);
            }

            @Override
            public boolean userExists(String username) {
                return userDetailsRepository.userExists(username);
            }

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userDetailsRepository.loadUserByUsername(username);
            }
        };
    }

}
