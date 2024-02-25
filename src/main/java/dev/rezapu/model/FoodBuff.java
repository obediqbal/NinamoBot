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

    @Column(nullable = false, length = 7)
    @NonNull
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private FoodBuffType type;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private FoodBuffType type_order;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private String stats;

    public FoodBuff(@NotNull String address, @NotNull FoodBuffType type, int level, String stats) {
        this.address = address;
        this.type = type;
        this.type_order = type;
        this.level = level;
        this.stats = stats;
    }
}
