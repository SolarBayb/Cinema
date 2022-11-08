package lpnu.repository;

import lpnu.dto.TicketDTO;
import lpnu.entity.Ticket;
import lpnu.entity.User;
import lpnu.exception.ServiceException;
import lpnu.mapper.TicketToTicketDTOMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketRepository {
    private final List<Ticket> tickets = new ArrayList<>();
    private long id = 1;

    private final UserRepository userRepository;
    private final HallRepository hallRepository;
    private final TicketToTicketDTOMapper ticketMapper;

    public TicketRepository(final UserRepository userRepository, final TicketToTicketDTOMapper ticketMapper,
                            final HallRepository hallRepository) {
        this.userRepository = userRepository;
        this.hallRepository = hallRepository;
        this.ticketMapper = ticketMapper;
    }

    public List<Ticket> getAllTickets() {
        return new ArrayList<>(tickets);
    }

    public void deleteTicketById(final Long id) {
        for (final Ticket ticket : tickets) {
            if (ticket.getId().equals(id)) {
                tickets.remove(ticket);
                break;
            }
        }
    }

    public Ticket updateTicket(final Ticket ticket) {
        final Ticket savedTicket = getTicketById(ticket.getId());

        savedTicket.setId(ticket.getId());
        savedTicket.setPrice(ticket.getPrice());
        savedTicket.setSit(ticket.getSit());
        savedTicket.setRow(ticket.getRow());
        savedTicket.setCinemaId(ticket.getCinemaId());
        savedTicket.setHallId(ticket.getHallId());
        savedTicket.setFilmId(ticket.getFilmId());

        return savedTicket;
    }

    public void saveTicket(final Ticket ticket) {
        ticket.setId(id);
        ++id;
        tickets.add(ticket);
    }

    public Ticket getTicketById(final Long id) {
        return tickets.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ServiceException(400, "ticket with id " + id + " not found"));
    }

    public void removeTicketFromUserByTicketId(final Long id) {
        TicketDTO ticketDTO = ticketMapper.toDTO(getTicketById(id));
        for (User user : userRepository.getAllUsers()) {
                user.getTicketDTOList().remove(ticketDTO);
        }
    }

    public Ticket calculateAndUpdatePrice(final Ticket ticket){
        if (ticket.getRow() > hallRepository.getHallById(ticket.getHallId()).getHallSeat().getRows()
                || ticket.getSit() > hallRepository.getHallById(ticket.getHallId()).getHallSeat().getColumns()) {
            throw new ServiceException(400, "hall doesn't have such sit or/and row");
        }

        double price = ticket.getRow() == hallRepository.getHallById(ticket.getHallId()).getHallSeat().getRows()
                ? hallRepository.getHallById(ticket.getHallId()).getFilms().get(ticket.getFilmId().intValue()).getPriceTechnology() + Ticket.MARK_UP + Ticket.STANDART_PRICE
                : hallRepository.getHallById(ticket.getHallId()).getFilms().get(ticket.getFilmId().intValue()).getPriceTechnology() + Ticket.STANDART_PRICE;

        ticket.setPrice(price);
        return ticket;
    }
}
