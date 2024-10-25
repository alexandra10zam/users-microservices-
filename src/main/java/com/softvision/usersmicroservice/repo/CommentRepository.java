package com.softvision.usersmicroservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softvision.usersmicroservice.entity.Comment;
import java.util.*;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}
