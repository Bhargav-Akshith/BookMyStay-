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

class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}

class InventoryService {
    private Map<String, Integer> availability = new HashMap<>();

    public void addRoom(String type, int count) {
        availability.put(type, count);
    }

    public void increment(String type) {
        availability.put(type, availability.getOrDefault(type, 0) + 1);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }
}

class BookingHistory {
    private Map<String, Reservation> confirmed = new HashMap<>();
    private Set<String> cancelled = new HashSet<>();

    public void addReservation(Reservation r) {
        confirmed.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return confirmed.get(id);
    }

    public void markCancelled(String id) {
        cancelled.add(id);
    }

    public boolean isCancelled(String id) {
        return cancelled.contains(id);
    }
}

class CancellationService {
    private InventoryService inventory;
    private BookingHistory history;
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(InventoryService inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancel(String reservationId) {
        Reservation r = history.getReservation(reservationId);

        if (r == null) {
            System.out.println("Cancellation failed: Reservation not found");
            return;
        }

        if (history.isCancelled(reservationId)) {
            System.out.println("Cancellation failed: Already cancelled");
            return;
        }

        rollbackStack.push(r.getRoomId());
        inventory.increment(r.getRoomType());
        history.markCancelled(reservationId);

        System.out.println("Cancelled: " + reservationId + " Room Released: " + r.getRoomId());
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 0);
        inventory.addRoom("Double", 1);

        BookingHistory history = new BookingHistory();
        history.addReservation(new Reservation("R101", "Single", "S1"));
        history.addReservation(new Reservation("R102", "Double", "D1"));

        CancellationService service = new CancellationService(inventory, history);

        service.cancel("R101");
        service.cancel("R101");
        service.cancel("R999");
    }
}