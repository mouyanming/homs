package jp.co.hyron.ope.service.impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.hyron.ope.dto.AccountDto;
import jp.co.hyron.ope.entity.User;
import jp.co.hyron.ope.repository.UserRepository;
import jp.co.hyron.ope.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
    private  UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Optional<User> getUserById(long id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    @Override
    public Collection<User> getAllUsers() {
        return userRepository.findAll(new Sort("email"));
    }

    @Override
    public User create(AccountDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        return userRepository.saveAndFlush(user);
    }

	@Override
	public void update(User usr) {
		userRepository.saveAndFlush(usr);
		
	}

}
