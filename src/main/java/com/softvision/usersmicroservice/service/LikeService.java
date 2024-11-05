package com.softvision.usersmicroservice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softvision.usersmicroservice.entity.Like;
import com.softvision.usersmicroservice.entity.Post;
import com.softvision.usersmicroservice.entity.User;
import com.softvision.usersmicroservice.repo.LikeRepository;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    // Method to like a post
    public void likePost(User user, Long postId) {
        // Check if the user has already liked the post
        if (likeRepository.findByUser_UseridAndPost_Id(user.getUserid(), postId).isEmpty()) {
            Like like = new Like();
            like.setUser(user);
            like.setPost(new Post()); 
            likeRepository.save(like);
        }
    }

    // Method to unlike a post
    public void unlikePost(User user, Long postId) {
        likeRepository.findByUser_UseridAndPost_Id(user.getUserid(), postId).ifPresent(likeRepository::delete);
    }
}