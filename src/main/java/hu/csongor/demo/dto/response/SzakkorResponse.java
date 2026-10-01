package hu.csongor.demo.dto.response;

public class SzakkorResponse {

    private Integer id;
    private String name;
    private String leiras;
    private String idopont;
    private String helyszin;
    private Integer maxLetszam;
    private Long jelentkezokSzama;
    private Double atlagErtekeles;

    public SzakkorResponse(
            Integer id,
            String name,
            String leiras,
            String idopont,
            String helyszin,
            Integer maxLetszam,
            Long jelentkezokSzama,
            Double atlagErtekeles) {

        this.id = id;
        this.name = name;
        this.leiras = leiras;
        this.idopont = idopont;
        this.helyszin = helyszin;
        this.maxLetszam = maxLetszam;
        this.jelentkezokSzama = jelentkezokSzama;
        this.atlagErtekeles = atlagErtekeles;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLeiras() {
        return leiras;
    }

    public String getIdopont() {
        return idopont;
    }

    public String getHelyszin() {
        return helyszin;
    }

    public Integer getMaxLetszam() {
        return maxLetszam;
    }

    public Long getJelentkezokSzama() {
        return jelentkezokSzama;
    }
    public Double getAtlagErtekeles() {
        return atlagErtekeles;
    }

}