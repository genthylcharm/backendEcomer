package com.dvl.Ecommer.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // tu tang len 1 don vi de khong giong nhau
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
}
