package ticketsproject.service.impl;

import java.util.Optional;
import java.util.stream.Stream;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ticketsproject.model.TicketInfo;
import ticketsproject.repository.TicketInfoRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketInfoServiceImplTest {
    public static final Long ID1 = 1L;
    public static final Long ID2 = 100L;
    public static final Long ID3 = 5L;
    @Mock
    private TicketInfoRepository ticketInfoRepository;
    @InjectMocks
    private TicketInfoServiceImpl ticketInfoService;

    @Test
    @DisplayName("Verify findById() method works")
    void findById_ExistingId_ReturnsTicket() {
        // Given
        Long id = ID1;
        TicketInfo expected = new TicketInfo();
        expected.setId(id);
        // Mocking behavior
        when(ticketInfoRepository.findById(id)).thenReturn(Optional.of(expected));
        // When
        TicketInfo actual = ticketInfoService.findById(id);
        // Then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Verify findById() method throws exception for non-existing ID")
    @MethodSource("provideNonExistingIds")
    void findById_NonExistingId_ThrowsEntityNotFoundException(long id) {
        // Mocking behavior
        when(ticketInfoRepository.findById(id)).thenReturn(Optional.empty());
        // When / Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            ticketInfoService.findById(id);
        });
        assertEquals("Can`t find a ticket with id: " + id, exception.getMessage());
    }

    private static Stream<Long> provideNonExistingIds() {
        return Stream.of(ID1, ID2, ID3);
    }
}
