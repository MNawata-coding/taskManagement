package com.example.demo.dto.requestDto;

import java.time.LocalDateTime;

/**
 * タスク全取得返却用Dto
 * @param taskName タスク名
 * @param crateDate 作成日時
 */
public record GetAllTaskResponseDto(
    int id,
    String taskName, 
    LocalDateTime createDate) {}
