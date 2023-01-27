package com.example.todo.todoapi.controller;

import com.example.todo.todoapi.dto.request.TodoCreateRequestDTO;
import com.example.todo.todoapi.dto.request.TodoModifyRequestDTO;
import com.example.todo.todoapi.dto.response.TodoListResponseDTO;
import com.example.todo.todoapi.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/todos")
// CORS 허용 설정
@CrossOrigin
public class TodoApiController {

    private final TodoService todoService;

    // 할 일 등록 요청
    @PostMapping
    public ResponseEntity<?> createTodo(
            @AuthenticationPrincipal String userId
            , @Validated @RequestBody TodoCreateRequestDTO requestDTO
            , BindingResult result

    ) {
        if (result.hasErrors()) {
            log.warn("DTO 검증 에러 발생 : {}", result.getFieldError());
            return ResponseEntity
                    .badRequest()
                    .body(result.getFieldError());
        }

        try {
            TodoListResponseDTO responseDTO = todoService.create(requestDTO, userId);
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(TodoListResponseDTO.builder().error(e.getMessage()));
        }

    }

    // 할 일 삭제 요청
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(
            @AuthenticationPrincipal String userId
            , @PathVariable("id") String todoId
    ) {
        log.info("/api/todos/{} DELETE request!", todoId);

        if (todoId == null || todoId.trim().equals("")) {
            return ResponseEntity
                    .badRequest()
                    .body(TodoListResponseDTO.builder().error("ID를 전달해주세요"));
        }

        try {
            TodoListResponseDTO responseDTO = todoService.delete(todoId, userId);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(TodoListResponseDTO.builder().error(e.getMessage()));
        }
    }

    // 할 일 목록요청 (GET)
    @GetMapping
    public ResponseEntity<?> retrieveTodoList(
            // 인증완료처리시 등록했던 값을 넣어줌
            @AuthenticationPrincipal String userId
    ) {
        log.info("/api/todos GET request!");

        TodoListResponseDTO responseDTO = todoService.retrieve(userId);

        return ResponseEntity.ok().body(responseDTO);

    }

    // 할 일 수정요청 (PUT, PATCH)
    @RequestMapping(
            value = "/{id}"
            , method = {RequestMethod.PUT, RequestMethod.PATCH}
    )
    public ResponseEntity<?> updateTodo(
            @AuthenticationPrincipal String userId
            , @PathVariable("id") String todoId
            , @Validated @RequestBody TodoModifyRequestDTO requestDTO
            , BindingResult result
            , HttpServletRequest request
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(result.getFieldError());
        }

        log.info("/api/todos/{} {} request", todoId, request.getMethod());
        log.info("modifying dto : {}", requestDTO);

        try {
            TodoListResponseDTO responseDTO = todoService.update(todoId, requestDTO, userId);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(TodoListResponseDTO.builder().error(e.getMessage()));
        }
    }

}
