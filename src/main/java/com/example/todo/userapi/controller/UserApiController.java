package com.example.todo.userapi.controller;

import com.example.todo.userapi.dto.LoginRequestDTO;
import com.example.todo.userapi.dto.LoginResponseDTO;
import com.example.todo.userapi.dto.UserSignUpDTO;
import com.example.todo.userapi.dto.UserSignUpResponseDTO;
import com.example.todo.userapi.exception.DuplicatedEmailException;
import com.example.todo.userapi.exception.NoRegisteredArgumentsException;
import com.example.todo.userapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin
public class UserApiController {

    private final UserService userService;

    // 회원가입 요청 처리
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(
            @Validated @RequestBody UserSignUpDTO signUpDTO
            , BindingResult result
    ) {
        log.info("/api/auth/signup POST! - {}", signUpDTO);

        if (result.hasErrors()) {
            log.warn(result.toString());
            return ResponseEntity
                    .badRequest()
                    .body(result.toString());
        }

        try {
            UserSignUpResponseDTO responseDTO
                    = userService.create(signUpDTO);
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (NoRegisteredArgumentsException e) {
            // 예외 상황 2가지 (dto가 null인 문제, 이메일 중복문제)
            log.warn("필수 가입 정보를 다시확인하세요.");
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        } catch (DuplicatedEmailException e) {
            log.warn("중복되었습니다. 다른 이메일을 작성해주세요.");
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // 이메일 중복확인 요청 처리
    // GET : /api/auth/check?email=abc@bbb.com
    @GetMapping("/check")
    public ResponseEntity<?> checkEmail(String email) {
        if (email == null || email.trim().equals("")) {
            return ResponseEntity.badRequest().body("이메일을 전달해 주세요");
        }
        boolean flag = userService.isDuplicate(email);
        log.info("{} 중복 여부?? - {}", email, flag);
        return ResponseEntity.ok().body(flag);
    }

    // 로그인 요청 처리
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(
            @Validated @RequestBody LoginRequestDTO requestDTO) {

        try {
            LoginResponseDTO userInfo = userService.getByCredentials(
                    requestDTO.getEmail(),
                    requestDTO.getPassword()
            );
            return ResponseEntity
                    .ok()
                    .body(userInfo);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(LoginResponseDTO.builder()
                            .message(e.getMessage())
                            .build()
                    );
        }
    }

}