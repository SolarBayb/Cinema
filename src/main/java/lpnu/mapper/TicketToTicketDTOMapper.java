package lpnu.mapper;

import lpnu.dto.TicketDTO;
import lpnu.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketToTicketDTOMapper {
    public Ticket toEntity(final TicketDTO ticketDTO){
        return new Ticket(
                ticketDTO.getId(),
                ticketDTO.getSit(),
                ticketDTO.getRow(),
                ticketDTO.getCinemaId(),
                ticketDTO.getHallId(),
                ticketDTO.getFilmId());
    }
    public TicketDTO toDTO(final Ticket ticket){
        return new TicketDTO(
                ticket.getId(),
                ticket.getPrice(),
                ticket.getSit(),
                ticket.getRow(),
                ticket.getCinemaId(),
                ticket.getHallId(),
                ticket.getFilmId());
    }
}
