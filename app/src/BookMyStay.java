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

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

class BookingService {
    private Queue<Reservation> queue;
    private InventoryService inventory;
    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> roomAllocations = new HashMap<>();
    private int idCounter = 1;

    public BookingService(Queue<Reservation> queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    private String generateRoomId(String type) {
        String id;
        do {
            id = type.substring(0, 1).toUpperCase() + idCounter++;
        } while (allocatedRoomIds.contains(id));
        return id;
    }

    public void processBookings() {
        while (!queue.isEmpty()) {
            Reservation r = queue.poll();
            String type = r.getRoomType();

            if (inventory.getAvailability(type) > 0) {
                String roomId = generateRoomId(type);
                allocatedRoomIds.add(roomId);

                roomAllocations.putIfAbsent(type, new HashSet<>());
                roomAllocations.get(type).add(roomId);

                inventory.decrement(type);

                System.out.println("Confirmed: " + r.getGuestName() + " -> " + type + " Room ID: " + roomId);
            } else {
                System.out.println("Failed: " + r.getGuestName() + " -> No availability for " + type);
            }
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 2);
        inventory.addRoom("Double", 1);

        Queue<Reservation> queue = new LinkedList<>();
        queue.offer(new Reservation("Alice", "Single"));
        queue.offer(new Reservation("Bob", "Single"));
        queue.offer(new Reservation("Charlie", "Single"));
        queue.offer(new Reservation("David", "Double"));

        BookingService bookingService = new BookingService(queue, inventory);
        bookingService.processBookings();
    }
}