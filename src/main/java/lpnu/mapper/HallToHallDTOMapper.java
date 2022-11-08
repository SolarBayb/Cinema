package lpnu.mapper;

import lpnu.dto.HallDTO;
import lpnu.entity.Hall;
import org.springframework.stereotype.Component;

@Component
public class HallToHallDTOMapper {
    public Hall toEntity(final HallDTO hallDTO){
        return new Hall(
               hallDTO.getId(),
                hallDTO.getFilms(),
                hallDTO.getHallSeat());
    }
    public HallDTO toDTO(final Hall hall){
        return new HallDTO(
                hall.getId(),
                hall.getFilms(),
                hall.getHallSeat());
    }
}
