package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.requestDto.GetAllTaskResponseDto;
import com.example.demo.dto.requestDto.RegisterTaskRequestDto;
import com.example.demo.service.CreateTaskService;
import com.example.demo.service.DeleteTaskService;
import com.example.demo.service.GetTaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final CreateTaskService crateService;
    private final GetTaskService getTaskService;
    private final DeleteTaskService deleteTaskServce;

    /**
     * すべてのタスクを返却する
     * 
     * @param param
     * @return GetAllTaskResponseDto 取得できたすべてのタスク
     */
    @GetMapping("/getAll")
    public List<GetAllTaskResponseDto> getAllTask() {
        System.out.println(getTaskService.getAll());
        return getTaskService.getAll();
    }

    /**
     * タスクを登録する
     * @param dto
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody @Valid RegisterTaskRequestDto dto){
        System.out.println("タスク登録");
        crateService.addTask(dto);

        boolean flg = true;

        if(flg){
            return ResponseEntity.notFound().build();
        }

        URI location = URI.create("/articles/" + dto.taskName());

        // 作成された場合、ステータスコード201を返却する
        // locationで指定されたページにアクセスする新しいリクエストを生成するためにlocationを指定する
        return ResponseEntity
            .created(location)
            .body("success::create");
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        deleteTaskServce.deleteTaskById(id);
    }
}
