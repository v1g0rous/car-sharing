package carsharing.view;

import carsharing.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppView implements View {

    public static final String INCORRECT_USER_COMMAND = "Your input is incorrect, please, try again";
    public static final String UNKNOWN_USER_INPUT = "UNKNOWN_USER_INPUT";

    public AppView() {
    }


    // check if userInput is valid for shown menu
    @Override
    public Boolean isInputValid(String userInput, AppMenu menu) {
        if (userInput == null || userInput.isEmpty()) return false;

        Boolean validInput = false;

        try {
            List<MenuItem> items = menu.getMenuItems();

            String regex = prepareMenuRegex(items);

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(userInput);
            if (matcher.find()) { // check if userInput is within range of menu item numbers
                validInput = true;
            }
        } catch (Exception e) {
            new Log("Failed to check validity of user input: " + userInput + " for AppMenu", e );
        }

        return validInput;
    }




    private String prepareMenuRegex(List<MenuItem> items) {
        StringBuilder regex = new StringBuilder();
        int lastIndex = items.size() - 1;
        for (int i = 0; i < items.size(); i++) {
            int labelNumber = items.get(i).getLabelNumber();
            regex.append("^" + labelNumber + "$");
            regex.append(i == lastIndex ? "" : "|");
        }

        return regex.toString();
    }

    public String readUserInput(AppMenu menu) {

        String labelNumber = this.getUserInput();
        Boolean validInput = this.isInputValid(labelNumber, menu);

        return validInput ? labelNumber : null;
    }

    @Override
    public void showNotification(String notification) {
        System.out.println(notification);
    }


    public void showMenu(AppMenu menu) {
        menu.show();
    }



    public AppMenu createMenu(List<MenuItem> items) {
        return new Menu(items);
    }

    @Override
    public List<MenuItem> createMenuItems(List<String> items) {
        List<MenuItem> menuItems = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            MenuItem mi = new MenuItem(item, item, i, i + 1);
            menuItems.add(mi);
        }

        return menuItems;
    }


}
