package com.softvision.usersmicroservice.service;

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
