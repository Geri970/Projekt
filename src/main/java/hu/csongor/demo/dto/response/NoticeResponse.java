package hu.csongor.demo.dto.response;

import java.time.LocalDateTime;

public class HirdetmenyResponse {

    private Integer id;
    private String title;
    private String content;
    private Integer teacherId;
    private Integer schoolId;
    private LocalDateTime createdAt;

    public HirdetmenyResponse(
            Integer id,
            String title,
            String content,
            Integer teacherId,
            Integer schoolId,
            LocalDateTime createdAt) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.teacherId = teacherId;
        this.schoolId = schoolId;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}