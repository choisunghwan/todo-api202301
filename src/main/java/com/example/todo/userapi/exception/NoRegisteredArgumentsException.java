package com.example.todo.userapi.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoRegisteredArgumentsException extends RuntimeException{

    // 기본 생성자

    // 에러메시지를 처리하는 생성자

    public NoRegisteredArgumentsException(String message){
        super(message);
    }
}
