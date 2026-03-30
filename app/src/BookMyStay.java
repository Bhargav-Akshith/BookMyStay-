import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

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
import java.io.*;
import java.util.*;

class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    String bookingId;
    String hotelName;
    int roomsBooked;

    Booking(String bookingId, String hotelName, int roomsBooked) {
        this.bookingId = bookingId;
        this.hotelName = hotelName;
        this.roomsBooked = roomsBooked;
    }

    public String toString() {
        return bookingId + " | " + hotelName + " | Rooms: " + roomsBooked;
    }
}

class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
    Map<String, Integer> hotelRooms = new HashMap<>();

    void addHotel(String name, int rooms) {
        hotelRooms.put(name, rooms);
    }

    boolean bookRooms(String name, int count) {
        if (!hotelRooms.containsKey(name)) return false;
        int available = hotelRooms.get(name);
        if (available < count) return false;
        hotelRooms.put(name, available - count);
        return true;
    }

    public String toString() {
        return hotelRooms.toString();
    }
}

class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;
    List<Booking> bookings;
    Inventory inventory;

    SystemState(List<Booking> bookings, Inventory inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "system_state.dat";

    static void save(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("State saved successfully");
        } catch (Exception e) {
            System.out.println("Error saving state");
        }
    }

    static SystemState load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("State loaded successfully");
            return (SystemState) ois.readObject();
        } catch (Exception e) {
            System.out.println("No valid saved state found, starting fresh");
            return null;
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        List<Booking> bookings;
        Inventory inventory;

        SystemState state = PersistenceService.load();

        if (state != null) {
            bookings = state.bookings;
            inventory = state.inventory;
        } else {
            bookings = new ArrayList<>();
            inventory = new Inventory();
            inventory.addHotel("HotelA", 10);
            inventory.addHotel("HotelB", 5);
        }

        if (inventory.bookRooms("HotelA", 2)) {
            bookings.add(new Booking(UUID.randomUUID().toString(), "HotelA", 2));
        }

        if (inventory.bookRooms("HotelB", 1)) {
            bookings.add(new Booking(UUID.randomUUID().toString(), "HotelB", 1));
        }

        System.out.println("Current Inventory: " + inventory);
        System.out.println("Bookings:");
        for (Booking b : bookings) {
            System.out.println(b);
        }

        SystemState newState = new SystemState(bookings, inventory);
        PersistenceService.save(newState);
    }
}import java.util.*;
