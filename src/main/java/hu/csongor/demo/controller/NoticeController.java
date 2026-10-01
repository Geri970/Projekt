package hu.csongor.demo.controller;

import hu.csongor.demo.dto.response.HirdetmenyResponse;
import hu.csongor.demo.entity.Hirdetmeny;
import hu.csongor.demo.service.HirdetmenyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hirdetmeny")
public class HirdetmenyController {

    private final HirdetmenyService hirdetmenyService;

    public HirdetmenyController(
            HirdetmenyService hirdetmenyService) {

        this.hirdetmenyService = hirdetmenyService;
    }

    @PostMapping
    public Object createHirdetmeny(
            @RequestBody Hirdetmeny hirdetmeny) {

        return hirdetmenyService
                .createHirdetmeny(hirdetmeny);
    }

    @GetMapping
    public List<HirdetmenyResponse>
    getAllHirdetmenyek() {

        return hirdetmenyService
                .getAllHirdetmenyek();
    }

    @GetMapping("/school/{id}")
    public List<HirdetmenyResponse>
    getSchoolHirdetmenyek(
            @PathVariable Integer id) {

        return hirdetmenyService
                .getSchoolHirdetmenyek(id);
    }

    @GetMapping("/teacher/{id}")
    public List<HirdetmenyResponse>
    getTeacherHirdetmenyek(
            @PathVariable Integer id) {

        return hirdetmenyService
                .getTeacherHirdetmenyek(id);
    }

    @DeleteMapping("/{id}")
    public String torles(
            @PathVariable Integer id) {

        return hirdetmenyService
                .torles(id);
    }
    @PutMapping("/{id}")
    public String updateHirdetmeny(
            @PathVariable Integer id,
            @RequestBody Hirdetmeny ujAdatok){

        return hirdetmenyService
                .updateHirdetmeny(
                        id,
                        ujAdatok
                );
    }
}
