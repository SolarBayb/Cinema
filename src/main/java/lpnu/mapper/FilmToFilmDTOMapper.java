package lpnu.mapper;

import lpnu.dto.FilmDTO;
import lpnu.entity.Film;
import org.springframework.stereotype.Component;

@Component
public class FilmToFilmDTOMapper {
    public Film toEntity(final FilmDTO filmDTO){
        return new Film(
                filmDTO.getId(),
                filmDTO.getDuration(),
                filmDTO.getName(),
                filmDTO.getMinAge(),
                filmDTO.getTechnology());
    }

    public Film toFullEntity(final FilmDTO filmDTO){
        return new Film(
                filmDTO.getId(),
                filmDTO.getDuration(),
                filmDTO.getName(),
                filmDTO.getMinAge(),
                filmDTO.getPriceTechnology(),
                filmDTO.getTechnology());
    }

    public FilmDTO toDTO(final Film film){
        return new FilmDTO(
                film.getId(),
                film.getDuration(),
                film.getName(),
                film.getMinAge(),
                film.getPriceTechnology(),
                film.getTechnology());
    }
}
