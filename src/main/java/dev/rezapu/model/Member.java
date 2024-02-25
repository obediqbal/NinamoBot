package dev.rezapu.model;

import dev.rezapu.exceptions.BadUsageException;
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
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    @NonNull
    private String discord_id;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT '-'")
    private String ign = "-";

    @Column(columnDefinition = "INT DEFAULT 0")
    private int point;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int raid;


    public Member addPoint(int point){
        this.point += point;
        return this;
    }
    public Member removePoint(int point) throws BadUsageException {
        if(this.point-point<0){
            throw new BadUsageException("Poin tidak cukup");
        }
        this.point -= point;
        return this;
    }
    public Member incRaid(){
        this.raid++;
        return this;
    }
    public Member decRaid() throws BadUsageException{
        if(this.raid-1<0){
            throw new BadUsageException("Raid sudah nol");
        }
        this.raid--;
        return this;
    }
}
