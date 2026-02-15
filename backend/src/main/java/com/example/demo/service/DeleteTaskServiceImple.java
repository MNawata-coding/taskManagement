package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DeleteTaskServiceImple implements DeleteTaskService {

    private final TaskRepository repository;

    /**
     * 指定IDのタスクを削除する
     * 
     * @param id タスクID
     */
    @Override
    public void deleteTaskById(int taskId){
        repository.deleteById(taskId);
    }
}