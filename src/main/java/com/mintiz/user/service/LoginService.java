package com.mintiz.user.service;

import com.mintiz.domain.User;
import com.mintiz.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 로그인 기능
    public User login(String loginId, String password){
        return userRepository.findByLoginId(loginId)
                .filter(m -> passwordEncoder.matches(password,m.getPassword()))
                .orElse(null);
    }

}
