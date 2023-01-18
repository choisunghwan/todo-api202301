package com.example.todo.todoapi.dto.request;

import com.example.todo.todoapi.entity.TodoEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@Builder

public class TodoCreateRequestDTO {

    @NotBlank
    @Size(min = 2, max = 10)
    private String title;

    //이 dto를 엔터티로 변환
    public TodoEntity toEntity(){
        return TodoEntity.builder()
                .title(this.title)
                .build();
    }

}
