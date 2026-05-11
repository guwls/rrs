package com.example.review.service;

import com.example.review.api.request.LoginRequest;
import com.example.review.api.request.SignUpRequest;
import com.example.review.model.UserEntity;
import com.example.review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public void signUp(SignUpRequest request) {
        // 1. 이메일 중복 체크
       if(userRepository.findByEmail(request.getEmail()).isPresent()) {
           throw new RuntimeException("이미 존재하는 이메일입니다.");
       }

        // 2. 비밀번호 암호화
       String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 3. UserEntity 빌드 후 저장
        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .createdAt(ZonedDateTime.now())
                .build();

        userRepository.save(user);
    }

    //로그인
    public String login(LoginRequest request) {
        //1. 이메일 유저 조회
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("없는 유저입니다."));

        //2. 비밀번호 검증
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }


        //3. JWT Access Token 발급 후 반환
        return jwtUtil.generateAccessToken(user.getEmail());
    }


}
