package com.g1dra.foodmanager.models;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "foods")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank(message = "Name is mandatory")
    public String name;

    @NotBlank(message = "Price is mandatory")
    public Long price;

    @CreatedBy
    @ManyToOne
    private User user;

    @Column(name = "created_at")
    @CreatedDate
    public LocalDateTime createdAt;
}
