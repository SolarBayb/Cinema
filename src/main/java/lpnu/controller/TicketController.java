package lpnu.controller;

import lpnu.dto.TicketDTO;
import lpnu.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        return ResponseEntity.ok().body(ticketService.getAllTickets());
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable final Long id) {
        return ResponseEntity.ok().body(ticketService.getTicketById(id));
    }

    @PostMapping("/tickets")
    public ResponseEntity<TicketDTO> saveTicket(@Validated @RequestBody final TicketDTO ticketDTO) {
        return ResponseEntity.ok().body(ticketService.saveTicket(ticketDTO));
    }

    @PutMapping("/tickets")
    public ResponseEntity<TicketDTO> updateTicket(@Validated @RequestBody final TicketDTO ticketDTO) {
        return ResponseEntity.ok().body(ticketService.updateTicket(ticketDTO));
    }

    @DeleteMapping("/tickets/{id}")
    public ResponseEntity deleteTicketById(@PathVariable final Long id) {
        ticketService.deleteTicketById(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/tickets-user/{id}")
    public ResponseEntity<TicketDTO> removeTicketFromUserByTicketId(@PathVariable final Long id){
        ticketService.removeTicketFromUserByTicketId(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/tickets/{ticketId}/{userId}")
    public ResponseEntity<TicketDTO> addTicketToUserById(@PathVariable final Long ticketId, @PathVariable final Long userId) {
        return ResponseEntity.ok().body(ticketService.addTicketToUserById(ticketId, userId));
    }
}
