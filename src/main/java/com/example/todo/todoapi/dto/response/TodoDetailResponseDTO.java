package com.example.todo.todoapi.dto.response;

import com.example.todo.todoapi.entity.TodoEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class TodoDetailResponseDTO {

    private String id;
    private String title;
    private boolean done;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 a hh시 mm분 ss초")
    private LocalDateTime regDate;

    // 엔터티를 받아서 DTO로 만들어주는 생성자
    public TodoDetailResponseDTO(TodoEntity entity) {
        this.id = entity.getTodoId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
        this.regDate = entity.getCreateDate();
    }
}