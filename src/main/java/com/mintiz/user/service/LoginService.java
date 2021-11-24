package com.mintiz.user.service;

import com.mintiz.domain.User;
import com.mintiz.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    /*
    // 로그인 기능
    public User login(Long id, String password){
        return userRepository.findById(id)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
    */

}
