package hu.csongor.demo.service;

import hu.csongor.demo.entity.Notice;
import hu.csongor.demo.entity.School;
import hu.csongor.demo.entity.User;

import hu.csongor.demo.repository.NoticeRepository;
import hu.csongor.demo.repository.SchoolRepository;
import hu.csongor.demo.repository.UserRepository;
import hu.csongor.demo.dto.response.NoticeResponse;

import java.util.List;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HirdetmenyService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;

    public HirdetmenyService(
            NoticeRepository noticeRepository,
            UserRepository userRepository,
            SchoolRepository schoolRepository) {

        this.noticeRepository = noticeRepository;
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
    }
    public Object createHirdetmeny(
            Notice notice) {


        if(notice.getTitle() == null ||
                notice.getTitle().isBlank()) {

            return "A cím megadása kötelező!";
        }

        if(notice.getContent() == null ||
                notice.getContent().isBlank()) {

            return "A tartalom megadása kötelező!";
        }

        User teacher = userRepository
                .findById(
                        notice.getTeacherId()
                )
                .orElse(null);

        if(teacher == null) {

            return "Nincs ilyen tanár!";
        }

        if(!teacher.getRole()
                .equals("TANAR")) {

            return "Csak tanár hozhat létre hirdetményt!";
        }

        School school = schoolRepository
                .findById(
                        notice.getSchoolId()
                )
                .orElse(null);

        if(school == null) {

            return "Nincs ilyen iskola!";
        }

        if(Boolean.TRUE.equals(
                school.getIsDeleted())) {

            return "Az iskola törölve van!";
        }

        notice.setCreatedAt(
                LocalDateTime.now().withNano(0)
        );

        notice.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );

        notice.setIsDeleted(false);

        return noticeRepository.save(
                notice
        );
    }
    public List<NoticeResponse>
    getAllHirdetmenyek() {

        List<Notice> hirdetmenyek =
                noticeRepository
                        .findByIsDeletedFalse();

        return hirdetmenyek.stream()
                .map(notice ->
                        new NoticeResponse(
                                notice.getId(),
                                notice.getTitle(),
                                notice.getContent(),
                                notice.getTeacherId(),
                                notice.getSchoolId(),
                                notice.getCreatedAt()
                        ))
                .toList();
    }
    public List<NoticeResponse>
    getSchoolHirdetmenyek(
            Integer schoolId) {

        return noticeRepository
                .findBySchoolIdAndIsDeletedFalse(
                        schoolId
                )
                .stream()
                .map(notice ->
                        new NoticeResponse(
                                notice.getId(),
                                notice.getTitle(),
                                notice.getContent(),
                                notice.getTeacherId(),
                                notice.getSchoolId(),
                                notice.getCreatedAt()
                        ))
                .toList();
    }
    public List<NoticeResponse>
    getTeacherHirdetmenyek(
            Integer teacherId) {

        return noticeRepository
                .findByTeacherIdAndIsDeletedFalse(
                        teacherId
                )
                .stream()
                .map(notice ->
                        new NoticeResponse(
                                notice.getId(),
                                notice.getTitle(),
                                notice.getContent(),
                                notice.getTeacherId(),
                                notice.getSchoolId(),
                                notice.getCreatedAt()
                        ))
                .toList();
    }
    public String updateHirdetmeny(
            Integer id,
            Notice ujAdatok) {

        Notice notice =
                noticeRepository
                        .findById(id)
                        .orElse(null);

        if(notice == null) {
            return "Nincs ilyen hirdetmény!";
        }

        if(!notice.getTeacherId()
                .equals(ujAdatok.getTeacherId())) {

            return "Csak a létrehozó tanár módosíthatja a hirdetményt!";
        }

        if(ujAdatok.getTitle() == null ||
                ujAdatok.getTitle().isBlank()) {

            return "A cím megadása kötelező!";
        }

        if(ujAdatok.getContent() == null ||
                ujAdatok.getContent().isBlank()) {

            return "A tartalom megadása kötelező!";
        }

        notice.setTitle(
                ujAdatok.getTitle()
        );

        notice.setContent(
                ujAdatok.getContent()
        );

        notice.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );

        noticeRepository.save(
                notice
        );

        return "Hirdetmény módosítva!";
    }

    public String torles(
            Integer id) {

        Notice notice =
                noticeRepository
                        .findById(id)
                        .orElse(null);

        if(notice == null) {
            return "Nincs ilyen hirdetmény!";
        }

        notice.setIsDeleted(true);

        notice.setDeletedAt(
                LocalDateTime.now().withNano(0)
        );

        notice.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );

        noticeRepository.save(
                notice
        );

        return "Hirdetmény törölve!";
    }

}
