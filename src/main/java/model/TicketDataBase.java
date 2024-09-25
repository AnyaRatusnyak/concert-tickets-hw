package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Getter
@Setter
@Entity
public class TicketDataBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BusTicket.TicketType ticketType;
    private LocalDate creationDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDataBase user;
}
