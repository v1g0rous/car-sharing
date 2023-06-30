package carsharing.state;

import carsharing.App;
import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.service.CustomerService;
import carsharing.service.RentalService;
import carsharing.util.Log;
import carsharing.view.AppMenu;
import carsharing.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class CustomerInfoState extends State {

    public static final String ALREADY_RENTED_A_CAR = "You've already rented a car!\n";
    public static final String RENTED_CAR_TITLE = "Your rented car:\n";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong, please, try again\n";
    public static final String CLIENT_DIDN_T_RENT_A_CAR = "You didn't rent a car!\n";
    public static final String COMPANY_TITLE = "Company:\n";
    public static final String CLIENT_RETURNED_RENTED_CAR = "You've returned a rented car!\n";

    private List<MenuItem> customerInfoMenuItems = new ArrayList<>(List.of(
            new MenuItem("Rent a car", "rentCar", 1, 1),
            new MenuItem("Return a rented car", "returnRentedCar", 2, 2),
            new MenuItem("My rented car", "myRentedCar", 3, 3),
            new MenuItem("Back", "back", 4, 0)
    ));

    RentalService rentalService;
    CustomerService customerService;

    public CustomerInfoState(App app, CustomerService customerService, RentalService rentalService) {
        this.app = app;
        this.view = app.getView();
        this.rentalService = rentalService;
        this.customerService = customerService;
    }

    public void request(Command userCommand) {
        try {
            switch (userCommand) {
                case RENT_CAR -> rentCar();
                case RETURN_RENTED_CAR -> returnCar();
                case MY_RENTED_CAR -> showRentedCar();
                case BACK -> moveBack();
                case UNKNOWN_COMMAND -> doInvalidCommandFlow();
            }
        } catch (Exception e) {
            new Log("AppController failed", e);
            view.showNotification(SOMETHING_WENT_WRONG);
        }
    }

    private void returnCar() {
        if (!isCustomerRentedCar()) {
            view.showNotification(CLIENT_DIDN_T_RENT_A_CAR);
            return;
        }

        Boolean carReturned = rentalService.removeRentedCar(app.getActiveCustomer());

        if (carReturned) {
            view.showNotification(CLIENT_RETURNED_RENTED_CAR);
        }
    }

    private void showRentedCar() {
        if (!isCustomerRentedCar()) {
            view.showNotification(CLIENT_DIDN_T_RENT_A_CAR);
            return;
        }

        Car rentedCar = rentalService.getRentedCarByCustomer(app.getActiveCustomer());
        Company rentalCompany = rentalService.getCompanyByCar(rentedCar);

        String message = RENTED_CAR_TITLE +
                rentedCar.getName() + "\n" +
                COMPANY_TITLE +
                rentalCompany.getName() + "\n";

        view.showNotification(message);
    }

    private boolean isCustomerRentedCar() {
        return rentalService.isCustomerRentedCar(app.getActiveCustomer());
    }

    private void rentCar() {
        if (rentalService.isCustomerRentedCar(app.getActiveCustomer())) {
            view.showNotification(ALREADY_RENTED_A_CAR);
            return;
        }

        app.transitionForward(app.getChooseCompanyState());
    }

    private void moveBack() {
        app.transitionJumpTo(MainMenuState.class);
    }

    @Override
    public void display() {
        AppMenu customerInfoMenu = showCustomerInfoMenu();
        Command command = getUserCommand(customerInfoMenu);
        request(command);
    }

    private Command getUserCommand(AppMenu customerInfoMenu) {
        String userInput = view.readUserInput(customerInfoMenu);
        Command command = defineUserCommand(customerInfoMenu, userInput);
        return command;
    }

    private AppMenu showCustomerInfoMenu() {
        AppMenu customerInfoMenu = view.createMenu(customerInfoMenuItems);
        view.showMenu(customerInfoMenu);
        return customerInfoMenu;
    }
}
