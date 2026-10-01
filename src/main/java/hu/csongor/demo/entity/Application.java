package hu.csongor.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
@Entity
@Table(name = "jelentkezesek")
public class Jelentkezes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "szakkor_id")
    private Integer szakkorId;

    @Column(name = "created_at")
    private LocalDateTime jelentkezesDatum;
    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSzakkorId() {
        return szakkorId;
    }

    public void setSzakkorId(Integer szakkorId) {
        this.szakkorId = szakkorId;
    }

    public LocalDateTime getJelentkezesDatum() {
        return jelentkezesDatum;
    }

    public void setJelentkezesDatum(
            LocalDateTime jelentkezesDatum) {

        this.jelentkezesDatum = jelentkezesDatum;
    }
}