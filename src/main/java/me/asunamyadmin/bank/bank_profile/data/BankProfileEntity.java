package me.asunamyadmin.bank.bank_profile.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import me.asunamyadmin.bank.bank_profile.service.Status;
import me.asunamyadmin.bank.security.domain.Role;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "bank_users")
public class BankProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String username;
    @Enumerated(value = EnumType.STRING)
    Role role;
    @Enumerated(value = EnumType.STRING)
    Status status;
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public boolean isAdmin() {
        return this.role.equals(Role.SYSTEM) || this.role.equals(Role.HEAD);
    }
}
