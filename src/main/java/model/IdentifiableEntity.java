package model;

public abstract class IdentifiableEntity {
    protected int id;

    public IdentifiableEntity(int id) {
        if (id < 0 || id > 9999) {
            throw new IllegalArgumentException(
                    "ID must contain max 4 digits."
            );
        }
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void print() {
        System.out.println("print content in console");
    }
}
