package com.softvision.usersmicroservice.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.softvision.usersmicroservice.entity.Like;
import java.util.Optional;
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);
    Long countByPostId(Long postId);
}
