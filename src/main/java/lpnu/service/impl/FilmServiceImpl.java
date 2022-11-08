package lpnu.service.impl;

import lpnu.dto.FilmDTO;
import lpnu.entity.Film;
import lpnu.exception.ServiceException;
import lpnu.mapper.FilmToFilmDTOMapper;
import lpnu.repository.FilmRepository;
import lpnu.service.FilmService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmToFilmDTOMapper filmMapper;
    private final FilmRepository filmRepository;

    public FilmServiceImpl(final FilmToFilmDTOMapper filmMapper, final FilmRepository filmRepository) {
        this.filmMapper = filmMapper;
        this.filmRepository = filmRepository;
    }

    @Override
    public List<FilmDTO> getAllFilms() {
        return filmRepository.getAllFilms().stream()
                .map(filmMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FilmDTO getFilmById(final Long id) {
        return filmMapper.toDTO(filmRepository.getFilmById(id));
    }

    @Override
    public void deleteFilmById(final Long id) {
        filmRepository.deleteFilmById(id);
    }

    @Override
    public FilmDTO updateFilm(final FilmDTO filmDTO) {
        if (filmDTO.getId() == null) {
            throw new ServiceException(400, "id is null");
        }

        final Film film = filmRepository.calculateAndUpdatePrice(filmMapper.toEntity(filmDTO));
        return filmMapper.toDTO(filmRepository.updateFilm(film));
    }

    @Override
    public FilmDTO saveFilm(final FilmDTO filmDTO) {
        if (filmDTO.getId() != null) {
            throw new ServiceException(400, "id not null");
        }

        final Film film = filmRepository.calculateAndUpdatePrice(filmMapper.toEntity(filmDTO));
        if (filmRepository.getAllFilms().stream().anyMatch(filmMapper.toEntity(filmDTO)::equals)) {
            throw new ServiceException(400, "film is already saved");
        }

        filmRepository.saveFilm(film);
        return filmMapper.toDTO(film);
    }
}
