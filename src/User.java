public class User extends IdentifiableEntity {
    public User(int id) {
        super(id);
    }

    public void printRole() {
        System.out.println("I am User");
    }
}
