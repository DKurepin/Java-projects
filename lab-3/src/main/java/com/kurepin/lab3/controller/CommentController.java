package com.kurepin.lab3.controller;

import com.kurepin.lab3.dto.CommentDto;
import com.kurepin.lab3.entities.Comment;
import com.kurepin.lab3.mapper.CommentMapper;
import com.kurepin.lab3.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping("/get_all_comments")
    public List<CommentDto> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/get_comment_by_id/{id}")
    public CommentDto getCommentById(@PathVariable("id") Long id) {
        return commentService.getCommentById(id);
    }

    @PostMapping("/add_comment")
    public Comment addComment(@RequestBody CommentDto commentDTO) throws ParseException {
        Comment comment = commentMapper.toEntity(commentDTO);
        return commentService.saveComment(comment);
    }

    @DeleteMapping("/delete_comment_by_id/{id}")
    public void deleteCommentById(@PathVariable Long id) {
        commentService.deleteByCommentId(id);
    }

    @DeleteMapping("/delete_comment")
    public void deleteComment(@RequestBody CommentDto commentDTO) throws ParseException {
        Comment comment = commentMapper.toEntity(commentDTO);
        commentService.deleteByComment(comment);
    }

    @DeleteMapping("/delete_all_comments")
    public void deleteAllComments() {
        commentService.deleteAllComments();
    }

    @PutMapping("/update_comment")
    public Comment updateComment(@RequestBody CommentDto commentDTO) throws ParseException {
        Comment comment = commentMapper.toEntity(commentDTO);
        return commentService.updateComment(comment);
    }
}
