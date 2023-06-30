package carsharing.state;

import carsharing.App;
import carsharing.service.CarService;
import carsharing.service.RentalService;
import carsharing.strategy.UserStrategy;


public class ChooseCarState extends State{
    CarService carService;
    RentalService rentalService;

    public ChooseCarState(App app, CarService carService, RentalService rentalService) {
        this.app = app;
        this.view = app.getView();
        this.carService = carService;
        this.rentalService = rentalService;
    }
    @Override
    public void display() {
        UserStrategy currentStrategy = app.getUserStrategy();
        currentStrategy.handleChooseCar(carService, rentalService);
    }

}
