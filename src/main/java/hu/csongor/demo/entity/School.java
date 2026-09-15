package hu.csongor.demo.entity;

import jakarta.persistence.*;

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
}