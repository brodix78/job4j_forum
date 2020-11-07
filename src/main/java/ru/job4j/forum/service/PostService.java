package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private PostRepository posts;

    public PostService(PostRepository posts) {
        this.posts = posts;
    }

    public List<Post> getAll() {
        List<Post> rsl = new ArrayList<>();
        posts.findAll().forEach(rsl::add);
        return rsl;
    }

    public Post save(Post post) {
        return posts.save(post);
    }

    public Post update(Post post) {
        var rsl = posts.findById(post.getId());
        if (rsl.isPresent()) {
            return posts.save(post);
        }
        return null;
    }

    public Post findById(int id) {
        var rsl = posts.findById(id);
        if (rsl.isPresent()) {
            return rsl.get();
        }
        return null;
    }
}
