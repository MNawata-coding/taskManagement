package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.requestDto.RegisterTaskRequestDto;
import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public final class CreateTaskServiceImple implements CreateTaskService {

    private final TaskRepository taskRepository;

    @Override
    public void addTask(RegisterTaskRequestDto dto){
        // builderで設定する
        Task task = Task.builder()
        .taskName(dto.taskName())
        .build();
        taskRepository.save(task);
    }
}
