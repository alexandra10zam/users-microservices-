package com.softvision.usersmicroservice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import com.softvision.usersmicroservice.entity.Post;
import com.softvision.usersmicroservice.entity.User;
import com.softvision.usersmicroservice.repo.PostRepository;
@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post createPost(User user, String content) {
        Post post = new Post();
        post.setAuthor(user);
        post.setContent(content);
        post.setTimestamp(LocalDateTime.now());
        return postRepository.save(post);
    }

    public List<Post> getFeed() {
        return postRepository.findAllByOrderByTimestampDesc();
    }
}
