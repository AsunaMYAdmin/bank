package me.asunamyadmin.bank.profile.domain;

import me.asunamyadmin.bank.profile.data.ProfileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ProfileMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Profile mapProfileEntityToProfile(ProfileEntity profileEntity) {
        return new Profile(
                profileEntity.getUsername(),
                profileEntity.getPassword(),
                profileEntity.getAccountNumber(),
                profileEntity.getRole(),
                profileEntity.getCreatedAt()
        );
    }

    public ProfileEntity mapProfileToProfileEntity(Profile profile) {
        ProfileEntity entity = new ProfileEntity();
        entity.setUsername(profile.username());
        String encodedPassword = passwordEncoder.encode(profile.password());
        entity.setPassword(encodedPassword);
        return entity;
    }
}
