package lpnu.dto;

import lpnu.entity.Film;
import lpnu.model.HallSeat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HallDTO {
    private Long id;

    private List<Film> films = new ArrayList<>();

    private HallSeat hallSeat;

    public HallDTO(){

    }

    public HallDTO(Long id, List<Film> films, HallSeat hallSeat) {
        this.id = id;
        this.films = films;
        this.hallSeat = hallSeat;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(final List<Film> films) {
        this.films = films;
    }

    public HallSeat getHallSeat() {
        return hallSeat;
    }

    public void setHallSeat(final HallSeat hallSeat) {
        this.hallSeat = hallSeat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HallDTO hallDTO = (HallDTO) o;
        return Objects.equals(films, hallDTO.films) && Objects.equals(hallSeat, hallDTO.hallSeat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(films, hallSeat);
    }
}
