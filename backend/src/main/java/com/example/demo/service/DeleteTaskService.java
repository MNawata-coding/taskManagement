package com.example.demo.service;

import org.springframework.stereotype.Service;

/**
 * タスク削除用サービス
 */
@Service
public interface DeleteTaskService {
    /**
     * 指定したIDのタスクを削除する
     * 
     * @param id
     */
    abstract void deleteTaskById(int taskId);
}