package com.example.todo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*서버가 돌아가는지 확인해주기 위한 컨트롤러*/
@RestController
@Slf4j
@CrossOrigin

public class HealthCheckController {

    @GetMapping("/")
    public ResponseEntity<?> check(){
        log.info("server is running...");
        return ResponseEntity
                .ok()
                .body("server is running...");
    }
}
