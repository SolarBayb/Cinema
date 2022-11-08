package lpnu.service;

import lpnu.dto.FilmDTO;
import lpnu.dto.HallDTO;

import java.util.List;

public interface HallService {
    HallDTO saveHall(final HallDTO hallDTO);
    List<HallDTO> getAllHalls();
    HallDTO getHallById(final Long id);
    HallDTO updateHall(final HallDTO hallDTO);
    void deleteHallById(final Long id);
    HallDTO addFilm(final FilmDTO filmDTO, final Long id);
}
