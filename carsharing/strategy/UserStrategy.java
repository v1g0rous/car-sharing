package carsharing.strategy;

import carsharing.service.CarService;
import carsharing.service.RentalService;

public interface UserStrategy {
    void handleChooseCar(CarService carService, RentalService rentalService);
}
