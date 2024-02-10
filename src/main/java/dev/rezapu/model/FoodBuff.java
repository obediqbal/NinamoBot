package dev.rezapu.model;

import dev.rezapu.enums.FoodBuffType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodBuff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @NonNull
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private FoodBuffType type;

    @Column(nullable = false)
    private int level;
}
