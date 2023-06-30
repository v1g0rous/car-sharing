package carsharing.state;

import carsharing.App;
import carsharing.entity.Customer;
import carsharing.service.CustomerService;
import carsharing.util.Log;
import carsharing.view.AppMenu;
import carsharing.view.MenuItem;

import java.util.List;
import java.util.stream.Collectors;

public class ChooseCustomerState extends State {

    public static final String CUSTOMER_LIST_IS_EMPTY = "The customer list is empty!";
    public static final String CUSTOMER_LIST_TITLE = "Customer list:";
    CustomerService customerService;

    public ChooseCustomerState(App app, CustomerService customerService) {
        this.app = app;
        this.view = app.getView();
        this.customerService = customerService;
    }

    public void request(Command userCommand) {
        try {
            switch (userCommand) {
                case SHOW_CUSTOMER_INFO -> app.transitionForward(app.getCustomerInfoState());
                case BACK -> app.transitionBack();
                case UNKNOWN_COMMAND -> doInvalidCommandFlow();
            }
        } catch (Exception e) {
            new Log("AppController failed", e);
            view.showNotification(SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public void display() {
        List<Customer> customerList = customerService.getAllCustomers();

        if (customerList == null || customerList.size() == 0) {
            view.showNotification(CUSTOMER_LIST_IS_EMPTY);
            app.transitionBack();
            return;
        }

        AppMenu customersMenu = showMenu(customerList);
        String userInput = view.readUserInput(customersMenu);
        Command command = defineUserCommand(customersMenu, userInput);

        if (command == Command.SHOW_CUSTOMER_INFO) {
            setActiveCustomer(customersMenu, userInput);
        }

        request(command);
    }

    private void setActiveCustomer(AppMenu customersMenu, String userInput) {

        String customerName = AppMenu.getMenuItemByLabelNumber(
                        Integer.parseInt(userInput), customersMenu).
                getLabel();

        loginAsCustomer(customerName);

    }


    private AppMenu showMenu(List<Customer> customerList) {
        AppMenu customersMenu = view.createMenu(addMenuItems(customerList));
        System.out.println(CUSTOMER_LIST_TITLE);
        view.showMenu(customersMenu);
        return customersMenu;
    }



    private void loginAsCustomer(String customerName) {
        app.setActiveCustomer(customerService.getCustomerByName(customerName));
    }

    @Override
    public Command defineUserCommand(AppMenu menu, String labelNumber) {
        int labelNumberInt = -1;

        try {
            labelNumberInt = Integer.parseInt(labelNumber);
        } catch (NumberFormatException e) {
            new Log("Failed to define Command for userInput: " + labelNumber, e);
        }

        MenuItem item = AppMenu.getMenuItemByLabelNumber(labelNumberInt, menu);

        if (item == null) {
            return Command.UNKNOWN_COMMAND;
        }

        return Command.getCommandByMenuItem(item.getApiName()) == Command.BACK ?
                Command.BACK :
                Command.SHOW_CUSTOMER_INFO;
    }

    private List<MenuItem> addMenuItems(List<Customer> customerList) {
        List<String> customerNames = customerList.stream().map(Customer::getName).collect(Collectors.toList());

        List<MenuItem> items = view.createMenuItems(customerNames);
        MenuItem back = new MenuItem("Back", "back", items.size() + 1, 0);
        items.add(back);

        return items;
    }


}
