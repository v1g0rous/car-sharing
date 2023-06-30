package carsharing.view;

import carsharing.util.Log;

import java.util.List;
import java.util.Scanner;

public interface View {
    void showMenu(AppMenu menu);
    String readUserInput(AppMenu menu);
    Boolean isInputValid(String userInput, AppMenu menu);
    default String getUserInput() {
        String userInput = "";
        try {
            Scanner scanner = new Scanner(System.in);
            userInput = scanner.nextLine();
        } catch (Exception e) {
            new Log("Invalid user input: userInput=" + userInput, e);
        }

        return userInput;
    }

    void showNotification(String notification);

    AppMenu createMenu(List<MenuItem> items);

    List<MenuItem> createMenuItems(List<String> items);


}
