package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PostService {

    private AtomicInteger id = new AtomicInteger(1);

    private final Map<Integer, Post> posts = new HashMap<>();

    public PostService() {
        posts.put(1, Post.of("Продаю всякое"));
    }

    public List<Post> getAll() {
        return new ArrayList<>(posts.values());
    }

    public Post save(Post post) {
        post.setId(id.incrementAndGet());
        posts.put(post.getId(), post);
        return post;
    }

    public Post update(Post post) {
        posts.put(post.getId(), post);
        return post;
    }

    public Post findById(int id) {
        return posts.get(id);
    }
}
