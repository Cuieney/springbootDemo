package com.example.demo;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRespository extends JpaRepository<User,Long> {

    @Query("select u from User u where u.name = :name")
    List<User> findByName(@Param("name")String name);
    User findByNameAndAge(String name, Integer age);



}
