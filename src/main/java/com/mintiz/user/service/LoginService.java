package com.mintiz.user.service;

import com.mintiz.domain.User;
import com.mintiz.user.SessionConst;
import com.mintiz.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    //로그인 유저 정보 가져오기
    public User getLoginUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (User)session.getAttribute(SessionConst.LOGIN_MEMBER);
    }

}
