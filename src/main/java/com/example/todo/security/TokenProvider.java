package com.example.todo.security;

//토큰을 발급하고, 서명위조를 확인해주는 객체

import com.example.todo.userapi.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class TokenProvider {

    // 토큰 서명에 사용할 불변성을 가진비밀 키(512 바이트 이상의 랜덤 문자열)
    private static final String SECRET_KEY = "Q4NSl604sgyHJj1qwEkR3ycUeR4uUAt7WJraD7EN3O9DVM4yyYuHxMEbSF4XXyYJkal13eqgB0F7Bq4H";

    //토큰 발급 메서드
    public String createToken(UserEntity userEntity){
        //만료시간 설정
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );

        // 토큰 생성
        return Jwts.builder()
                //header에 들어갈 서명
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())
                        , SignatureAlgorithm.HS512
                ).setSubject(userEntity.getId()) //sub: 토큰 식별자
                .setIssuer("todo app")// iss:발급자 정보
                .setIssuedAt(new Date()) // iat:토큰 발급 시간
                .setExpiration(expiryDate) //exp: 토큰 만료 시간
                .compact();
    }
}
