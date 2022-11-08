package lpnu.service;

import lpnu.dto.FilmDTO;

import java.util.List;

public interface FilmService {
    FilmDTO saveFilm(final FilmDTO filmDTO);
    List<FilmDTO> getAllFilms();
    FilmDTO getFilmById(final Long id);
    FilmDTO updateFilm(final FilmDTO filmDTO);
    void deleteFilmById(final Long id);
}
