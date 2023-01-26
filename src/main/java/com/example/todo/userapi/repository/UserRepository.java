package com.example.todo.userapi.repository;

import com.example.todo.userapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository <UserEntity,String>{

    //이메일로 회원 조회
    // select * from tbl_user where email=?
    UserEntity findByEmail(String email);

    //이메일 중복 검사 : 카운트 했을때 중복안된경우 0건 , 중복일 경우 1건 이상..
    // select count(*) from tbl_user where email=?
//    @Query("select count(*) from UserEntity u where u.email=?1")
    boolean existsByEmail(String email);
}
