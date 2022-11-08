package lpnu.service;

import lpnu.dto.CinemaDTO;
import lpnu.dto.FilmDTO;
import lpnu.dto.HallDTO;

import java.util.List;

public interface CinemaService {
    CinemaDTO saveCinema(final CinemaDTO cinemaDTO);
    List<CinemaDTO> getAllCinemas();
    CinemaDTO getCinemaById(final Long id);
    CinemaDTO updateCinema(final CinemaDTO cinemaDTO);
    void deleteCinemaById(final Long id);
    CinemaDTO addHall(final HallDTO hallDTO, final Long id);
    CinemaDTO addFilm(final FilmDTO filmDTO, final Long cinemaId, final Long hallId);
}
