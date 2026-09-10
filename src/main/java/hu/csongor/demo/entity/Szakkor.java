package hu.csongor.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "szakkorok")

public class Szakkor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nev;
    private String leiras;
    private String idopont;
    private String helyszin;
    private Integer maxLetszam;


    private Long getId(){
        return id;
    }
    public String getNev(){
        return nev;
    }
    public void  setNev(String nev){
        this.nev = nev;
    }
    public String getLeiras(){
        return leiras;
    }
    public void setLeiras(String leiras){
        this.leiras = leiras;
    }
    public String getIdopont(){
        return idopont;
    }
    public void setIdopont(String idopont){
        this.idopont = idopont;
    }
    public String getHelyszin(){
        return helyszin;
    }
    public void setHelyszin(String helyszin){
        this.helyszin = helyszin;
    }
    public Integer getMaxLetszam(){
        return maxLetszam;
    }
    public void setMaxLetszam(Integer maxLetszam){
        this.maxLetszam = maxLetszam;
    }
}
