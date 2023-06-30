package carsharing;

import carsharing.DAO.AppDAOImpl;
import carsharing.entity.Company;
import carsharing.entity.Customer;
import carsharing.service.*;
import carsharing.state.*;
import carsharing.strategy.UserStrategy;
import carsharing.util.Log;
import carsharing.view.*;

import java.util.Stack;


public class App {
    View view;
    private State mainMenuState;
    private State managerMenuState;
    private State chooseCompanyState;
    private State companyInfoState;
    private State createCompanyState;
    private State chooseCarState;
    private State createCarState;
    private State chooseCustomerState;
    private State createCustomerState;
    private State customerInfoState;
    private State activeState;
    private UserStrategy userStrategy;
    private Company activeCompany;
    private Customer activeCustomer;
    private Stack<State> stateHistory;


    public App(String[] args) {
        initDatabase(args);
        this.view = new AppView();

        this.mainMenuState = new MainMenuState(this);
        this.managerMenuState = new ManagerMenuState(this);

        CompanyService companyService = new CompanyServiceImpl();
        this.createCompanyState = new CreateCompanyState(this, companyService);
        this.chooseCompanyState = new ChooseCompanyState(this, companyService);

        CustomerService customerService = new CustomerServiceImpl();
        this.chooseCustomerState = new ChooseCustomerState(this, customerService);
        this.createCustomerState = new CreateCustomerState(this, customerService);

        CarService carService = new CarServiceImpl();
        RentalService rentalService = new RentalServiceImpl();

        this.customerInfoState = new CustomerInfoState(this, customerService, rentalService);
        this.companyInfoState = new CompanyInfoState(this, carService);
        this.chooseCarState = new ChooseCarState(this, carService, rentalService);
        this.createCarState = new CreateCarState(this, carService);


        this.activeState = mainMenuState;
        this.stateHistory = new Stack<>();
        stateHistory.push(activeState);
    }



    public void transitionForward(State state) {
        this.activeState = state;

        stateHistory.push(state);
    }

    public void transitionBack() { // for linear state changes: 1,2,3 => 3, 2, 1
        if (!this.stateHistory.isEmpty()) {
            this.stateHistory.pop();
            if (!this.stateHistory.isEmpty()) {
                activeState = this.stateHistory.peek();
                return;
            }
        }

        activeState = this.mainMenuState;
    }

    // for non-linear state changes for special situations: 1,2,3,4,5 => 5,2
    public void transitionJumpTo(Class<? extends State> stateClass) {
        while (!this.stateHistory.isEmpty() && !this.stateHistory.peek().getClass().equals(stateClass)) {
            this.stateHistory.pop();
        }

        activeState = stateHistory.peek();
    }

    public View getView() {
        return view;
    }

    public void run() {
        while (true) {
            activeState.display();
        }
    }

    public UserStrategy getUserStrategy() {
        return userStrategy;
    }

    public void setUserStrategy(UserStrategy userStrategy) {
        this.userStrategy = userStrategy;
    }

    public Company getActiveCompany() {
        return activeCompany;
    }

    public void setActiveCompany(Company activeCompany) {
        this.activeCompany = activeCompany;
    }

    private void initDatabase(String[] args) {
        new AppDAOImpl().initDatabase(args);
    }

    public void terminate() {
        try {
            System.exit(0);
        } catch (Exception e) {
            new Log("Failed to terminate app", e);
        }
    }

    public State getManagerMenuState() {
        return managerMenuState;
    }

    public State getChooseCompanyState() {
        return chooseCompanyState;
    }

    public State getCompanyInfoState() {
        return companyInfoState;
    }

    public State getCreateCompanyState() {
        return createCompanyState;
    }

    public State getChooseCarState() {
        return chooseCarState;
    }

    public State getCreateCarState() {
        return createCarState;
    }

    public State getChooseCustomerState() {
        return chooseCustomerState;
    }

    public State getCreateCustomerState() {
        return createCustomerState;
    }

    public Customer getActiveCustomer() {
        return activeCustomer;
    }

    public void setActiveCustomer(Customer activeCustomer) {
        this.activeCustomer = activeCustomer;
    }

    public State getCustomerInfoState() {
        return customerInfoState;
    }



}
