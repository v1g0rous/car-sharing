package carsharing.state;

import carsharing.App;
import carsharing.strategy.CustomerStrategy;
import carsharing.strategy.ManagerStrategy;
import carsharing.util.Log;
import carsharing.view.AppMenu;
import carsharing.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainMenuState extends State {
    private List<MenuItem> mainMenuItems = new ArrayList<>(List.of(
            new MenuItem("Log in as a manager", "loginAsManager", 1, 1),
            new MenuItem("Log in as a customer", "loginAsCustomer", 2, 2),
            new MenuItem("Create a customer", "createCustomer", 3, 3),
            new MenuItem("Exit", "exitProgram", 4, 0)
    ));

    public MainMenuState(App app) {
        this.app = app;
        this.view = app.getView();
    }

    @Override
    public void display() {
        AppMenu mainMenu = view.createMenu(mainMenuItems);
        view.showMenu(mainMenu);

        String userInput = view.readUserInput(mainMenu);
        Command command = defineUserCommand(mainMenu, userInput);

        request(command);
    }

    public void request(Command userCommand) {
        try {
            switch (userCommand) {
                case LOGIN_AS_MANAGER -> loginAsManager();
                case LOGIN_AS_CUSTOMER -> loginAsCustomer();
                case CREATE_CUSTOMER -> app.transitionForward(app.getCreateCustomerState());
                case EXIT_PROGRAM -> app.terminate();
                case UNKNOWN_COMMAND -> doInvalidCommandFlow();
            }
        } catch (Exception e) {
            new Log("MainMenuState failed", e);
            view.showNotification(SOMETHING_WENT_WRONG);
        }
    }

    private void loginAsCustomer() {
        app.setUserStrategy(new CustomerStrategy(app));
        app.transitionForward(app.getChooseCustomerState());
    }

    private void loginAsManager() {
        app.setUserStrategy(new ManagerStrategy(app));
        app.transitionForward(app.getManagerMenuState());
    }

}
