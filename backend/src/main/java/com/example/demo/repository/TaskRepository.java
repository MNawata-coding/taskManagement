package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Task;


@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>  {
    // 必要なものがあれば都度実装する
    // 基本CRUDはjpaを利用
}
