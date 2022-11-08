package resource;

import lpnu.Application;
import lpnu.dto.FilmDTO;
import lpnu.entity.Film;
import lpnu.mapper.FilmToFilmDTOMapper;
import lpnu.service.FilmService;
import lpnu.util.JacksonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class FilmTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private FilmService filmService;

    @Autowired
    private FilmToFilmDTOMapper filmMapper;

    @Test
    public void saveFilm_thenStatus200() throws Exception {
        final FilmDTO filmDTO = new FilmDTO(null, 180, "Uncharted: Lost legacy", 14, 0.0, "3D");

        mvc.perform(post("/api/v1/films").contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JacksonUtil.serialize(filmDTO))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.priceTechnology", is(40.0)));
    }

    @Test
    public void getAllFilms_thenStatus200() throws Exception {
        final Film film = new Film(null, 180, "Uncharted: Lost legacy", 14, "3D");
        filmService.saveFilm(filmMapper.toDTO(film));

        mvc.perform(get("/api/v1/films").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].priceTechnology", is(40.0)));
    }

    @Test
    public void getFilmById_thenStatus200() throws Exception {
        final Film film = new Film(null, 180, "Uncharted: Lost legacy", 14, "3D");
        final FilmDTO filmDTO = filmService.saveFilm(filmMapper.toDTO(film));

        mvc.perform(get("/api/v1/films/" + filmDTO.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.priceTechnology", is(40.0)));
    }

    @Test
    public void updateFilm_thenStatus200() throws Exception {
        final Film film1 = new Film(null, 180, "Uncharted: Lost legacy", 14, "3D");
        final Film film2 = new Film(1L, 190, "Uncharted: legacy", 12, "4D");

        filmService.saveFilm(filmMapper.toDTO(film1));

        mvc.perform(put("/api/v1/films").contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JacksonUtil.serialize(film2))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceTechnology", is(90.0)));
    }

    @Test
    public void deleteFilmById_thenStatus200() throws Exception {
        final Film film1 = new Film(null, 180, "Uncharted: Lost legacy", 14, "3D");
        final Film film2 = new Film(null, 190, "Uncharted: legacy", 12, "4D");

        filmService.saveFilm(filmMapper.toDTO(film1));
        final FilmDTO filmDTO = filmService.saveFilm(filmMapper.toDTO(film2));

        mvc.perform(delete("/api/v1/films/" + filmDTO.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(filmService.getAllFilms().size(),1);
    }
}
