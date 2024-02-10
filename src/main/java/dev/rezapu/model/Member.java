package dev.rezapu.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
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

    public void addPoint(int point){
        this.point += point;
    }
    public void removePoint(int point) throws Exception{
        if(this.point-point<0){
            throw new Exception("Not enough point");
        }
        this.point -= point;
    }
}
