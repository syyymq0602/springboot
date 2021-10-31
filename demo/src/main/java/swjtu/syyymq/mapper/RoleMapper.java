package swjtu.syyymq.mapper;

import org.apache.ibatis.annotations.Mapper;
import swjtu.syyymq.entity.Role;

import java.util.List;

@Mapper
public interface RoleMapper {

    void add(Role role);

    void update(Role role);

    void delete(int id);

    List<Role> findAll();
}
