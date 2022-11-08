package resource;

import lpnu.Application;
import lpnu.dto.CinemaDTO;
import lpnu.entity.Cinema;
import lpnu.entity.Film;
import lpnu.entity.Hall;
import lpnu.mapper.CinemaToCinemaDTOMapper;
import lpnu.model.HallSeat;
import lpnu.service.CinemaService;
import lpnu.util.JacksonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class CinemaTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private CinemaToCinemaDTOMapper cinemaMapper;

    @Test
    public void saveCinema_thenStatus200() throws Exception {
        final Film film1 = new Film(null, 180, "Uncharted: Lost legacy", 14, "3D");
        final Film film2 = new Film(null, 190, "Uncharted: legacy", 12, "4D");

        List<Film> films = new ArrayList<>();
        films.add(film1);
        films.add(film2);

        final HallSeat hallSeat = new HallSeat(10, 8);

        final Hall hall = new Hall(null, films, hallSeat);

        List<Hall> halls = new ArrayList<>();
        halls.add(hall);

       final CinemaDTO cinemaDTO = new CinemaDTO(null, "Lumier", halls);

        mvc.perform(post("/api/v1/cinemas").contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JacksonUtil.serialize(cinemaDTO))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Lumier")));
    }

    @Test
    public void getAllCinemas_thenStatus200() throws Exception {
        final Film film1 = new Film(null, 180, "Uncharted: Lost legacy", 14, "3D");
        final Film film2 = new Film(null, 190, "Uncharted: legacy", 12, "4D");

        List<Film> films = new ArrayList<>();
        films.add(film1);
        films.add(film2);

        final HallSeat hallSeat = new HallSeat(10, 8);

        final Hall hall = new Hall(null, films, hallSeat);

        List<Hall> halls = new ArrayList<>();
        halls.add(hall);

        final Cinema cinema = new Cinema(null, "Lumier", halls);
        final CinemaDTO cinemaDTO = cinemaService.saveCinema(cinemaMapper.toDTO(cinema));

        mvc.perform(get("/api/v1/cinemas").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("Lumier")));
    }

    @Test
    public void getCinemaById_thenStatus200() throws Exception {
        final Film film = new Film(null, 180, "Uncharted: Lost legacy", 14, "3D");

        List<Film> films = new ArrayList<>();
        films.add(film);

        final HallSeat hallSeat = new HallSeat(10, 8);

        final Hall hall = new Hall(null, films, hallSeat);

        List<Hall> halls = new ArrayList<>();
        halls.add(hall);

        final Cinema cinema = new Cinema(null, "Lumier", halls);
        final CinemaDTO cinemaDTO = cinemaService.saveCinema(cinemaMapper.toDTO(cinema));

        mvc.perform(get("/api/v1/cinemas/" + cinemaDTO.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Lumier")));
    }

    @Test
    public void updateCinema_thenStatus200() throws Exception {
        final Film film1 = new Film(null, 180, "Uncharted: Lost legacy", 14, "3D");
        final Film film2 = new Film(null, 174, "Uncharted: Paradise", 15, "3D");

        List<Film> films1 = new ArrayList<>();
        films1.add(film1);

        List<Film> films2 = new ArrayList<>();
        films2.add(film2);


        final HallSeat hallSeat1 = new HallSeat(10, 8);
        final HallSeat hallSeat2 = new HallSeat(9, 12);

        final Hall hall1 = new Hall(null, films1, hallSeat1);
        final Hall hall2 = new Hall(null, films2, hallSeat2);

        List<Hall> halls1 = new ArrayList<>();
        halls1.add(hall1);
        List<Hall> halls2 = new ArrayList<>();
        halls2.add(hall2);


        final Cinema cinema1 = new Cinema(null, "Lumier", halls1);
        final CinemaDTO cinemaDTO = cinemaService.saveCinema(cinemaMapper.toDTO(cinema1));

        final Cinema cinema2 = new Cinema(1L, "Planeta-kino", halls2);

        mvc.perform(put("/api/v1/cinemas").contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JacksonUtil.serialize(cinema2))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Planeta-kino")));
    }

    @Test
    public void deleteHallById_thenStatus200() throws Exception {
        final Film film = new Film(null, 180, "Uncharted: Lost legacy", 14, "3D");

        List<Film> films = new ArrayList<>();
        films.add(film);

        final HallSeat hallSeat = new HallSeat(10, 8);

        final Hall hall = new Hall(null, films, hallSeat);

        List<Hall> halls = new ArrayList<>();
        halls.add(hall);


        final Cinema cinema = new Cinema(null, "Lumier", halls);
        final CinemaDTO cinemaDTO = cinemaService.saveCinema(cinemaMapper.toDTO(cinema));

        mvc.perform(delete("/api/v1/cinemas/" + cinemaDTO.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(cinemaService.getAllCinemas().size(), 0);
    }
}
