package hu.csongor.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "iskolak")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String varos;

    @Column(name = "description")
    private String leiras;

    @Column(name = "status")
    private String statusz;
    @Column(name = "created_at",  updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVaros() {
        return varos;
    }

    public void setVaros(String varos) {
        this.varos = varos;
    }

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public String getStatusz() {
        return statusz;
    }

    public void setStatusz(String statusz) {
        this.statusz = statusz;
    }
    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
    public LocalDateTime getDeletedAt() {return deletedAt;}
    public void setDeletedAt(LocalDateTime deletedAt) {this.deletedAt = deletedAt;}
    public Boolean getIsDeleted() {return isDeleted;}
    public void setIsDeleted(Boolean isDeleted) {this.isDeleted = isDeleted;}
}