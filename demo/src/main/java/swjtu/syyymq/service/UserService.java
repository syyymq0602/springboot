package swjtu.syyymq.service;


import swjtu.syyymq.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findByUsername(String name);
}
