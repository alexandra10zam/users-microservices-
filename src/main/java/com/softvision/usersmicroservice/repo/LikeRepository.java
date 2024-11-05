package com.softvision.usersmicroservice.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.softvision.usersmicroservice.entity.Like;
import java.util.Optional;

import org.springframework.stereotype.Repository;


@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUser_UseridAndPost_Id(Long userid, Long postId);
}
