package com.kurepin.service;


import com.kurepin.entities.Comment;
import com.kurepin.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getAllByAuthor(String author);
    List<Comment> getAllCommentsByTaskId(Task taskId);
}
