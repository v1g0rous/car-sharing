package carsharing.strategy;

import carsharing.App;
import carsharing.entity.Car;
import carsharing.entity.Customer;
import carsharing.service.CarService;
import carsharing.service.RentalService;
import carsharing.state.Command;
import carsharing.util.Log;
import carsharing.view.AppMenu;
import carsharing.view.MenuItem;
import carsharing.view.View;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerStrategy implements UserStrategy {
    private static final String CHOOSE_CAR = "Choose a car:";
    public static final String INCORRECT_USER_COMMAND = "Your input is incorrect, please, try again\n";
    public static final String NO_AVAILABLE_CARS = "No available cars";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong, please, try again\n";

    App app;
    View view;
    CarService carService;
    RentalService rentalService;

    public CustomerStrategy(App app) {
        this.app = app;
        this.view = app.getView();
    }


    public Command defineChooseCarCommand(AppMenu carsMenu, String userInput) {
        int labelNumberInt = -1;

        try {
            labelNumberInt = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            new Log("Failed to define Command for userInput: " + userInput, e);
        }

        MenuItem item = AppMenu.getMenuItemByLabelNumber(labelNumberInt, carsMenu);

        if (item == null) {
            return Command.UNKNOWN_COMMAND;
        }

        return Command.getCommandByMenuItem(item.getApiName()) == Command.BACK ?
                Command.BACK :
                Command.SET_RENTED_CAR;
    }

    public void request(Command userCommand) {
        try {
            switch (userCommand) {
                case SET_RENTED_CAR -> app.transitionForward(app.getCustomerInfoState());
                case BACK -> app.transitionBack();
                case UNKNOWN_COMMAND -> doInvalidCommandFlow();
            }
        } catch (Exception e) {
            new Log("AppController failed", e);
            view.showNotification(SOMETHING_WENT_WRONG);
        }
    }

    private void doInvalidCommandFlow() {
        view.showNotification(INCORRECT_USER_COMMAND);
    }

    private boolean assignRentedCarToCustomer(Car car, Customer customer) {
        boolean carIsAssigned = rentalService.assignCarToCustomer(car, customer);
        return carIsAssigned;
    }

    private Car getRentedCar(AppMenu carsMenu, String userInput, CarService carService) {
        String carName = AppMenu.getMenuItemByLabelNumber(
                        Integer.parseInt(userInput), carsMenu).
                getLabel();

        return carService.getCarByName(carName);
    }


    @Override
    public void handleChooseCar(CarService carService, RentalService rentalService) {
        assignServices(carService, rentalService);

        String activeCompanyName = app.getActiveCompany().getName();
        List<Car> availableCars = carService.getAvailableCarsByCompanyName(activeCompanyName);

        if (availableCars == null || availableCars.size() == 0) {
            view.showNotification(NO_AVAILABLE_CARS + " in the " + "'" + activeCompanyName + "'" + " company");
            app.transitionBack();
            return;
        }

        AppMenu carsMenu = view.createMenu(createCarListMenuItems(view, availableCars));
        showChooseCarMenu(view, carsMenu);

        String userInput = view.readUserInput(carsMenu);
        Command command = defineChooseCarCommand(carsMenu, userInput);

        if (command == Command.SET_RENTED_CAR) {
            Car car = getRentedCar(carsMenu, userInput, carService);
            Customer customer = app.getActiveCustomer();
            boolean carIsAssigned = assignRentedCarToCustomer(car, customer);

            if (carIsAssigned) {
                view.showNotification("You rented " + "'" + car.getName() + "'\n");
            }
        }

        request(command);
    }

    private void assignServices(CarService carService, RentalService rentalService) {
        this.carService = carService;
        this.rentalService = rentalService;
    }


    private static void showChooseCarMenu(View view, AppMenu carsMenu) {
        System.out.println(CHOOSE_CAR);
        view.showMenu(carsMenu);
        System.out.println();
    }

    private static List<MenuItem> createCarListMenuItems(View view, List<Car> carList) {
        List<String> carNames = carList.stream().map(Car::getName).collect(Collectors.toList());
        List<MenuItem> items = view.createMenuItems(carNames);
        MenuItem back = new MenuItem("Back", "back", items.size() + 1, 0);
        items.add(back);

        return items;
    }
}
