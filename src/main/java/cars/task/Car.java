package cars.task;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode

public class Car {

    private String brand;
    private String model;
    private double price;
    private double mileage;
    private short variety;
    private short zasieg;


    public static List<Car> carsExtension = new ArrayList<>();

    public Car(String brand, String model, double price, double mileage) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.mileage = mileage;
        carsExtension.add(this);
    }

}
