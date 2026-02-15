package com.example.demo.service;


import org.springframework.stereotype.Service;

import com.example.demo.dto.requestDto.RegisterTaskRequestDto;

/**
 * タスク保存用
 */
@Service
public interface CreateTaskService {
    // タスクを作成する
    abstract void addTask(RegisterTaskRequestDto task);
}
