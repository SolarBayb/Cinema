package lpnu.repository;

import lpnu.entity.Film;
import lpnu.entity.Hall;
import lpnu.exception.ServiceException;
import lpnu.model.EnumTechnology;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HallRepository {
    private final List<Hall> halls = new ArrayList<>();
    private long id = 1;

    public List<Hall> getAllHalls() {
        return new ArrayList<>(halls);
    }

    public void deleteHallById(final Long id) {
        for (final Hall hall : halls) {
            if (hall.getId().equals(id)) {
                halls.remove(hall);
                break;
            }
        }
    }

    public Hall updateHall(final Hall hall) {
        final Hall savedHall = getHallById(hall.getId());

        savedHall.setFilms(hall.getFilms());
        savedHall.setHallSeat(hall.getHallSeat());

        return savedHall;
    }

    public void saveHall(final Hall hall) {
        hall.setId(id);
        ++id;
        halls.add(hall);
    }

    public Hall getHallById(final Long id) {
        return halls.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ServiceException(400, "hall with id " + id + " not found"));
    }

    public Hall addFilm(final Film film, final Long id) {
        final Hall hall = getHallById(id);

        if (hall.getFilms().stream().anyMatch(film::equals)) {
            throw new ServiceException(400, "there is already such film");
        } else {
            hall.getFilms().add(film);
        }
        return hall;
    }

    public Hall updateAllFilmsInHall(final Hall hall){
        hall.setFilms(hall.getFilms().stream()
                .map(this::calculateAndUpdatePrice)
                .toList());
        return hall;
    }

    public Film calculateAndUpdatePrice(final Film film){
        double price = -1;
        for(final EnumTechnology technology : EnumTechnology.values()){
            if(technology.getName().equals(film.getTechnology())){
                price = technology.getPrice();
            }
        }

        if(price == -1){
            throw new ServiceException(400, "enum doesn't contain " + film.getTechnology() + " technology");
        }

        film.setPriceTechnology(price);
        return film;
    }
}