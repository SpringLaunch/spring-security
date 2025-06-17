package com.example.userService.repository;

import com.example.userService.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{

    Optional<UserEntity> findByUsernameAndEnabled(String username, boolean enabled);

    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE (u.username = :input OR u.email = :input) AND u.enabled = true")
    Optional<UserEntity> findEnabledUserByUsernameOrEmail(@Param("input") String input);


}
