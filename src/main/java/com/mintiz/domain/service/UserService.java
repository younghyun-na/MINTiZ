package com.mintiz.domain.service;

import com.mintiz.domain.User;
import com.mintiz.domain.dto.UserSignupDto;
import com.mintiz.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    // 회원 가입
    @Transactional
    public User join(UserSignupDto userSignupDto) {

        // 중복 회원 검증
        if(userRepository.findByEmail(userSignupDto.getEmail()).isPresent())
            throw new IllegalStateException("이미 존재하는 회원입니다.");

        // 회원 객체 생성 후 저장
        return userRepository.save(User.builder()
                .email(userSignupDto.getEmail())
                .id(Long.valueOf(userSignupDto.getId()))
                .password(passwordEncoder.encode(userSignupDto.getPassword()))
                .name(userSignupDto.getName())
                .level(userSignupDto.getLevel())
                .build());

    }

}
