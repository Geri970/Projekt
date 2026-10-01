package hu.csongor.demo.dto.response;

public class JelentkezoResponse {

    private Integer id;
    private String name;
    private String email;

    public JelentkezoResponse(
            Integer id,
            String name,
            String email) {

        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}