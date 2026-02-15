package com.example.demo.dto.requestDto;

/**
 * idを1つ指定して削除する
 * @param id 削除するタスクID
 */
public record DeleteTaskRequestDto (
    int taskId
) {}
