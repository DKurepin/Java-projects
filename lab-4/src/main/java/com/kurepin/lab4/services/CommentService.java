package com.kurepin.lab4.services;

import com.kurepin.lab4.dto.CommentDto;
import com.kurepin.lab4.entities.Comment;
import com.kurepin.lab4.entities.Task;

import java.util.List;

public interface CommentService {
    Comment saveComment(Comment Comment);
    void deleteByCommentId(Long id);
    void deleteByComment(Comment Comment);
    void deleteAllComments();
    Comment updateComment(Comment Comment);
    CommentDto getCommentById(Long id);
    List<CommentDto> getAllComments();
    List<CommentDto> getAllCommentsByAuthor(String author);
    List<CommentDto> getAllCommentsByTaskId(Task taskId);
}
