package model;

public class Client extends User {
    public Client(int id) {
        super(id);
    }

    @Override
    public void printRole() {
        System.out.println("I am model.Client ");
    }

    public Ticket getTicket(int id) {
        return new Ticket(id);
    }
}
