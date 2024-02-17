package dev.rezapu.model;

import dev.rezapu.hooks.HookType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@Data
@Accessors(chain = true)
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Hook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    @NonNull
    private String message_id;

    @Column(nullable = false)
    @NonNull
    private String channel_id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private HookType type;
}
