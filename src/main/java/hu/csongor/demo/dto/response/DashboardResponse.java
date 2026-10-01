package hu.csongor.demo.dto.response;

public class DashboardResponse {

    private Long usersSzama;
    private Long iskolakSzama;
    private Long szakkorokSzama;
    private Long jelentkezesekSzama;
    private Long hirdetmenyekSzama;
    private Long ertekelesekSzama;

    public DashboardResponse(
            Long usersSzama,
            Long iskolakSzama,
            Long szakkorokSzama,
            Long jelentkezesekSzama,
            Long hirdetmenyekSzama,
            Long ertekelesekSzama) {

        this.usersSzama = usersSzama;
        this.iskolakSzama = iskolakSzama;
        this.szakkorokSzama = szakkorokSzama;
        this.jelentkezesekSzama = jelentkezesekSzama;
        this.hirdetmenyekSzama = hirdetmenyekSzama;
        this.ertekelesekSzama = ertekelesekSzama;
    }

    public Long getUsersSzama() {
        return usersSzama;
    }

    public Long getIskolakSzama() {
        return iskolakSzama;
    }

    public Long getSzakkorokSzama() {
        return szakkorokSzama;
    }

    public Long getJelentkezesekSzama() {
        return jelentkezesekSzama;
    }

    public Long getHirdetmenyekSzama() {
        return hirdetmenyekSzama;
    }

    public Long getErtekelesekSzama() {
        return ertekelesekSzama;
    }
}
