package com.kurepin.lab4.controller;

import com.kurepin.lab4.dto.CommentDto;
import com.kurepin.lab4.dto.EmployeeDto;
import com.kurepin.lab4.dto.TaskDto;
import com.kurepin.lab4.entities.Comment;
import com.kurepin.lab4.entities.Employee;
import com.kurepin.lab4.entities.Task;
import com.kurepin.lab4.mapper.CommentMapper;
import com.kurepin.lab4.mapper.EmployeeMapper;
import com.kurepin.lab4.mapper.TaskMapper;
import com.kurepin.lab4.security.services.AccessControlSecurity;
import com.kurepin.lab4.services.CommentService;
import com.kurepin.lab4.services.EmployeeService;
import com.kurepin.lab4.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;
    private final AccessControlSecurity accessControlSecurity;

    @GetMapping("/get_all_comments")
    @PreAuthorize("hasAuthority('read')")
    public List<CommentDto> getAllComments() {
        if (accessControlSecurity.isAdmin(username())) {
            return commentService.getAllComments();
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @GetMapping("/get_comment_by_id/{id}")
    @PreAuthorize("hasAuthority('read')")
    public CommentDto getCommentById(@PathVariable("id") Long id) {
        if (accessControlSecurity.commentIsAllowed(username(), id)) {
            return commentService.getCommentById(id);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @GetMapping("/get_all_comments_by_author/{author}")
    @PreAuthorize("hasAuthority('read')")
    public List<CommentDto> getAllCommentsByAuthor(@PathVariable("author") String author) {
        if (accessControlSecurity.isAdmin(username())) {
            return commentService.getAllCommentsByAuthor(author);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @GetMapping("/get_all_comments_by_task_id/{id}")
    @PreAuthorize("hasAuthority('read')")
    public List<CommentDto> getAllCommentsByTaskId(@PathVariable("id") Long id) throws ParseException {
        if (accessControlSecurity.commentIsAccessedByTaskId(username(), id)) {
            TaskDto taskDto = taskService.getTaskById(id);
            EmployeeDto employeeDto = employeeService.getEmployeeById(taskDto.getEmployeeId());
            Employee employee = employeeMapper.toEntity(employeeDto);
            Task task = taskMapper.toEntity(taskDto, employee);
            return commentService.getAllCommentsByTaskId(task);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @PostMapping("/add_comment")
    @PreAuthorize("hasAuthority('update')")
    public Comment addComment(@RequestBody CommentDto CommentDto) throws ParseException {
        if (accessControlSecurity.commentIsAccessedByTaskId(username(), CommentDto.getTaskId())) {
            Comment comment = commentMapper.toEntity(CommentDto);
            return commentService.saveComment(comment);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @DeleteMapping("/delete_comment_by_id/{id}")
    @PreAuthorize("hasAuthority('update')")
    public void deleteCommentById(@PathVariable Long id) {
        if (accessControlSecurity.commentIsAllowed(username(), id)) {
            commentService.deleteByCommentId(id);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @DeleteMapping("/delete_comment")
    @PreAuthorize("hasAuthority('update')")
    public void deleteComment(@RequestBody CommentDto CommentDto) throws ParseException {
        if (accessControlSecurity.commentIsAllowed(username(), CommentDto.getId())) {
            Comment comment = commentMapper.toEntity(CommentDto);
            commentService.deleteByComment(comment);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @DeleteMapping("/delete_all_comments")
    @PreAuthorize("hasAuthority('update')")
    public void deleteAllComments() {
        if (accessControlSecurity.isAdmin(username())) {
            commentService.deleteAllComments();
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @PutMapping("/update_comment")
    @PreAuthorize("hasAuthority('update')")
    public Comment updateComment(@RequestBody CommentDto CommentDto) throws ParseException {
        if (accessControlSecurity.commentIsAllowed(username(), CommentDto.getId())) {
            Comment comment = commentMapper.toEntity(CommentDto);
            return commentService.updateComment(comment);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    private String username() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
