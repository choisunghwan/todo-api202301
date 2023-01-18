package com.example.todo.todoapi.repository;

import com.example.todo.todoapi.entity.TodoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Commit // test 실행 후 커밋
class TodoRepositoryTest {

    @Autowired
    TodoRepository todoRepository;


    @BeforeEach
    void insertTest(){
        TodoEntity todo1 = TodoEntity.builder().title("저녁 장보기").build();
        TodoEntity todo2 = TodoEntity.builder().title("책 읽기").build();
        TodoEntity todo3 = TodoEntity.builder().title("목욕하기").build();

        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);
    }

    @Test
    @DisplayName("할 일 목록을 조회하면 리스트의 사이즈가 3이어야 한다.")
    void findAllTest(){
        //given
        //when
        List<TodoEntity> list = todoRepository.findAll();
        //then
        assertEquals(3,list.size());

    }


}