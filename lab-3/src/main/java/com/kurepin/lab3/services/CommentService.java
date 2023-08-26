package com.kurepin.lab3.services;

import com.kurepin.lab3.dto.CommentDto;
import com.kurepin.lab3.entities.Comment;

import java.util.List;

public interface CommentService {
    Comment saveComment(Comment Comment);
    void deleteByCommentId(Long id);
    void deleteByComment(Comment Comment);
    void deleteAllComments();
    Comment updateComment(Comment Comment);
    CommentDto getCommentById(Long id);
    List<CommentDto> getAllComments();
}
