package lpnu.controller;

import lpnu.dto.FilmDTO;
import lpnu.service.FilmService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class FilmController {
    private final FilmService filmService;

    public FilmController(final FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public ResponseEntity<List<FilmDTO>> getFilmHalls() {
        return ResponseEntity.ok().body(filmService.getAllFilms());
    }

    @GetMapping("/films/{id}")
    public ResponseEntity<FilmDTO> getFilmById(@PathVariable final Long id) {
        return ResponseEntity.ok().body(filmService.getFilmById(id));
    }

    @PostMapping("/films")
    public ResponseEntity<FilmDTO> saveFilm(@Validated @RequestBody final FilmDTO filmDTO) {
        return ResponseEntity.ok().body(filmService.saveFilm(filmDTO));
    }

    @PutMapping("/films")
    public ResponseEntity<FilmDTO> updateFilm(@Validated @RequestBody final FilmDTO filmDTO) {
        return ResponseEntity.ok().body(filmService.updateFilm(filmDTO));
    }

    @DeleteMapping("/films/{id}")
    public ResponseEntity deleteFilmById(@PathVariable final Long id) {
        filmService.deleteFilmById(id);
        return ResponseEntity.ok().build();
    }
}
