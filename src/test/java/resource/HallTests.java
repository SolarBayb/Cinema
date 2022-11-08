package resource;

import lpnu.Application;
import lpnu.dto.HallDTO;
import lpnu.entity.Film;
import lpnu.entity.Hall;
import lpnu.mapper.HallToHallDTOMapper;
import lpnu.model.HallSeat;
import lpnu.service.HallService;
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
public class HallTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private HallService hallService;

    @Autowired
    private HallToHallDTOMapper hallMapper;

    @Test
    public void saveHall_thenStatus200() throws Exception {
        final Film film1 = new Film(null, 180, "Uncharted: Lost legacy", 14, "3D");
        final Film film2 = new Film(null, 190, "Uncharted: legacy", 12, "4D");

        List<Film> films = new ArrayList<>();
        films.add(film1);
        films.add(film2);

        final HallSeat hallSeat = new HallSeat(10, 8);

        final HallDTO hallDTO = new HallDTO(null, films, hallSeat);

        mvc.perform(post("/api/v1/halls").contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JacksonUtil.serialize(hallDTO))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.films[0].priceTechnology", is(40.0)));
    }

    @Test
    public void getAllHalls_thenStatus200() throws Exception {
        final Film film1 = new Film(null, 180, "Uncharted: Lost legacy", 14, "3D");
        final Film film2 = new Film(null, 190, "Uncharted: legacy", 12, "4D");

        List<Film> films = new ArrayList<>();
        films.add(film1);
        films.add(film2);

        final HallSeat hallSeat = new HallSeat(10, 8);

        final Hall hall = new Hall(null, films, hallSeat);
        final HallDTO hallDTO = hallService.saveHall(hallMapper.toDTO(hall));

        mvc.perform(get("/api/v1/halls").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].films[0].priceTechnology", is(40.0)));
    }

    @Test
    public void getHallById_thenStatus200() throws Exception {
        final Film film1 = new Film(null, 180, "Uncharted: Lost legacy", 14, "3D");

        List<Film> films = new ArrayList<>();
        films.add(film1);

        final HallSeat hallSeat = new HallSeat(10, 8);

        final Hall hall = new Hall(null, films, hallSeat);
        final HallDTO hallDTO = hallService.saveHall(hallMapper.toDTO(hall));

        mvc.perform(get("/api/v1/halls/" + hallDTO.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.films[0].priceTechnology", is(40.0)));
    }

    @Test
    public void updateHall_thenStatus200() throws Exception {
        final Film film1 = new Film(null, 180, "Uncharted: Lost legacy", 14, "3D");
        final Film film2 = new Film(null, 190, "Uncharted: legacy", 12, "4D");

        List<Film> films1 = new ArrayList<>();
        films1.add(film1);
        List<Film> films2 = new ArrayList<>();
        films2.add(film2);

        final HallSeat hallSeat1 = new HallSeat(10, 8);
        final HallSeat hallSeat2 = new HallSeat(12, 6);

        final Hall hall1 = new Hall(null, films1, hallSeat1);
        final HallDTO hallDTO = hallService.saveHall(hallMapper.toDTO(hall1));

        final Hall hall2 = new Hall(1L, films2, hallSeat2);

        mvc.perform(put("/api/v1/halls").contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JacksonUtil.serialize(hall2))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.films[0].priceTechnology", is(90.0)));
    }

    @Test
    public void deleteHallById_thenStatus200() throws Exception {
        final Film film1 = new Film(null, 180, "Uncharted: Lost legacy", 14, "3D");
        final Film film2 = new Film(null, 190, "Uncharted: legacy", 12, "4D");

        List<Film> films1 = new ArrayList<>();
        films1.add(film1);
        List<Film> films2 = new ArrayList<>();
        films2.add(film2);

        final HallSeat hallSeat1 = new HallSeat(10, 8);
        final HallSeat hallSeat2 = new HallSeat(12, 6);

        final Hall hall1 = new Hall(null, films1, hallSeat1);
        final Hall hall2 = new Hall(null, films2, hallSeat2);
        hallService.saveHall(hallMapper.toDTO(hall1));

        final HallDTO hallDTO = hallService.saveHall(hallMapper.toDTO(hall2));

        mvc.perform(delete("/api/v1/halls/" + hallDTO.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(hallService.getAllHalls().size(), 1);
    }
}
