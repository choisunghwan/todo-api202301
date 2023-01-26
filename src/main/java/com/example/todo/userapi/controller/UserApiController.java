package com.example.todo.userapi.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
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
}