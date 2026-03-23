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

class Room {
    private String type;
    private double price;
    private List<String> amenities;

    public Room(String type, double price, List<String> amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getAmenities() {
        return amenities;
    }
}

class Inventory {
    private Map<String, Integer> availability = new HashMap<>();

    public void addRoom(String type, int count) {
        availability.put(type, count);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        return Collections.unmodifiableMap(availability);
    }
}

class SearchService {
    private Inventory inventory;
    private Map<String, Room> roomMap;

    public SearchService(Inventory inventory, Map<String, Room> roomMap) {
        this.inventory = inventory;
        this.roomMap = roomMap;
    }

    public void searchAvailableRooms() {
        Map<String, Integer> data = inventory.getAllAvailability();
        for (String type : data.keySet()) {
            int count = data.get(type);
            if (count > 0 && roomMap.containsKey(type)) {
                Room room = roomMap.get(type);
                System.out.println("Room Type: " + room.getType());
                System.out.println("Price: " + room.getPrice());
                System.out.println("Amenities: " + room.getAmenities());
                System.out.println("Available: " + count);
                System.out.println("---------------------------");
            }
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory.addRoom("Single", 3);
        inventory.addRoom("Double", 0);
        inventory.addRoom("Suite", 2);

        Map<String, Room> rooms = new HashMap<>();
        rooms.put("Single", new Room("Single", 1000, Arrays.asList("WiFi", "TV")));
        rooms.put("Double", new Room("Double", 2000, Arrays.asList("WiFi", "TV", "AC")));
        rooms.put("Suite", new Room("Suite", 5000, Arrays.asList("WiFi", "TV", "AC", "Mini Bar")));

        SearchService searchService = new SearchService(inventory, rooms);
        searchService.searchAvailableRooms();
    }
}