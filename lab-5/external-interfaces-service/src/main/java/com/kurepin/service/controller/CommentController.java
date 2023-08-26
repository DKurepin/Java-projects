package com.kurepin.service.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurepin.entities.dto.CommentDto;
import com.kurepin.entities.dto.TaskDto;

import com.kurepin.service.security.services.AccessControlSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@EnableRabbit
public class CommentController {
    private final AccessControlSecurity accessControlSecurity;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @GetMapping("/get_all_comments")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<List<CommentDto>> getAllComments() {
        if (accessControlSecurity.isAdmin(username())) {
            rabbitTemplate.convertAndSend("comment-exchange", "comment.get.all", "");
            Message resultMessage = rabbitTemplate.receive("comment-get-result-queue", 5000);
            if (resultMessage == null) {
                throw new RuntimeException("Failed to receive result message");
            }
            try {
                List<CommentDto> resultDtoList = objectMapper.readValue(resultMessage.getBody(),
                        new TypeReference<List<CommentDto>>() {
                        });
                return ResponseEntity.ok(resultDtoList);
            } catch (IOException e) {
                throw new RuntimeException("Failed to deserialize result message", e);
            }
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @GetMapping("/get_comment_by_id/{id}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") Long id) {
        if (accessControlSecurity.commentIsAllowed(username(), id)) {
            rabbitTemplate.convertAndSend("comment-exchange", "comment.get.byId", id);
            Message resultMessage = rabbitTemplate.receive("comment-get-result-queue", 5000);
            if (resultMessage == null) {
                throw new RuntimeException("Failed to receive result message");
            }
            try {
                CommentDto commentDto = objectMapper.readValue(resultMessage.getBody(),
                        new TypeReference<CommentDto>() {
                        });
                return ResponseEntity.ok(commentDto);
            } catch (IOException e) {
                throw new RuntimeException("Failed to deserialize result message", e);
            }
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @GetMapping("/get_all_comments_by_author/{author}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<List<CommentDto>> getAllCommentsByAuthor(@PathVariable("author") String author) {
        if (accessControlSecurity.isAdmin(username())) {
            rabbitTemplate.convertAndSend("comment-exchange", "comment.get.allByAuthor", author);
            Message resultMessage = rabbitTemplate.receive("comment-get-result-queue", 5000);
            if (resultMessage == null) {
                throw new RuntimeException("Failed to receive result message");
            }
            try {
                List<CommentDto> resultDtoList = objectMapper.readValue(resultMessage.getBody(),
                        new TypeReference<List<CommentDto>>() {
                        });
                return ResponseEntity.ok(resultDtoList);
            } catch (IOException e) {
                throw new RuntimeException("Failed to deserialize result message", e);
            }
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @GetMapping("/get_all_comments_by_task_id/{id}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<List<CommentDto>> getAllCommentsByTaskId(@PathVariable("id") Long id) throws ParseException {
        if (accessControlSecurity.commentIsAccessedByTaskId(username(), id)) {
            TaskDto taskDto = new TaskDto();
            taskDto.setId(id);
            rabbitTemplate.convertAndSend("comment-exchange", "comment.get.allByTaskId", taskDto);
            Message resultMessage = rabbitTemplate.receive("comment-get-result-queue", 5000);
            if (resultMessage == null) {
                throw new RuntimeException("Failed to receive result message");
            }
            try {
                List<CommentDto> resultDtoList = objectMapper.readValue(resultMessage.getBody(),
                        new TypeReference<List<CommentDto>>() {
                        });
                return ResponseEntity.ok(resultDtoList);
            } catch (IOException e) {
                throw new RuntimeException("Failed to deserialize result message", e);
            }
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @PostMapping("/add_comment")
    @PreAuthorize("hasAuthority('update')")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto) throws ParseException {
        if (accessControlSecurity.commentIsAccessedByTaskId(username(), commentDto.getTaskId())) {
            rabbitTemplate.convertAndSend("comment-exchange", "comment.create", commentDto);
            Message resultMessage = rabbitTemplate.receive("comment-get-result-queue", 5000);
            if (resultMessage == null) {
                throw new RuntimeException("Failed to receive result message");
            }
            try {
                CommentDto commentDtoResult = objectMapper.readValue(resultMessage.getBody(),
                        new TypeReference<CommentDto>() {
                        });
                return ResponseEntity.ok(commentDtoResult);
            } catch (IOException e) {
                throw new RuntimeException("Failed to deserialize result message", e);
            }
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @DeleteMapping("/delete_comment_by_id/{id}")
    @PreAuthorize("hasAuthority('update')")
    public void deleteCommentById(@PathVariable Long id) {
        if (accessControlSecurity.commentIsAllowed(username(), id)) {
            rabbitTemplate.convertAndSend("comment-exchange", "comment.delete.byId", id);
        }
        throw new AccessDeniedException("403 Forbidden");
    }


    @DeleteMapping("/delete_all_comments")
    @PreAuthorize("hasAuthority('update')")
    public void deleteAllComments() {
        if (accessControlSecurity.isAdmin(username())) {
            rabbitTemplate.convertAndSend("comment-exchange", "comment.delete.all", "");
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @PutMapping("/update_comment")
    @PreAuthorize("hasAuthority('update')")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto) throws ParseException {
        if (accessControlSecurity.commentIsAllowed(username(), commentDto.getId())) {
            rabbitTemplate.convertAndSend("comment-exchange", "comment.update", commentDto);
            Message resultMessage = rabbitTemplate.receive("comment-get-result-queue", 5000);
            if (resultMessage == null) {
                throw new RuntimeException("Failed to receive result message");
            }
            try {
                CommentDto commentDtoResult = objectMapper.readValue(resultMessage.getBody(),
                        new TypeReference<CommentDto>() {
                        });
                return ResponseEntity.ok(commentDtoResult);
            } catch (IOException e) {
                throw new RuntimeException("Failed to deserialize result message", e);
            }
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    private String username() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
