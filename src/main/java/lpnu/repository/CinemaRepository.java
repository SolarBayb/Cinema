package lpnu.repository;

import lpnu.entity.Cinema;
import lpnu.entity.Film;
import lpnu.entity.Hall;
import lpnu.exception.ServiceException;
import lpnu.model.EnumTechnology;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CinemaRepository {
    private final List<Cinema> cinemas = new ArrayList<>();
    private long id = 1;

    public List<Cinema> getAllCinemas() {
        return new ArrayList<>(cinemas);
    }

    public void deleteCinemaById(final Long id) {
        for (final Cinema cinema : cinemas) {
            if (cinema.getId().equals(id)) {
                cinemas.remove(cinema);
                break;
            }
        }
    }

    public Cinema updateCinema(final Cinema cinema) {
        final Cinema savedCinema = getCinemaById(cinema.getId());

        savedCinema.setName(cinema.getName());
        savedCinema.setHalls(cinema.getHalls());

        return savedCinema;
    }

    public void saveCinema(final Cinema cinema) {
        cinema.setId(id);
        ++id;
        cinemas.add(cinema);
    }

    public Cinema getCinemaById(final Long id) {
        return cinemas.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ServiceException(400, "film with id '" + id + "' not found"));
    }

    public Cinema addHall(final Hall hall, final Long id) {
        final Cinema cinema = getCinemaById(id);

        if (cinema.getHalls().stream().anyMatch(hall::equals)) {
            throw new ServiceException(400, "there is already such hall");
        } else {
            cinema.getHalls().add(hall);
        }
        return cinema;
    }

    public Cinema addFilm(final Film film, final Long cinemaId, final Long hallId) {
        final Cinema cinema = getCinemaById(cinemaId);

        if (cinema.getHalls().get(hallId.intValue() - 1).getFilms().stream().anyMatch(film::equals)) {
            throw new ServiceException(400, "there is already such film");
        } else {
            cinema.getHalls().get(hallId.intValue() - 1).getFilms().add(film);
        }

        return cinema;
    }

    public Cinema updateAllHallsInCinema(final Cinema cinema){
        cinema.setHalls(cinema.getHalls().stream()
                .map(this::updateAllFilmsInHall).toList());
        return cinema;
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
