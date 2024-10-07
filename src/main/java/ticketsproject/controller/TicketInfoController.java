package ticketsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ticketsproject.model.TicketInfo;
import ticketsproject.service.TicketInfoService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/tickets")
public class TicketInfoController {
    private final TicketInfoService ticketInfoService;

    @GetMapping("/{id}")
    public TicketInfo getTicketById(@PathVariable Long id) {
        return ticketInfoService.findById(id);
    }
}
