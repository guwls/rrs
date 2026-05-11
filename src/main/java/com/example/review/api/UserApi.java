package com.example.review.api;

import com.example.review.api.request.LoginRequest;
import com.example.review.api.request.SignUpRequest;
import com.example.review.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApi {
    private final UserService userService;

    @PostMapping("/sign-up")
    public void signUp(
            @RequestBody SignUpRequest request
    ) {
        userService.signUp(request);

    }

    @PostMapping("/login")
    public String  login(
            @RequestBody LoginRequest request
    ){
        return userService.login(request);
    }

}
