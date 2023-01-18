package com.example.todo.todoapi.repository;

import com.example.todo.todoapi.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity,String> { //uuid 타입이 string 이기 때문에

    // 완료되지 않은 할일들만 조회
    @Query("select t from TodoEntity t where t.done = 0")
    List<TodoEntity> findNotYetTodos();
}
