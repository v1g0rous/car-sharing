package carsharing.strategy;

import carsharing.App;
import carsharing.entity.Car;
import carsharing.service.CarService;
import carsharing.service.RentalService;
import carsharing.view.AppMenu;
import carsharing.view.MenuItem;
import carsharing.view.View;

import java.util.List;
import java.util.stream.Collectors;

public class ManagerStrategy implements UserStrategy {
    public static final String CAR_LIST_IS_EMPTY = "The car list is empty!\n";
    public static final String CAR_LIST_TITLE = "Car list:";

    App app;
    View view;


    public ManagerStrategy(App app) {
        this.app = app;
        this.view = app.getView();
    }

    @Override
    public void handleChooseCar(CarService carService, RentalService rentalService) {
        String activeCompanyName = app.getActiveCompany().getName();
        List<Car> carList = carService.getAllCarsByCompanyName(activeCompanyName);

        if (isListEmpty(app, view, carList)) return;

        AppMenu carsMenu = view.createMenu(createCarListMenuItems(view, carList));
        showCarsMenu(view, carsMenu);

        app.transitionBack();
    }

    private static boolean isListEmpty(App app, View view, List<Car> carList) {
        if (carList == null || carList.size() == 0) {
            view.showNotification(CAR_LIST_IS_EMPTY);
            app.transitionBack();
            return true;
        }
        return false;
    }

    private static void showCarsMenu(View view, AppMenu carsMenu) {
        System.out.println(CAR_LIST_TITLE);
        view.showMenu(carsMenu);
        System.out.println();
    }

    private static List<MenuItem> createCarListMenuItems(View view, List<Car> carList) {
        List<String> carNames = carList.stream().map(Car::getName).collect(Collectors.toList());
        List<MenuItem> items = view.createMenuItems(carNames);
        return items;
    }
}
