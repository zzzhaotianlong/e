import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class BikeService {
    private Deque<ERyderLog> systemLogStack = new ArrayDeque<>();
    private Queue<BikeRequest> bikeRequestQueue = new ArrayDeque<>();

    public void viewSystemLogs() {
        if (systemLogStack.isEmpty()) {
            System.out.println("No system logs available.");
            return;
        }
        System.out.println("\n=== System Logs ===");
        for (ERyderLog log : systemLogStack) {
            System.out.println(log);
        }
    }

    public void rentBikeSuccess(String bikeId, String location) {
        String logId = "BR" + bikeId;
        String eventDesc = "Bike with bikeID " + bikeId + " was rented from location " + location;
        ERyderLog log = new ERyderLog(logId, eventDesc, LocalDateTime.now());
        systemLogStack.push(log); 
    }

    public void tripStart(String bikeId) {
        String logId = "TS" + bikeId;
        String eventDesc = "Trip started for bike " + bikeId;
        ERyderLog log = new ERyderLog(logId, eventDesc, LocalDateTime.now());
        systemLogStack.push(log);
    }

    public void removeTrip(String bikeId, String location) {
        String logId = "TE" + bikeId;
        String eventDesc = "Trip ended for bike " + bikeId;
        ERyderLog log = new ERyderLog(logId, eventDesc, LocalDateTime.now());
        systemLogStack.push(log);

        if (!bikeRequestQueue.isEmpty()) {
            BikeRequest nextRequest = bikeRequestQueue.poll(); 
            System.out.println("\nAssigning bike " + bikeId + " to waiting user: " + nextRequest.getUserEmail());

        }
    }

    public boolean reserveBike(String userEmail, String location, boolean bikeAvailable) {
        if (bikeAvailable) {

            System.out.println("Bike reserved successfully for " + userEmail);
            return true;
        } else {
            BikeRequest request = new BikeRequest(userEmail, location, LocalDateTime.now());
            bikeRequestQueue.offer(request);
            System.out.println("Bike not available. Request added to queue.");
            return false;
        }
    }

    public void viewBikeRequestQueue() {
        if (bikeRequestQueue.isEmpty()) {
            System.out.println("No pending bike requests.");
            return;
        }
        System.out.println("\n=== Pending Bike Requests ===");
        for (BikeRequest req : bikeRequestQueue) {
            System.out.println(req);
        }
    }

    public void updateBikeRequestQueue() {
        if (bikeRequestQueue.isEmpty()) {
            System.out.println("Queue is already empty.");
            return;
        }
        BikeRequest removed = bikeRequestQueue.poll();
        System.out.println("Removed request: " + removed);
        System.out.println("Queue updated successfully.");
    }

    public Deque<ERyderLog> getSystemLogStack() {
        return systemLogStack;
    }

    public Queue<BikeRequest> getBikeRequestQueue() {
        return bikeRequestQueue;
    }
}