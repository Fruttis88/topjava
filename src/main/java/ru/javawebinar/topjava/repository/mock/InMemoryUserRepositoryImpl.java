package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UsersUtil;
import sun.rmi.runtime.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger count = new AtomicInteger(0);

    {
        UsersUtil.USERS.sort(Comparator.comparing(user -> user.getName()));
        UsersUtil.USERS.forEach(this::save);
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew()){
            user.setId(count.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        try{
        repository.remove(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        return (List<User>) repository.values();
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        User currentUser = null;
        for (Map.Entry<Integer, User> map : repository.entrySet()){
            if (map.getValue().getEmail().equals(email)){
                currentUser = map.getValue();
            }
        }
//        сделать через стрим
        return currentUser;
    }

}
