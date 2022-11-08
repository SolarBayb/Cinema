package lpnu.service.impl;

import lpnu.dto.FilmDTO;
import lpnu.dto.HallDTO;
import lpnu.entity.Film;
import lpnu.entity.Hall;
import lpnu.exception.ServiceException;
import lpnu.mapper.FilmToFilmDTOMapper;
import lpnu.mapper.HallToHallDTOMapper;
import lpnu.repository.HallRepository;
import lpnu.service.HallService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HallServiceImpl implements HallService {
    private final HallToHallDTOMapper hallMapper;
    private final FilmToFilmDTOMapper filmMapper;
    private final HallRepository hallRepository;
    private final FilmServiceImpl filmService;

    public HallServiceImpl(final HallToHallDTOMapper hallMapper, final FilmToFilmDTOMapper filmMapper,
                           final HallRepository hallRepository, final FilmServiceImpl filmService) {
        this.hallMapper = hallMapper;
        this.filmMapper = filmMapper;
        this.hallRepository = hallRepository;
        this.filmService = filmService;
    }

    @Override
    public List<HallDTO> getAllHalls() {
        return hallRepository.getAllHalls().stream()
                .map(hallMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HallDTO getHallById(final Long id) {
        return hallMapper.toDTO(hallRepository.getHallById(id));
    }

    @Override
    public void deleteHallById(final Long id) {
        hallRepository.deleteHallById(id);
    }

    @Override
    public HallDTO updateHall(final HallDTO hallDTO) {
        if (hallDTO.getId() == null) {
            throw new ServiceException(400, "id is null");
        }

        final Hall hall = hallRepository.updateAllFilmsInHall(hallMapper.toEntity(hallDTO));
        return hallMapper.toDTO(hallRepository.updateHall(hall));
    }

    @Override
    public HallDTO saveHall(final HallDTO hallDTO) {
        if(hallDTO.getId() != null){
            throw new ServiceException(400, "id not null");
        }

        Hall hall = hallMapper.toEntity(hallDTO);
        hall = hallRepository.updateAllFilmsInHall(hall);

        if (hallRepository.getAllHalls().stream().anyMatch(hallMapper.toEntity(hallDTO)::equals)) {
            throw new ServiceException(400, "hall is already saved");
        }

        hallRepository.saveHall(hall);
        return hallMapper.toDTO(hall);
    }

    @Override
    public HallDTO addFilm(final FilmDTO filmDTO, final Long id) {
        final Film newFilm = filmMapper.toEntity(filmService.saveFilm(filmDTO));
        final Hall hall = hallRepository.addFilm(newFilm, id);

        hallRepository.saveHall(hall);
        return hallMapper.toDTO(hall);
    }
}
