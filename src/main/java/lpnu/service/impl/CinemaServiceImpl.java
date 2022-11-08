package lpnu.service.impl;

import lpnu.dto.CinemaDTO;
import lpnu.dto.FilmDTO;
import lpnu.dto.HallDTO;
import lpnu.entity.Cinema;
import lpnu.entity.Film;
import lpnu.entity.Hall;
import lpnu.exception.ServiceException;
import lpnu.mapper.CinemaToCinemaDTOMapper;
import lpnu.mapper.FilmToFilmDTOMapper;
import lpnu.mapper.HallToHallDTOMapper;
import lpnu.repository.CinemaRepository;
import lpnu.service.CinemaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaServiceImpl implements CinemaService {
    private final CinemaToCinemaDTOMapper cinemaMapper;
    private final HallToHallDTOMapper hallMapper;
    private final FilmToFilmDTOMapper filmMapper;
    private final CinemaRepository cinemaRepository;

    public CinemaServiceImpl(final CinemaToCinemaDTOMapper cinemaMapper, final HallToHallDTOMapper hallMapper,
                             final FilmToFilmDTOMapper filmMapper, final CinemaRepository cinemaRepository) {
        this.cinemaMapper = cinemaMapper;
        this.hallMapper = hallMapper;
        this.filmMapper = filmMapper;
        this.cinemaRepository = cinemaRepository;
    }

    @Override
    public List<CinemaDTO> getAllCinemas() {
        return cinemaRepository.getAllCinemas().stream()
                .map(cinemaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CinemaDTO getCinemaById(final Long id) {
        return cinemaMapper.toDTO(cinemaRepository.getCinemaById(id));
    }

    @Override
    public void deleteCinemaById(final Long id) {
        cinemaRepository.deleteCinemaById(id);
    }

    @Override
    public CinemaDTO updateCinema(final CinemaDTO cinemaDTO) {
        if (cinemaDTO.getId() == null) {
            throw new ServiceException(400, "id is null");
        }

        final Cinema cinema = cinemaRepository.updateAllHallsInCinema(cinemaMapper.toEntity(cinemaDTO));
        return cinemaMapper.toDTO(cinemaRepository.updateCinema(cinema));
    }

    @Override
    public CinemaDTO saveCinema(final CinemaDTO cinemaDTO) {
        if(cinemaDTO.getId() != null){
            throw new ServiceException(400, "id not null");
        }

        Cinema cinema = cinemaMapper.toEntity(cinemaDTO);
        cinema = cinemaRepository.updateAllHallsInCinema(cinema);

        if (cinemaRepository.getAllCinemas().stream().anyMatch(cinemaMapper.toEntity(cinemaDTO)::equals)){
            throw new ServiceException(400, "cinema is already saved");
        }

        cinemaRepository.saveCinema(cinema);
        return cinemaMapper.toDTO(cinema);
    }

    @Override
    public CinemaDTO addHall(final HallDTO hallDTO, final Long id) {
        final Hall hall = cinemaRepository.updateAllFilmsInHall(hallMapper.toEntity(hallDTO));
        final Cinema cinema = cinemaRepository.addHall(hallMapper.toEntity(hallDTO), id);

        cinemaRepository.saveCinema(cinema);
        return cinemaMapper.toDTO(cinema);
    }

    @Override
    public CinemaDTO addFilm(final FilmDTO filmDTO, final Long cinemaId, final Long hallId) {
        final Film film = cinemaRepository.calculateAndUpdatePrice(filmMapper.toEntity(filmDTO));
        final Cinema cinema = cinemaRepository.addFilm(filmMapper.toEntity(filmDTO), cinemaId, hallId);

        cinemaRepository.saveCinema(cinema);
        return cinemaMapper.toDTO(cinema);
    }
}