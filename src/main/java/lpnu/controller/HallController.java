package lpnu.controller;

import lpnu.dto.FilmDTO;
import lpnu.dto.HallDTO;
import lpnu.mapper.FilmToFilmDTOMapper;
import lpnu.mapper.HallToHallDTOMapper;
import lpnu.service.FilmService;
import lpnu.service.HallService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class HallController {
    private final HallService hallService;
    private final FilmService filmService;
    private final HallToHallDTOMapper hallMapper;
    private final FilmToFilmDTOMapper filmMapper;

    public HallController(final HallService hallService, final FilmService filmService,
                          final HallToHallDTOMapper hallMapper, final FilmToFilmDTOMapper filmMapper) {
        this.hallService = hallService;
        this.filmService = filmService;
        this.hallMapper = hallMapper;
        this.filmMapper = filmMapper;
    }

    @GetMapping("/halls")
    public ResponseEntity<List<HallDTO>> getAllHalls() {
        return ResponseEntity.ok().body(hallService.getAllHalls());
    }

    @GetMapping("/halls/{id}")
    public ResponseEntity<HallDTO> getHallById(@PathVariable final Long id) {
        return ResponseEntity.ok().body(hallService.getHallById(id));
    }

    @PostMapping("/halls")
    public ResponseEntity<HallDTO> saveHall(@Validated @RequestBody final HallDTO hallDTO) {
        return ResponseEntity.ok().body(hallService.saveHall(hallDTO));
    }

    @PutMapping("/halls")
    public ResponseEntity<HallDTO> updateHall(@Validated @RequestBody final HallDTO hallDTO) {
        return ResponseEntity.ok().body(hallService.updateHall(hallDTO));
    }

    @PutMapping("/halls-film/{id}")
    public ResponseEntity<HallDTO> addFilm(@Validated @RequestBody final FilmDTO filmDTO, @PathVariable final Long id){
        return ResponseEntity.ok().body(hallService.addFilm(filmDTO, id));
    }

    @DeleteMapping("/halls/{id}")
    public ResponseEntity deleteHallById(@PathVariable final Long id) {
        hallService.deleteHallById(id);
        return ResponseEntity.ok().build();
    }
}
