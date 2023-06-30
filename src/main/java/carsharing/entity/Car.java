package carsharing.entity;

public class Car {
    private int id;
    private int companyId;
    private String name;

    public Car(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Car(String name) {
        this.name = name;
    }

    public Car(String name, int companyId) {
        this.name = name;
        this.companyId = companyId;
    }

    public Car(int id, String name, int companyId) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
