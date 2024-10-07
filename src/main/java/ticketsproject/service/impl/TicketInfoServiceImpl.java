package ticketsproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ticketsproject.repository.TicketInfoRepository;
import ticketsproject.model.TicketInfo;
import ticketsproject.service.TicketInfoService;

@Service
@RequiredArgsConstructor
public class TicketInfoServiceImpl implements TicketInfoService {
    private final TicketInfoRepository ticketInfoRepository;

    @Override
    public TicketInfo findById(Long id) {
        return ticketInfoRepository.findById(id).get();
    }
}
