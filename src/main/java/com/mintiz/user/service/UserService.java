package com.mintiz.user.service;
import com.mintiz.domain.ImageFile;
import com.mintiz.domain.User;
import com.mintiz.file.FileStore;
import com.mintiz.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import com.mintiz.user.model.UserSignupDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStore fileStore;


    // 회원 가입
    @Transactional
    public User join(UserSignupDto userSignupDto) {

        // 중복 회원 검증
        if(userRepository.findByEmail(userSignupDto.getEmail()).isPresent())
            throw new IllegalStateException("이미 존재하는 회원입니다.");

        User user = User.builder()
                .email(userSignupDto.getEmail())
                .password(passwordEncoder.encode(userSignupDto.getPassword()))
                .loginId(userSignupDto.getLoginId())
                .name(userSignupDto.getName())
                .level(userSignupDto.getLevel())
                .build();
        try{
            ImageFile image = fileStore.storeFile(userSignupDto.getProfile());
            user.setProfile(image);
        }catch(Exception e){
            e.printStackTrace();
        }

        // 회원 객체 생성 후 저장
        return userRepository.save(user);
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


    // 아이디 중복 체크
    public boolean emailCheck(String email){
        return userRepository.findByEmail(email).isPresent();
    }



}

