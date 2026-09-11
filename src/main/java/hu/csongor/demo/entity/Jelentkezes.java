package hu.csongor.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "jelentkezesek")
public class Jelentkezes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long szakkorId;

    private LocalDateTime jelentkezesDatum;

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSzakkorId() {
        return szakkorId;
    }

    public void setSzakkorId(Long szakkorId) {
        this.szakkorId = szakkorId;
    }

    public LocalDateTime getJelentkezesDatum() {
        return jelentkezesDatum;
    }

    public void setJelentkezesDatum(LocalDateTime jelentkezesDatum) {
        this.jelentkezesDatum = jelentkezesDatum;
    }
}