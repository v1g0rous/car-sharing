package carsharing.state;

import carsharing.App;
import carsharing.util.Log;
import carsharing.view.AppMenu;
import carsharing.view.MenuItem;
import carsharing.view.View;

public abstract class State {
    public static final String SOMETHING_WENT_WRONG = "Something went wrong, please, try again\n";
    public static final String INCORRECT_USER_COMMAND = "Your input is incorrect, please, try again\n";
    App app;
    View view;

    abstract public void display();

    public void doInvalidCommandFlow() {
        view.showNotification(INCORRECT_USER_COMMAND);
    }

    public Command defineUserCommand(AppMenu menu, String labelNumber) {
        int labelNumberInt = -1;

        try {
            labelNumberInt = Integer.parseInt(labelNumber);
        } catch (NumberFormatException e) {
            new Log("Failed to define Command for userInput: " + labelNumber, e);
        }

        MenuItem item = AppMenu.getMenuItemByLabelNumber(labelNumberInt, menu);

        if (item != null) {
            return Command.getCommandByMenuItem(item.getApiName());
        } else {
            return Command.UNKNOWN_COMMAND;
        }
    }


}
