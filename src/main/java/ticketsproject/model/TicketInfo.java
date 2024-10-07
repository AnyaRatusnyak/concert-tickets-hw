package ticketsproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Getter
@Setter
@Entity
public class TicketInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TicketDataBaseType ticketType;
    private LocalDate creationDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;

    public enum TicketDataBaseType {
        DAY, WEEK, MONTH, YEAR
    }
}
