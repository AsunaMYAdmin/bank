package me.asunamyadmin.bank.user.domain;

import me.asunamyadmin.bank.user.data.UserEntity;
import me.asunamyadmin.bank.user.data.UserRepository;
import me.asunamyadmin.bank.user.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userEntityMapper = new UserEntityMapper();
    }

    public List<User> findAll() {
        List<UserEntity> usersFromRepository = new ArrayList<>(userRepository.findAll());
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : usersFromRepository) {
            User user = userEntityMapper.toUser(userEntity);
            users.add(user);
        }
        return users;
    }

    public User findById(int id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        userIsNull(userEntity);
        return userEntityMapper.toUser(userEntity);
    }

    public User updateUser (User user, int id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        userIsNull(userEntity);
        userEntity.setEmail(user.email());
        userEntity.setPassword(user.password());
        return userEntityMapper.toUser(userRepository.save(userEntity));
    }

    public User saveUser(User user) {
        UserEntity newEntity = userRepository.save(userEntityMapper.toUserEntity(user));
        return userEntityMapper.toUser(newEntity);
    }

    public void deleteById(int id) {
        userIsNull(userEntityMapper.toUserEntity(findById(id)));
        userRepository.deleteById(id);
    }

    private void userIsNull(UserEntity user) {
        if (user == null) {
            logger.error("User is not found!");
            throw new UserNotFoundException("User with this id is not found");
        }
    }
}
