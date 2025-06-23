package com.group2.collapp.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group2.collapp.model.Comment;
import com.group2.collapp.repository.CommentRepository;
import com.group2.collapp.repository.TaskRepository;
import com.group2.collapp.repository.UserRepository;

@Service
public class CommentService {
    @Autowired private CommentRepository commentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private TaskRepository taskRepository;

    public Comment addComment(Long taskId, Long userId, String content) {
        Comment comment = new Comment();
        comment.setTask(taskRepository.findById(taskId).orElseThrow());
        comment.setAuthor(userRepository.findById(userId).orElseThrow());
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsForTask(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }
}