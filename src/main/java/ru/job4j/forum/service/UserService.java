package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserService {
    private AtomicInteger id = new AtomicInteger(0);

    private final Map<Integer, User> users = new HashMap<>();

    public User save(User user) {
        user.setId(id.incrementAndGet());
        users.put(user.getId(), user);
        return user;
    }

    public User findById(int id) {
        return users.get(id).safeCopy();
    }

    public User check(User user) {
        User rsl = null;
        for (User instance : users.values()) {
            if (instance.getUsername().equals(user.getUsername()) && instance.getPassword().equals(user.getPassword())) {
                rsl = instance.safeCopy();
            }
        }
        return rsl;
    }
}