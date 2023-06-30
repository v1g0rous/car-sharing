package carsharing.state;

import carsharing.App;
import carsharing.service.CarService;
import carsharing.util.Log;
import carsharing.view.AppMenu;
import carsharing.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class CompanyInfoState extends State {
    private List<MenuItem> companyInfoMenuItems = new ArrayList<>(List.of(
            new MenuItem("Car list", "showCarList", 1, 1),
            new MenuItem("Create a car", "createCar", 2, 2),
            new MenuItem("Back", "back", 3, 0)
    ));

    CarService carService;

    public CompanyInfoState(App app, CarService carService) {
        this.app = app;
        this.view = app.getView();
        this.carService = carService;
    }

    @Override
    public void display() {
        System.out.printf("\'%s\' company\n",app.getActiveCompany().getName());
        AppMenu companyInfoMenu = view.createMenu(companyInfoMenuItems);
        view.showMenu(companyInfoMenu);

        String userInput = view.readUserInput(companyInfoMenu);
        Command command = defineUserCommand(companyInfoMenu, userInput);

        request(command);
    }

    public void request(Command userCommand) {
        try {
            switch (userCommand) {
                case SHOW_CAR_LIST_FOR_RENT -> app.transitionForward(app.getChooseCarState());
                case CREATE_CAR -> app.transitionForward(app.getCreateCarState());
                case BACK -> moveBack();
                case UNKNOWN_COMMAND -> doInvalidCommandFlow();
            }
        } catch (Exception e) {
            new Log("AppController failed", e);
            view.showNotification(SOMETHING_WENT_WRONG);
        }
    }

    private void moveBack() {
        app.transitionJumpTo(ManagerMenuState.class);
    }

}
