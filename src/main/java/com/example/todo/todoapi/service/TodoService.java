package com.example.todo.todoapi.service;

import com.example.todo.todoapi.entity.TodoEntity;
import com.example.todo.todoapi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    //할 일 목록 조회
    public List<TodoEntity> retrieve(){
        return todoRepository.findAll();
    }


}
