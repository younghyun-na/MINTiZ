package com.mintiz.user;

import com.mintiz.domain.User;
import com.mintiz.user.model.UserSignupDto;
import com.mintiz.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public abstract class UserService {

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
                .id(Long.valueOf(userSignupDto.getId()))
                .password(passwordEncoder.encode(userSignupDto.getPassword()))
                .name(userSignupDto.getName())
                .build());
    }

    // 아이디로 회원 조회
    public User findUser(Long userId){
        return userRepository.findById(userId).orElseThrow(
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
