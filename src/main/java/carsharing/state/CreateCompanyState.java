package carsharing.state;

import carsharing.App;
import carsharing.service.CompanyService;
import carsharing.util.Log;

public class CreateCompanyState extends State {
    public static final String ENTER_THE_COMPANY_NAME = "Enter the company name:";
    public static final String COMPANY_WAS_CREATED = "The company was created!\n";
    CompanyService companyService;

    public CreateCompanyState(App app, CompanyService companyService) {
        this.app = app;
        this.view = app.getView();
        this.companyService = companyService;
    }

    @Override
    public void display() {
        createCompany();
        app.transitionBack();
    }


    public void createCompany() {
        try {
            view.showNotification(ENTER_THE_COMPANY_NAME);
            String name = view.getUserInput();

            boolean companyCreated = companyService.createNewCompany(name);

            view.showNotification(companyCreated ? COMPANY_WAS_CREATED : SOMETHING_WENT_WRONG);
        } catch (Exception e) {
            view.showNotification(SOMETHING_WENT_WRONG);
            new Log("Error: unable to createCompany", e);
        }
    }
}
