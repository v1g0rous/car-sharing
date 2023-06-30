package carsharing.entity;


public class Customer {

    private int id;
    private Integer rentedCarId;
    private String name;

    public Customer(int id, Integer rentedCarId, String name) {
        this.id = id;
        this.rentedCarId = rentedCarId;
        this.name = name;
    }

    public Customer(int rentedCarId, String name) {
        this.rentedCarId = rentedCarId;
        this.name = name;
    }

    public Customer(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRentedCarId() {
        return rentedCarId;
    }

    public void setRentedCarId(Integer rentedCarId) {
        this.rentedCarId = rentedCarId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
