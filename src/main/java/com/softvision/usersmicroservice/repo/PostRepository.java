package com.softvision.usersmicroservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softvision.usersmicroservice.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByTimestampDesc();
}
