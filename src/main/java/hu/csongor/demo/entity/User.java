package hu.csongor.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nev;

    private String jelszo;
    private String email;
    private String role;
    private LocalDateTime regisztracioDatum;
    private boolean tiltva;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;

    }
    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDateTime getRegisztracioDatum() {
        return regisztracioDatum;
    }
    public boolean isTiltva() {
        return tiltva;
    }
    public void setTiltva(boolean tiltva) {
        this.tiltva = tiltva;
    }

    public void setRegisztracioDatum(LocalDateTime regisztracioDatum) {
        this.regisztracioDatum = regisztracioDatum;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}