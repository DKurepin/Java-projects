package com.kurepin.service;

import com.kurepin.entities.dto.CommentDto;
import com.kurepin.entities.dto.TaskDto;


import java.text.ParseException;
import java.util.List;


public interface CommentService {
    CommentDto saveComment(CommentDto CommentDto) throws ParseException;
    void deleteByCommentId(Long id);
    void deleteAllComments();
    CommentDto updateComment(CommentDto commentDto) throws ParseException;
    CommentDto getCommentById(Long id);
    List<CommentDto> getAllComments();
    List<CommentDto> getAllCommentsByAuthor(String author);
    List<CommentDto> getAllCommentsByTaskId(TaskDto taskDto) throws ParseException;
}
