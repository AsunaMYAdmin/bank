package me.asunamyadmin.bank.security.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.asunamyadmin.bank.bank_profile.data.BankProfileEntity;
import me.asunamyadmin.bank.bank_profile.data.BankProfileRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2ProfileService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final BankProfileRepository repository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        BankProfileEntity entity = getOnCreateProfile(oAuth2User.getAttribute("sub"));
        return new DefaultOAuth2User(
                Set.of(entity.getRole().getSimpleGrantedAuthority()),
                oAuth2User.getAttributes(),
                "sub"
        );
    }

    public BankProfileEntity getOnCreateProfile(String username) {
        return repository.findByUsername(username).orElseGet(() -> {
           BankProfileEntity entity = new BankProfileEntity();
           entity.setUsername(username);
           entity.setRole(Role.USER);
           return entity;
        });
    }
}
