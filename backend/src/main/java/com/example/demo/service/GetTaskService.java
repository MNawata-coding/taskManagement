package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.requestDto.GetAllTaskResponseDto;

@Service
public interface GetTaskService {
    /**
     * すべて取得用
     * @return
     */
    abstract List<GetAllTaskResponseDto> getAll();
}
