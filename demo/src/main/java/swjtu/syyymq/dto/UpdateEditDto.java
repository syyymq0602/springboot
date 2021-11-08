package swjtu.syyymq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEditDto {
    private Integer id;
    private String username;
    private String enabled;
    private String locked;
    private String expired;
    private String credentialsExpire;

    private List<String> roles;
}
