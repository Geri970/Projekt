package hu.csongor.demo.controller;

import hu.csongor.demo.entity.Jelentkezes;
import hu.csongor.demo.service.JelentkezesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jelentkezes")
public class JelentkezesController {

    private final JelentkezesService jelentkezesService;

    public JelentkezesController(JelentkezesService jelentkezesService) {
        this.jelentkezesService = jelentkezesService;
    }

    @PostMapping
    public Object jelentkezes(@RequestBody Jelentkezes jelentkezes) {
        return jelentkezesService.jelentkezes(jelentkezes);
    }

    @GetMapping
    public List<Jelentkezes> getAllJelentkezes() {
        return jelentkezesService.getAllJelentkezes();
    }

    @GetMapping("/szakkor/{id}/db")
    public Map<String, Long> getJelentkezokSzama(@PathVariable Integer id) {
        return jelentkezesService.getJelentkezokSzama(id);
    }

    @GetMapping("/user/{id}")
    public List<Jelentkezes> getUserJelentkezesek(@PathVariable Integer id) {
        return jelentkezesService.getUserJelentkezesek(id);
    }

    @GetMapping("/szakkor/{id}")
    public List<Jelentkezes> getSzakkorJelentkezoi(@PathVariable Integer id) {
        return jelentkezesService.getSzakkorJelentkezoi(id);
    }

    @DeleteMapping("/{id}")
    public String torles(@PathVariable Integer id) {
        return jelentkezesService.torles(id);
    }
}