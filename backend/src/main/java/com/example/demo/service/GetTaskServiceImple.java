package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.requestDto.GetAllTaskResponseDto;
import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

/**
 * タスク取得用サービス
 */
@RequiredArgsConstructor
@Service
public class GetTaskServiceImple implements GetTaskService {
    
    private final TaskRepository taskRepository;

    /**
     * すべてのタスクを取得して返却する
     * 指定条件無し
     * @return
     */
    @Override
    public List<GetAllTaskResponseDto> getAll(){
        // すべてのタスクを取得する
        List<Task> taskList = taskRepository.findAll();

        // 返却用Dtoを作成して返却する
        return createAllTaskResponseDto(taskList);
    }

    /**
     * 樹得したタスクを全件Dto煮詰めて返却する
     * 存在しない場合は空のリストを返却する
     * 
     * @param taskList
     * @return
     */
    private List<GetAllTaskResponseDto> createAllTaskResponseDto(List<Task> taskList){
        // 詰替え用Dto
        List<GetAllTaskResponseDto> allTaskResponseDtoList = new ArrayList<>();

        // タスクをすべて詰め替える
        for(Task task : taskList){
            GetAllTaskResponseDto taskResponseDto = new GetAllTaskResponseDto(
                task.getId(),
                // タスク名
                task.getTaskName(),
                // 作成日時
                task.getCreateDateTime()
            );

            allTaskResponseDtoList.add(taskResponseDto);
        }

        return allTaskResponseDtoList;
    }

}
