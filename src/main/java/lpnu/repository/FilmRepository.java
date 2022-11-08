package lpnu.repository;

import lpnu.entity.Film;
import lpnu.exception.ServiceException;
import lpnu.model.EnumTechnology;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmRepository {
    private final List<Film> films = new ArrayList<>();
    private long id = 1;

    public List<Film> getAllFilms() {
        return new ArrayList<>(films);
    }

    public void deleteFilmById(final Long id) {
        for (final Film film : films) {
            if (film.getId().equals(id)) {
                films.remove(film);
                break;
            }
        }
    }

    public Film updateFilm(final Film film) {
        final Film savedFilm = getFilmById(film.getId());

        savedFilm.setName(film.getName());
        savedFilm.setDuration(film.getDuration());
        savedFilm.setMinAge(film.getMinAge());
        savedFilm.setPriceTechnology(film.getPriceTechnology());
        savedFilm.setTechnology(film.getTechnology());

        return savedFilm;
    }

    public void saveFilm(final Film film) {
        film.setId(id);
        ++id;
        films.add(film);
    }

    public Film getFilmById(final Long id) {
        return films.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ServiceException(400, "film with id " + id + " not found"));
    }

    public Film calculateAndUpdatePrice(final Film film) {
        double price = -1;
        for (final EnumTechnology technology : EnumTechnology.values()) {
            if (technology.getName().equals(film.getTechnology())) {
                price = technology.getPrice();
            }
        }

        if (price == -1) {
            throw new ServiceException(400, "enum doesn't contain " + film.getTechnology() + " technology");
        }

        film.setPriceTechnology(price);
        return film;
    }
}
