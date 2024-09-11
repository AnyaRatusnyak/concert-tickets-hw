package model;

public class BusTicket extends Ticket{
private TicketClass ticketClass;
private TicketType ticketType;
private String startDate;
private int busTicketPrice;

    public BusTicket(int id, TicketClass ticketClass, TicketType ticketType, String startDate, int busTicketPrice) {
        super(id);
        this.ticketClass = ticketClass;
        this.ticketType = ticketType;
        this.startDate = startDate;
        this.busTicketPrice = busTicketPrice;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public String getStartDate() {
        return startDate;
    }

    public int getBusTicketPrice() {
        return busTicketPrice;
    }

    public TicketClass getTicketClass() {
        return ticketClass;
    }

    @Override
    public String toString() {
        return "BusTicket{" +
                "ticketClass=" + ticketClass +
                ", ticketType=" + ticketType +
                ", startDate='" + startDate + '\'' +
                ", busTicketPrice=" + busTicketPrice +
                ", id=" + id +
                '}';
    }

    public enum TicketClass {
CLA, STD
}

public enum TicketType  {
       DAY, WEEK,MONTH,YEAR
    }
}
