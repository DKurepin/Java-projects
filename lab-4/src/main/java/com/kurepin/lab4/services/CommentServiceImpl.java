package com.kurepin.lab4.services;

import com.kurepin.lab4.dto.CommentDto;
import com.kurepin.lab4.entities.Comment;
import com.kurepin.lab4.entities.Task;
import com.kurepin.lab4.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Autowired
    private final CommentRepository commentRepository;

    @Override
    public Comment saveComment(Comment Comment) {
        return commentRepository.save(Comment);
    }

    @Override
    public void deleteByCommentId(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void deleteByComment(Comment Comment) {
        commentRepository.delete(Comment);
    }

    @Override
    public void deleteAllComments() {
        commentRepository.deleteAll();
    }

    @Override
    public Comment updateComment(Comment Comment) {
        return commentRepository.save(Comment);
    }

    @Override
    public CommentDto getCommentById(Long id) {
        Comment comment = commentRepository.getReferenceById(id);
        CommentDto commentDTO = CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getAuthor())
                .creationDate(comment.getCreationDate())
                .taskId(comment.getTaskId().getId())
                .build();
        return commentDTO;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> commentDtos = comments.stream().map(comment -> CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getAuthor())
                .creationDate(comment.getCreationDate())
                .taskId(comment.getTaskId().getId())
                .build()).toList();
        return commentDtos;
    }

    @Override
    public List<CommentDto> getAllCommentsByAuthor(String author) {
        List<Comment> comments = commentRepository.getAllByAuthor(author);
        List<CommentDto> commentDtos = comments.stream().map(comment -> CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getAuthor())
                .creationDate(comment.getCreationDate())
                .taskId(comment.getTaskId().getId())
                .build()).toList();
        return commentDtos;
    }

    @Override
    public List<CommentDto> getAllCommentsByTaskId(Task taskId) {
        List<Comment> comments = commentRepository.getAllCommentsByTaskId(taskId);
        List<CommentDto> commentDtos = comments.stream().map(comment -> CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getAuthor())
                .creationDate(comment.getCreationDate())
                .taskId(comment.getTaskId().getId())
                .build()).toList();
        return commentDtos;
    }
}
