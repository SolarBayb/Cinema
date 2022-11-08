package lpnu.entity;

import lpnu.model.HallSeat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hall{
    private Long id;
    private List<Film> films = new ArrayList<>();
    private HallSeat hallSeat;

    public Hall(){

    }

    public Hall(final Long id, List<Film> films, HallSeat hallSeat) {
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

    public void addFilm(final Film film){
        films.add(film);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall hall = (Hall) o;
        return Objects.equals(films, hall.films) && Objects.equals(hallSeat, hall.hallSeat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(films, hallSeat);
    }
}
