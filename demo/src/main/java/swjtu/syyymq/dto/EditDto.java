package swjtu.syyymq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditDto {
    private Integer id;
    private String username;
    private Boolean enabled;
    private Boolean locked;
    private Boolean expired;
    private Boolean credentialsExpire;

    private List<String> roles;

    @Override
    public String toString() {
        return "EditDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", enabled=" + enabled +
                ", locked=" + locked +
                ", expired=" + expired +
                ", credentialsExpire=" + credentialsExpire +
                ", roles=" + roles +
                '}';
    }
}
