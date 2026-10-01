package hu.csongor.demo.dto.response;

import java.time.LocalDateTime;

public class RatingResponse {

    private Integer id;
    private Integer star;
    private String opinion;
    private String userName;
    private String szakkorName;
    private LocalDateTime createdAt;

    public RatingResponse(
            Integer id,
            Integer star,
            String opinion,
            String userName,
            String szakkorName,
            LocalDateTime createdAt) {

        this.id = id;
        this.star = star;
        this.opinion = opinion;
        this.userName = userName;
        this.szakkorName = szakkorName;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public Integer getStar() {
        return star;
    }

    public String getOpinion() {
        return opinion;
    }

    public String getUserName() {
        return userName;
    }

    public String getSzakkorName() {
        return szakkorName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}