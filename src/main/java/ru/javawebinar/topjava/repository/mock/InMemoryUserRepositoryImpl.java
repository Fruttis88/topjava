package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UsersUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger count = new AtomicInteger(0);

    {
        UsersUtil.USERS.forEach(this::save);
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(count.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public boolean delete(int id) {
            return repository.remove(id) != null;
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>(repository.values());
        Collections.sort(list, (user1, user2) -> user1.getName().compareTo(user2.getName()));
        return Optional.of(list).orElseGet(Collections::emptyList);
    }

    @Override
    public User getByEmail(String email) {
        return repository.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny().orElse(null);
    }

}
