package com.example.todo.todoapi.entity;
//일정관리 프로그램


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "todoId")
@Builder

@Entity // 테이블과의 매핑. @Entity 가 붙은 클래스는 JPA가 관리하는 것으로, 엔티티라고 불림.
@Table(name = "tbl_todo") //설계된 db 테이블 이름 적으면 됨.
public class TodoEntity {

    @Id // @ID : 기본 키 매핑
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name ="system-uuid", strategy = "uuid") // uuid : universal unique id (중복되지 않는 id)
    private String todoId; // 절대 중복되지 않는 랜덤 문자가 나옴.

    @Column(nullable = false, length = 30)  // 제목이 필수에요! (not null) , 제목은 30자까지! / @Column: 필드와 컬럼명 매핑
    private String title; // 제목

    private boolean done; // 일정 완료 여부

    @CreationTimestamp
    private LocalDateTime createDate; // 등록 시간

}
