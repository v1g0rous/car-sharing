package carsharing.state;

import carsharing.App;
import carsharing.entity.Company;
import carsharing.service.CarService;
import carsharing.util.Log;

public class CreateCarState extends State{

    public static final String ENTER_CAR_NAME = "Enter the car name:";
    public static final String CAR_WAS_ADDED = "The car was added!\n";
    CarService carService;

    public CreateCarState(App app, CarService carService) {
        this.app = app;
        this.view = app.getView();
        this.carService = carService;
    }

    @Override
    public void display() {
        createCar();
        app.transitionBack();
    }


    public void createCar() {
        try {
            view.showNotification(ENTER_CAR_NAME);
            String carName = view.getUserInput();
            Company company = app.getActiveCompany();

            boolean carCreated = carService.createNewCar(carName, company);

            view.showNotification(carCreated ? CAR_WAS_ADDED : SOMETHING_WENT_WRONG);
        } catch (Exception e) {
            view.showNotification(SOMETHING_WENT_WRONG);
            new Log("Error: unable to createCar", e);
        }
    }
}
