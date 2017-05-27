package jp.co.hyron.ope.service;

import java.util.Collection;
import java.util.Optional;

import jp.co.hyron.ope.dto.AccountDto;
import jp.co.hyron.ope.entity.User;

public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    Collection<User> getAllUsers();

    User create(AccountDto dto);
    
    void update(User usr);
    

}