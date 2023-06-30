package carsharing.state;

public enum Command { // controller fires commands based on MenuItem apiName

    LOGIN_AS_MANAGER("loginAsManager"),
    LOGIN_AS_CUSTOMER("loginAsCustomer"),
    SHOW_COMPANY_LIST("showCompanyList"),
    SHOW_CUSTOMER_LIST("showCustomerList"),
    SHOW_COMPANY_INFO("showCompanyInfo"),
    SHOW_CUSTOMER_INFO("showCustomerInfo"),
    CREATE_COMPANY("createCompany"),
    CREATE_CUSTOMER("createCustomer"),
    CREATE_CAR("createCar"),
    RENT_CAR("rentCar"),
    RETURN_RENTED_CAR("returnRentedCar"),
    SET_RENTED_CAR("setRentedCar"),
    SHOW_CAR_LIST_FOR_RENT("showCarList"),
    MY_RENTED_CAR("myRentedCar"),
    EXIT_PROGRAM("exitProgram"),
    LOGOUT("logOut"),
    BACK("back"),
    UNKNOWN_COMMAND("unknownCommand");

    private final String apiName;


    Command(String apiName) {
        this.apiName = apiName;
    }

    public static Command getCommandByMenuItem(String apiName) {
        Command resultCommand = UNKNOWN_COMMAND;

        if (apiName == null) return resultCommand;

        for (Command cm : Command.values()) {
            if (cm.apiName.equals(apiName)) {
                resultCommand = cm;
            }
        }

        return resultCommand;
    }
}
