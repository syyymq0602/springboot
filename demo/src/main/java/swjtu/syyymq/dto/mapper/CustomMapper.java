package swjtu.syyymq.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import swjtu.syyymq.dto.EditDto;
import swjtu.syyymq.dto.RegisterDto;
import swjtu.syyymq.entity.Role;
import swjtu.syyymq.entity.User;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomMapper {
    @Mappings({
            @Mapping(source = "username",target = "username"),
            @Mapping(source = "password",target = "password",qualifiedByName = "code"),
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "enabled",ignore = true),
            @Mapping(target = "locked",ignore = true),
            @Mapping(target = "expired",ignore = true),
            @Mapping(target = "credentialsExpire",ignore = true),
            @Mapping(target = "roles",ignore = true)
    })
    User registerDtoToUser(RegisterDto registerDto);

    @Mappings({
            @Mapping(target = "roles",source = "roles",qualifiedByName = "rolesConvert"),
    })
    EditDto userToEditDto(User user);

    @Named("code")
    static String code(String password){
        return new BCryptPasswordEncoder().encode(password);
    }

    @Named("rolesConvert")
    static List<String> rolesConvert(List<Role> roles){
        if(roles==null||roles.size()==0){
            return null;
        }
        List<String> lists = new ArrayList<>();
        for (Role role : roles) {
            lists.add(role.getNameZh());
        }
        return lists;
    }
}
