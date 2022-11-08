package lpnu.service;

import lpnu.dto.TicketDTO;

import java.util.List;

public interface TicketService {
    TicketDTO saveTicket(final TicketDTO TicketDTO);
    List<TicketDTO> getAllTickets();
    TicketDTO getTicketById(final Long id);
    TicketDTO updateTicket(final TicketDTO TicketDTO);
    void deleteTicketById(final Long id);
    void removeTicketFromUserByTicketId(final Long id);
    TicketDTO addTicketToUserById(final Long ticketId, final Long userId);
}
