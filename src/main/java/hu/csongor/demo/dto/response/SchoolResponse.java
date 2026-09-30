package hu.csongor.demo.dto.response;

public class SchoolResponse {

    private Integer id;
    private String name;
    private String varos;
    private String statusz;
    private Long szakkorokSzama;

    public SchoolResponse(
            Integer id,
            String name,
            String varos,
            String statusz,
            Long szakkorokSzama) {

        this.id = id;
        this.name = name;
        this.varos = varos;
        this.statusz = statusz;
        this.szakkorokSzama = szakkorokSzama;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVaros() {
        return varos;
    }

    public String getStatusz() {
        return statusz;
    }

    public Long getSzakkorokSzama() {
        return szakkorokSzama;
    }
}
