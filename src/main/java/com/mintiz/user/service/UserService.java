package com.mintiz.user.service;
import com.mintiz.domain.User;
import com.mintiz.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.mintiz.user.model.UserSignupDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
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
                .loginId(userSignupDto.getLoginId())
                .password(passwordEncoder.encode(userSignupDto.getPassword()))
                .name(userSignupDto.getName())
                .level(userSignupDto.getLevel())
                .profile(userSignupDto.getProfile())
                .build());
    }


    // 회원 조회 (Mypage Controller에서 사용)
    public User findUser(Long id){
        return userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원"));

    }

    // 회원 조회 (로그인한 아이디로)
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원"));

    }

    /*
    // 아이디 중복 체크
    public int idCheck(Long id){
        int cnt = userRepository.idCheck(id);
        return cnt;
    }
    */


}

