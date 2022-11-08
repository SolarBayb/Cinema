package lpnu.mapper;

import lpnu.dto.CinemaDTO;
import lpnu.entity.Cinema;
import org.springframework.stereotype.Component;

@Component
public class CinemaToCinemaDTOMapper {
    public Cinema toEntity(final CinemaDTO cinemaDTO){
        return new Cinema(
                cinemaDTO.getId(),
                cinemaDTO.getName(),
                cinemaDTO.getHalls());
    }
    public CinemaDTO toDTO(final Cinema cinema){
        return new CinemaDTO(
                cinema.getId(),
                cinema.getName(),
                cinema.getHalls());
    }
}
