package dev.rezapu.model;

import dev.rezapu.enums.FoodBuffType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

@Entity
@Data
@Accessors(chain = true)
@NoArgsConstructor
@RequiredArgsConstructor
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

    public FoodBuff(@NotNull String address, @NotNull FoodBuffType type, int level) {
        this.address = address;
        this.type = type;
        this.level = level;
    }
}
