public class Admin extends User{
    public Admin(int id) {
        super(id);
    }

    @Override
    public void printRole() {
        System.out.println("I am Admin ");
    }

    public boolean checkTicket(Ticket ticket){
        return ticket.getId() != 0;
    }
}
