package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.customException.TodoNotFoundException;
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
        return getTaskService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getNotFoundTest(@PathVariable Long id) {

        // 404:リソースが存在しない場合に発生
        if(id != 1){
            throw new TodoNotFoundException();
        }

        System.out.println("name:" + HttpStatus.INTERNAL_SERVER_ERROR.name());
        System.out.println("value:" + HttpStatus.INTERNAL_SERVER_ERROR.value());

        // 入らなかった場合200を返却
        return ResponseEntity.ok().body("resourceSuccess");
    }

    /**
     * タスクを登録する
     * @param dto
     * @return
     * ssh-keygen -t ed25519 -C "nam.man344@gmail.com"
     */
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody @Valid RegisterTaskRequestDto dto){
        System.out.println("タスク登録");
        crateService.addTask(dto);

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
