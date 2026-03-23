/**
 * ==============================================================
 * MAIN CLASS – UseCase1HotelBookingApp
 * ==============================================================
 *
 * Use Case 1: Application Entry & Welcome Message
 *
 * Description:
 * This class represents the entry point of the
 * Hotel Booking Management System.
 *
 * At this stage, the application:
 * - Starts execution from the main() method
 * - Displays a welcome message to the user
 * - Confirms that the system has started successfully
 *
 * No business logic, data structures, or user input
 * is implemented in this use case.
 *
 * The goal is to establish a clear and predictable
 * application startup point.
 *
 * @author Developer
 * @version 1.0
 */
import java.util.*;

class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {
    private Map<String, List<Service>> serviceMap = new HashMap<>();

    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    public double calculateTotalCost(String reservationId) {
        double total = 0;
        List<Service> services = serviceMap.getOrDefault(reservationId, new ArrayList<>());
        for (Service s : services) {
            total += s.getCost();
        }
        return total;
    }

    public void displayServices(String reservationId) {
        List<Service> services = serviceMap.getOrDefault(reservationId, new ArrayList<>());
        for (Service s : services) {
            System.out.println("Service: " + s.getName() + ", Cost: " + s.getCost());
        }
    }
}

public class BookMyStay{
    public static void main(String[] args) {
        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId1 = "R101";
        String reservationId2 = "R102";

        manager.addService(reservationId1, new Service("Breakfast", 200));
        manager.addService(reservationId1, new Service("Airport Pickup", 500));
        manager.addService(reservationId2, new Service("Extra Bed", 300));

        System.out.println("Services for " + reservationId1);
        manager.displayServices(reservationId1);
        System.out.println("Total Cost: " + manager.calculateTotalCost(reservationId1));

        System.out.println("Services for " + reservationId2);
        manager.displayServices(reservationId2);
        System.out.println("Total Cost: " + manager.calculateTotalCost(reservationId2));
    }
}