package ru.job4j.forum.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;

@Controller
public class PostControl {

    private final PostService posts;

    public PostControl(PostService posts) {
        this.posts = posts;
    }

    @GetMapping("/create")
    public String create() {
        return "post/create";
    }

    @PostMapping("/post/save")
    public String save(@ModelAttribute Post post) {
        posts.save(post);
        return "redirect:/";
    }

    @GetMapping("/post")
    public String view(@RequestParam String id, Model model) {
        int i = Integer.parseInt(id);
        Post post = posts.findById(i);
        if (post != null) {
            model.addAttribute("post", post);
            return "post/view";
        }
        return "redirect:/";
    }

    @GetMapping("/post/edit")
    public String edit() {
        return "post/edit";
    }

    @PostMapping("/post/update")
    public String update(@ModelAttribute Post post) {
        posts.update(post);
        return "redirect:/";
    }
}
