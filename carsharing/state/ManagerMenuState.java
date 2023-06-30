package carsharing.state;

import carsharing.App;
import carsharing.util.Log;
import carsharing.view.AppMenu;
import carsharing.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ManagerMenuState extends State {
    private List<MenuItem> managerMenuItems = new ArrayList<>(List.of(
            new MenuItem("Company list", "showCompanyList", 1, 1),
            new MenuItem("Create a company", "createCompany", 2, 2),
            new MenuItem("Back", "back", 3, 0)
    ));

    public ManagerMenuState(App app) {
        this.app = app;
        this.view = app.getView();
    }

    @Override
    public void display() {

        AppMenu mainMenu = view.createMenu(managerMenuItems);
        view.showMenu(mainMenu);

        String userInput = view.readUserInput(mainMenu);
        Command command = defineUserCommand(mainMenu, userInput);

        request(command);
    }

    public void request(Command command) {
        try {
            switch (command) {
                case SHOW_COMPANY_LIST -> app.transitionForward(app.getChooseCompanyState());
                case CREATE_COMPANY -> app.transitionForward(app.getCreateCompanyState());
                case BACK -> app.transitionBack();
                case UNKNOWN_COMMAND -> doInvalidCommandFlow();
            }
        } catch (Exception e) {
            new Log("ManagerMenuState failed", e);
            view.showNotification(SOMETHING_WENT_WRONG);
        }
    }


}
