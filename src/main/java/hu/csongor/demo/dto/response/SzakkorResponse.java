package hu.csongor.demo.dto.response;

public class SzakkorResponse {

    private Integer id;
    private String name;
    private String leiras;
    private String idopont;
    private String helyszin;
    private Integer maxLetszam;
    private Long jelentkezokSzama;

    public SzakkorResponse(
            Integer id,
            String name,
            String leiras,
            String idopont,
            String helyszin,
            Integer maxLetszam,
            Long jelentkezokSzama) {

        this.id = id;
        this.name = name;
        this.leiras = leiras;
        this.idopont = idopont;
        this.helyszin = helyszin;
        this.maxLetszam = maxLetszam;
        this.jelentkezokSzama = jelentkezokSzama;
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
}