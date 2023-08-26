package com.kurepin.service;

import com.kurepin.entities.Comment;
import com.kurepin.entities.Task;
import com.kurepin.entities.dto.CommentDto;
import com.kurepin.entities.dto.TaskDto;
import com.kurepin.entities.mappers.CommentMapper;
import com.kurepin.entities.mappers.TaskMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@ComponentScan(basePackages = "com.kurepin.entities.mappers")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TaskMapper taskMapper;

    @Override
    public CommentDto saveComment(CommentDto CommentDto) throws ParseException {
        Comment Comment = commentMapper.toEntity(CommentDto);
        return commentMapper.toDto(commentRepository.save(Comment));
    }

    @Override
    public void deleteByCommentId(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void deleteAllComments() {
        commentRepository.deleteAll();
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto) throws ParseException {
        Comment Comment = commentMapper.toEntity(commentDto);
        return commentMapper.toDto(commentRepository.save(Comment));
    }

    @Override
    public CommentDto getCommentById(Long id) {
        Optional<Comment> task = commentRepository.findById(id);
        return commentMapper.toDto(task.get());
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
    public List<CommentDto> getAllCommentsByTaskId(TaskDto taskDto) throws ParseException {
        Task task = taskMapper.toEntity(taskDto);
        List<Comment> comments = commentRepository.getAllCommentsByTaskId(task);
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
