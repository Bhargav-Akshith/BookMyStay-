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
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(history);
    }
}

class BookingReportService {
    public void displayAllBookings(List<Reservation> reservations) {
        for (Reservation r : reservations) {
            System.out.println("ID: " + r.getReservationId() + ", Guest: " + r.getGuestName() + ", Room: " + r.getRoomType());
        }
    }

    public void displaySummary(List<Reservation> reservations) {
        Map<String, Integer> countMap = new HashMap<>();
        for (Reservation r : reservations) {
            countMap.put(r.getRoomType(), countMap.getOrDefault(r.getRoomType(), 0) + 1);
        }
        for (String type : countMap.keySet()) {
            System.out.println("Room Type: " + type + ", Bookings: " + countMap.get(type));
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();

        history.addReservation(new Reservation("R101", "Alice", "Single"));
        history.addReservation(new Reservation("R102", "Bob", "Double"));
        history.addReservation(new Reservation("R103", "Charlie", "Single"));

        BookingReportService reportService = new BookingReportService();

        System.out.println("All Bookings:");
        reportService.displayAllBookings(history.getAllReservations());

        System.out.println("Summary Report:");
        reportService.displaySummary(history.getAllReservations());
    }
}