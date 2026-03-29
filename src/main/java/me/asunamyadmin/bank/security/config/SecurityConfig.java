package me.asunamyadmin.bank.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.asunamyadmin.bank.bank_profile.data.BankProfileEntity;
import me.asunamyadmin.bank.security.domain.OAuth2ProfileService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Set;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final OAuth2ProfileService oAuth2ProfileService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/login", "/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .oidcUserService(this::oidcUserMapper)
                        )
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                )
                .build();
    }

    private OidcUser oidcUserMapper(OidcUserRequest oidcUserRequest) {
        try {
            OidcUser oidcUser = new OidcUserService().loadUser(oidcUserRequest);
            String sub = oidcUser.getAttribute("sub");
            if (sub == null) {
                log.error("OIDC 'sub' attribute is null for user: {}", oidcUser.getName());
                throw new RuntimeException("OIDC 'sub' is null");
            }
            BankProfileEntity profile = oAuth2ProfileService.getOnCreateProfile(sub);
            if (profile == null || profile.getRole() == null) {
                log.error("Profile or role is null for OIDC user: {}", oidcUser.getName());
                throw new RuntimeException("Profile/Role is null");
            }
            return new DefaultOidcUser(
                    Set.of(profile.getRole().getSimpleGrantedAuthority()),
                    oidcUser.getIdToken(),
                    oidcUser.getUserInfo()
            );
        } catch (Exception e) {
            log.error("Failed to map OIDC user", e);
            throw new RuntimeException("OIDC user mapping failed", e);
        }
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
