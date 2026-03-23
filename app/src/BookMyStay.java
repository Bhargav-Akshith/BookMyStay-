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

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public void displayRequests() {
        for (Reservation r : queue) {
            System.out.println("Guest: " + r.getGuestName() + ", Room Type: " + r.getRoomType());
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        bookingQueue.addRequest(new Reservation("Alice", "Single"));
        bookingQueue.addRequest(new Reservation("Bob", "Double"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite"));

        bookingQueue.displayRequests();
    }
}