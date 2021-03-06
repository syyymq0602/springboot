package swjtu.syyymq.mapper;

import org.apache.ibatis.annotations.Mapper;
import swjtu.syyymq.entity.Role;
import swjtu.syyymq.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

    User findByUsername(String name);

    List<User> findAll();

    List<Role> getUserRoleById(Integer id);

    void save(User user);

    void updatePassword(User user);

    void delete(String name);
    // TODO:待修改
    void updateUser(User user);
}
