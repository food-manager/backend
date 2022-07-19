package com.g1dra.foodmanager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "users")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    @Column(nullable = false)
    private String email;

    @JsonIgnore
    private String password;

    @Enumerated
    private UserRole role;

    @Column(name = "created_at")
    @CreatedDate
    public LocalDateTime createdAt;

    @Builder(builderMethodName = "userAdminBuilder")
    public static User createUserAdmin(
            String name,
            String email,
            String password
    ) {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(UserRole.ADMIN)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Builder(builderMethodName = "userRegularBuilder")
    public static User createUserRegular(
            String name,
            String email,
            String password
    ) {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(UserRole.REGULAR)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
