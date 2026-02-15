package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * タスク用Entity
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name= "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String taskName; // タスク名
    private LocalDateTime createDateTime; // 登録日時

    @Builder
    private Task(String taskName){
        // タスク名
        this.taskName = taskName;
        // 登録日時
        this.createDateTime = LocalDateTime.now();
    }
}
