package me.asunamyadmin.bank.user.domain;

import me.asunamyadmin.bank.user.data.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public User toUser(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getCreatedAt()
        );
    }

    public UserEntity toUserEntity(User user) {
        return new UserEntity(
                user.email(),
                user.password()
        );
    }
}
