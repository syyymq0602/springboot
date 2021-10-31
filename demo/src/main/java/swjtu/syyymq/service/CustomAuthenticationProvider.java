package swjtu.syyymq.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import swjtu.syyymq.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// 此类为登陆校验核心类，可以使用此类自定义密码校验逻辑，待完善
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            this.logger.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        String presentedPassword = authentication.getCredentials().toString();
        String username = authentication.getPrincipal().toString();
        List<String> role = loginRemote(username, presentedPassword);
        if (CollectionUtils.isEmpty(role)) {
            throw new BadCredentialsException("错误的用户名和密码");
        }
        User user = (User) userDetails;
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String s : role) {
            authorities.add(() -> s);
        }
    }

    /**
     * 模拟远程登陆
     *
     * 这里我们实际就是加个本地判断
     * @param username
     * @param password
     */
    private List<String> loginRemote(String username, String password) {
        if ("admin".equals(username) && "adminpwd".equals(password)) {
            return Collections.singletonList("ROLE_ADMIN");
        } else if ("user".equals(username) && "userpwd".equals(password)) {
            return Collections.singletonList("ROLE_USER");
        } else if ("dba".equals(username) && "dbapwd".equals(password)) {
            List<String> list = new ArrayList<>();
            list.add("ROLE_USER");
            list.add("ROLE_DBA");
            return list;
        }
        return null;
    }
}
