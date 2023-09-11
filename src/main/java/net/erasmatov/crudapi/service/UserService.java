package net.erasmatov.crudapi.service;

import net.erasmatov.crudapi.model.User;
import net.erasmatov.crudapi.repository.UserRepository;
import net.erasmatov.crudapi.repository.hibernate.HibernateUserRepositoryImpl;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        userRepository = new HibernateUserRepositoryImpl();
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public User getUserById(Integer id) {
        return userRepository.getById(id);
    }

    public User updateUser(User user) {
        return userRepository.update(user);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

}
