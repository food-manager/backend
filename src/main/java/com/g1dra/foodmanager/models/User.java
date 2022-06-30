package com.g1dra.foodmanager.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
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

    @Enumerated
    private UserRole role;

    @Column(name = "created_at")
    @CreatedDate
    public LocalDateTime createdAt;
}
