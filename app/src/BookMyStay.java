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

    public InventoryService() {
        availability.put("Single", 2);
        availability.put("Double", 1);
    }

    public synchronized boolean allocate(String type) {
        int count = availability.getOrDefault(type, 0);
        if (count > 0) {
            availability.put(type, count - 1);
            return true;
        }
        return false;
    }
}

class BookingProcessor implements Runnable {
    private Queue<Reservation> queue;
    private InventoryService inventory;

    public BookingProcessor(Queue<Reservation> queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            Reservation r;

            synchronized (queue) {
                if (queue.isEmpty()) {
                    break;
                }
                r = queue.poll();
            }

            if (r != null) {
                boolean success = inventory.allocate(r.getRoomType());
                if (success) {
                    System.out.println(Thread.currentThread().getName() + " confirmed " + r.getGuestName() + " for " + r.getRoomType());
                } else {
                    System.out.println(Thread.currentThread().getName() + " failed for " + r.getGuestName() + " (" + r.getRoomType() + ")");
                }
            }
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        Queue<Reservation> queue = new LinkedList<>();

        queue.offer(new Reservation("Alice", "Single"));
        queue.offer(new Reservation("Bob", "Single"));
        queue.offer(new Reservation("Charlie", "Single"));
        queue.offer(new Reservation("David", "Double"));
        queue.offer(new Reservation("Eve", "Double"));

        InventoryService inventory = new InventoryService();

        Thread t1 = new Thread(new BookingProcessor(queue, inventory), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(queue, inventory), "Thread-2");
        Thread t3 = new Thread(new BookingProcessor(queue, inventory), "Thread-3");

        t1.start();
        t2.start();
        t3.start();
    }
}