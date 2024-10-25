package com.softvision.usersmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.softvision.usersmicroservice.entity.Comment;
import com.softvision.usersmicroservice.entity.Post;
import com.softvision.usersmicroservice.entity.User;
import com.softvision.usersmicroservice.repo.CommentRepository;

import java.time.LocalDateTime;
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment addComment(User user, Long postId, String content) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(new Post());  
        comment.setContent(content);
        comment.setTimestamp(LocalDateTime.now());
        return commentRepository.save(comment);
    }
}
