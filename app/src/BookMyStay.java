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

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class InventoryService {
    private Map<String, Integer> availability = new HashMap<>();

    public void addRoom(String type, int count) {
        availability.put(type, count);
    }

    public int getAvailability(String type) throws InvalidBookingException {
        if (!availability.containsKey(type)) {
            throw new InvalidBookingException("Invalid room type: " + type);
        }
        return availability.get(type);
    }

    public void decrement(String type) throws InvalidBookingException {
        int current = getAvailability(type);
        if (current <= 0) {
            throw new InvalidBookingException("No availability for room type: " + type);
        }
        availability.put(type, current - 1);
    }
}

class BookingService {
    private InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void processReservation(Reservation reservation) {
        try {
            if (reservation.getGuestName() == null || reservation.getGuestName().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty");
            }

            String type = reservation.getRoomType();
            inventory.getAvailability(type);
            inventory.decrement(type);

            System.out.println("Booking confirmed for " + reservation.getGuestName() + " in " + type);
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 1);
        inventory.addRoom("Double", 0);

        BookingService bookingService = new BookingService(inventory);

        bookingService.processReservation(new Reservation("Alice", "Single"));
        bookingService.processReservation(new Reservation("Bob", "Double"));
        bookingService.processReservation(new Reservation("", "Single"));
        bookingService.processReservation(new Reservation("Charlie", "Suite"));
    }
}