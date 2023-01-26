package com.example.todo.userapi.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;


@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") // id 만 비교해라. 굳이 비번,이름까지 비교할 필요x
@Builder
@Entity
@Table(name = "tbl_user")
public class UserEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name ="system-uuid", strategy = "uuid") // uuid : universal unique id (중복되지 않는 id)
    private String id; //계정명이 아니라 식별 코드

    @Column(unique = true, nullable = false) //중복하면 안된다! 중복 제약 조건
    private String email;

    @Column(nullable = false) //필수다! null 값 x
    private String password;

    @Column(nullable = false)
    private  String userName;

    @CreationTimestamp
    private LocalDateTime joinDate;
}
