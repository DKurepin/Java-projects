package com.kurepin.lab4.repositories;

import com.kurepin.lab4.entities.Comment;
import com.kurepin.lab4.entities.Employee;
import com.kurepin.lab4.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getAllByAuthor(String author);
    List<Comment> getAllCommentsByTaskId(Task taskId);
}
