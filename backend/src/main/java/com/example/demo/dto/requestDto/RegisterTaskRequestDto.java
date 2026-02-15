package com.example.demo.dto.requestDto;

import java.time.LocalDateTime;

/**
 * タスク登録用DTO
 * @param taskName タスク名
 * @param createDate 作成日時
 */
public record RegisterTaskRequestDto(
    String taskName,
    LocalDateTime createDate
) {
    /**
     * コンパクトコンストラクタ
     * 設定されているもの以外は自動で初期化
     * 
     * @param taskName
     * @param createDate
     */
    public RegisterTaskRequestDto{
        //現在時刻を取得して設定
        createDate = LocalDateTime.now();
    }

    // /**
    //  * 通常のコンストラクタ
    //  */
    // public TaskRequestDto(String taskName, LocalDateTIme createDate){
    //     this.taskName = taskName;
    //     this.createDate = createDate;
    // }
}