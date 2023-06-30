package carsharing.state;

import carsharing.App;
import carsharing.service.CustomerService;
import carsharing.util.Log;

public class CreateCustomerState extends State {
    public static final String ENTER_CUSTOMER_NAME = "Enter the customer name:";
    public static final String CUSTOMER_WAS_ADDED = "The customer was added!";
    CustomerService customerService;

    public CreateCustomerState(App app, CustomerService customerService) {
        this.app = app;
        this.view = app.getView();
        this.customerService = customerService;
    }


    @Override
    public void display() {
        createCustomer();
        app.transitionBack();
    }

    public void createCustomer() {
        try {
            view.showNotification(ENTER_CUSTOMER_NAME);
            String name = view.getUserInput();

            boolean customerCreated = customerService.createNewCustomer(name);

            view.showNotification(customerCreated ? CUSTOMER_WAS_ADDED : SOMETHING_WENT_WRONG);
        } catch (Exception e) {
            view.showNotification(SOMETHING_WENT_WRONG);
            new Log("Error: unable to createCompany", e);
        }
    }
}
