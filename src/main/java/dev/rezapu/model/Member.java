package dev.rezapu.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@Data
@Accessors(chain = true)
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    @NonNull
    private String discord_id;

    @Column(nullable = false, unique = true)
    @NonNull
    private String ign;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int point;

    public Member addPoint(int point){
        this.point += point;
        return this;
    }
    public Member removePoint(int point) throws Exception{
        if(this.point-point<0){
            throw new Exception();
        }
        this.point -= point;
        return this;
    }
}
