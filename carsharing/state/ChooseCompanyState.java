package carsharing.state;

import carsharing.App;
import carsharing.entity.Company;
import carsharing.service.CompanyService;
import carsharing.strategy.CustomerStrategy;
import carsharing.strategy.ManagerStrategy;
import carsharing.util.Log;
import carsharing.view.AppMenu;
import carsharing.view.MenuItem;

import java.util.List;
import java.util.stream.Collectors;

public class ChooseCompanyState extends State{
    public static final String CHOOSE_COMPANY = "Choose the company:";
    CompanyService companyService;
    public static final String COMPANY_LIST_IS_EMPTY = "The company list is empty!";

    public ChooseCompanyState(App app, CompanyService companyService) {
        this.app = app;
        this.view = app.getView();
        this.companyService = companyService;
    }

    public void request(Command userCommand) {
        try {
            switch (userCommand) {
                case SHOW_COMPANY_INFO -> app.transitionForward(app.getCompanyInfoState()); // for manager
                case SHOW_CAR_LIST_FOR_RENT -> chooseCarForRent(); // for customer
                case BACK -> app.transitionBack();
                case UNKNOWN_COMMAND -> doInvalidCommandFlow();
            }
        } catch (Exception e) {
            new Log("AppController failed", e);
            view.showNotification(SOMETHING_WENT_WRONG);
        }
    }

    private void chooseCarForRent() {
        app.transitionForward(app.getChooseCarState());
    }

    @Override
    public void display() {
        List<Company> companyList = companyService.getAllCompanies();

        if (isListEmpty(companyList)) return;

        AppMenu companiesMenu = showMenu(companyList);
        String userInput = view.readUserInput(companiesMenu);

        // different behavior based on user type
        Command command = defineUserCommand(companiesMenu, userInput);

        if (command != Command.BACK) {
            setActiveCompany(companiesMenu, userInput);
        }

        request(command);
    }

    @Override
    public Command defineUserCommand(AppMenu menu, String labelNumber) {
        Command resultCommand = null;
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

        // different behavior depending on User strategy
        if (app.getUserStrategy() instanceof ManagerStrategy) {
            resultCommand = Command.SHOW_COMPANY_INFO;
        } else if (app.getUserStrategy() instanceof CustomerStrategy) {
            resultCommand = Command.SHOW_CAR_LIST_FOR_RENT;
        }

        return Command.getCommandByMenuItem(item.getApiName()) == Command.BACK ?
                Command.BACK :
                resultCommand;
    }


    private AppMenu showMenu(List<Company> companyList) {
        AppMenu companiesMenu = view.createMenu(addMenuItems(companyList));
        System.out.println(CHOOSE_COMPANY);
        view.showMenu(companiesMenu);
        return companiesMenu;
    }

    private boolean isListEmpty(List<Company> companyList) {
        if (companyList == null || companyList.size() == 0) {
            view.showNotification(COMPANY_LIST_IS_EMPTY);
            app.transitionBack();
            return true;
        }
        return false;
    }

    private void setActiveCompany(AppMenu companiesMenu, String userInput) {
        String companyName = AppMenu.getMenuItemByLabelNumber(
                Integer.parseInt(userInput), companiesMenu).
                getLabel();

        Company activeCompany = companyService.getCompanyByName(companyName);
        app.setActiveCompany(activeCompany);
    }


    private List<MenuItem> addMenuItems(List<Company> companyList) {
        List<String> companyNames = companyList.stream().map(Company::getName).collect(Collectors.toList());

        List<MenuItem> items = view.createMenuItems(companyNames);
        MenuItem back = new MenuItem("Back", "back", items.size() + 1, 0);
        items.add(back);

        return items;
    }


}
