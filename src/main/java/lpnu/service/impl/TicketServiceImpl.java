package lpnu.service.impl;

import lpnu.dto.TicketDTO;
import lpnu.entity.Hall;
import lpnu.entity.Ticket;
import lpnu.entity.User;
import lpnu.exception.ServiceException;
import lpnu.mapper.TicketToTicketDTOMapper;
import lpnu.repository.HallRepository;
import lpnu.repository.TicketRepository;
import lpnu.repository.UserRepository;
import lpnu.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketToTicketDTOMapper ticketMapper;
    private final TicketRepository ticketRepository;
    private final HallRepository hallRepository;
    private final UserRepository userRepository;

    public TicketServiceImpl(final TicketToTicketDTOMapper ticketMapper, final TicketRepository ticketRepository,
                             final HallRepository hallRepository, final UserRepository userRepository) {
        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
        this.hallRepository = hallRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TicketDTO> getAllTickets() {
        return ticketRepository.getAllTickets().stream()
                .map(ticketMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TicketDTO getTicketById(final Long id) {
        return ticketMapper.toDTO(ticketRepository.getTicketById(id));
    }

    @Override
    public void deleteTicketById(final Long id) {
        ticketRepository.deleteTicketById(id);
    }

    @Override
    public TicketDTO updateTicket(final TicketDTO ticketDTO) {
        if (ticketDTO.getId() == null) {
            throw new ServiceException(400, "id is null");
        }

        final Ticket ticket = ticketRepository.calculateAndUpdatePrice(ticketMapper.toEntity(ticketDTO));
        return ticketMapper.toDTO(ticketRepository.updateTicket(ticket));
    }

    @Override
    public TicketDTO saveTicket(final TicketDTO ticketDTO) {
        if (ticketDTO.getId() != null) {
            throw new ServiceException(400, "id not null");
        }

        final Ticket ticket = ticketRepository.calculateAndUpdatePrice(ticketMapper.toEntity(ticketDTO));

        if (ticketRepository.getAllTickets().stream().anyMatch(ticket::equals)){
            throw new ServiceException(400, "ticket is already saved");
        }

        ticketRepository.saveTicket(ticket);
        return ticketMapper.toDTO(ticket);
    }

    @Override
    public void removeTicketFromUserByTicketId(final Long id) {
        ticketRepository.removeTicketFromUserByTicketId(id);
    }

    @Override
    public TicketDTO addTicketToUserById(final Long ticketId, final Long userId) {
        final Ticket ticket = ticketRepository.getTicketById(ticketId);
        final User user = userRepository.getUserById(userId);

        if (ticketRepository.getAllTickets().stream().anyMatch(ticketRepository.getTicketById(ticketId)::equals)){
            throw new ServiceException(400, "ticket is already saved");
        }

        user.getTicketDTOList().add(ticketMapper.toDTO(ticket));
        userRepository.saveUser(user);
        return ticketMapper.toDTO(ticket);
    }

}
