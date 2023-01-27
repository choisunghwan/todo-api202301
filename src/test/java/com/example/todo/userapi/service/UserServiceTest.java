package com.example.todo.userapi.service;

import com.example.todo.userapi.dto.UserSignUpDTO;
import com.example.todo.userapi.dto.UserSignUpResponseDTO;
import com.example.todo.userapi.entity.UserEntity;
import org.apache.catalina.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("중복된 이메일이 포함된 회원정보로 가입하면 RuntimeException이 발생해야 한다.")
    void validateEmailTest() {
        // given
        UserSignUpDTO dto = UserSignUpDTO.builder()
                .email("abc1234@def.com")
                .password("54543")
                .userName("키키킥")
                .build();

        //when
        //then
        assertThrows(RuntimeException.class, () -> {
            userService.create(dto);
        });
    }


    @Test
    @DisplayName("검증된 회원정보로 가입하면 회원가입에 성공해야 한다.")
    void createTest() {
        // given
        UserSignUpDTO dto = UserSignUpDTO.builder()
                .email("zzzz9878@def.com")
                .password("1234")
                .userName("암호맨")
                .build();

        //when
        UserSignUpResponseDTO responseDTO = userService.create(dto);
        //then
        System.out.println("responseDTO = " + responseDTO);
        assertEquals("암호맨", responseDTO.getUserName());

    }


    @Test
    @DisplayName("존재하지 않는 이메일로 로그인을 시도하면 Exception이 발생해야한다.")
    void noUserTest() {
        //given
        String email = "dsfdsff@aasad.com";
        String password = "1234";

        //then
        assertThrows(RuntimeException.class, () -> {

            //when
            userService.getByCredentials(email, password);
        });
    }


    @Test
    @DisplayName("틀린 비밀번호로 로그인을 시도하면 Exception이 발생해야한다..")
    void invalidPasswordTest() {
        //given
        String email = "postman@aasad.com";
        String password = "1234";

        //then
        assertThrows(RuntimeException.class, () -> {

            //when
            userService.getByCredentials(email, password);
        });
    }


    @Test
    @DisplayName("정확한 정보로 로그인을 시도하면 회원정보가 반환되어야 한다..")
    void loginTest() {
        //given
        String email = "abcd@gmail.com";
        String password = "1q2w3e4r!";

        //when
        UserEntity loginUser = userService.getByCredentials(email, password);
        //then
        assertEquals("야호", loginUser.getUserName());
    }

}





