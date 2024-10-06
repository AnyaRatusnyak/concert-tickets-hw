package ticketsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ticketsproject.model.TicketDataBase;
import ticketsproject.service.TicketDataBaseService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/tickets")
public class TicketDataBaseController {
    private final TicketDataBaseService ticketDataBaseService;

    @GetMapping("/{id}")
    public TicketDataBase getTicketById(@PathVariable Long id) {
        return ticketDataBaseService.findById(id);
    }
}
