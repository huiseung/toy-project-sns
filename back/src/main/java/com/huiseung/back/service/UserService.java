package com.huiseung.back.service;

import com.huiseung.back.entity.user.User;
import com.huiseung.back.error.NotFoundException;
import com.huiseung.back.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public User join(String name, String email, String password){
        checkArgument(name != null, "name must be provided");
        checkArgument(email != null, "email must be provided");
        checkArgument(password != null, "password must be provided");
        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        return save(user);
    }

    @Transactional
    public User login(String email, String password){
        checkArgument(email != null, "email must be provided");
        checkArgument(password != null, "password must be provided");
        User user = findByEmail(email);
        user.login(passwordEncoder, password);
        return save(user);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email){
        checkArgument(email != null, "email must be provided");
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(User.class, email));
    }

    private User save(User user){
        return userRepository.save(user);
    }
}
