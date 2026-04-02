import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class BikeRental {
    private boolean isRegisteredUser;
    private String emailAddress;
    private String location;
    private LocalDateTime tripStartTime;
    private String bikeID;
    private boolean locationValid;

    private final UserRegistration userRegistration = new UserRegistration();
    private ActiveRental activeRental;
    private final LinkedList<ActiveRental> activeRentalsList = new LinkedList<>();
    private final Scanner scanner = new Scanner(System.in);

    public void simulateApplicationInput() {
        System.out.println("=====================================");
        System.out.println("   ERyder Bike Rental Simulation");
        System.out.println("=====================================");

        System.out.print("Is the user registered? (true/false): ");
        this.isRegisteredUser = scanner.nextBoolean();
        scanner.nextLine();

        System.out.print("Enter user email address: ");
        this.emailAddress = scanner.nextLine().trim();

        System.out.print("Enter rental location: ");
        this.location = scanner.nextLine().trim();

        System.out.println("\n🔍 Analysing rental request...");
        this.bikeID = analyseRequest(isRegisteredUser, emailAddress, location);

        if (!locationValid) {
            System.out.println("❌ Rental request failed (invalid location/no bike available).");
            return;
        }

        System.out.println("\n📱 Reserving e-bike...");
        reserveBike(bikeID);

        System.out.println("\n📋 Active Rentals:");
        viewActiveRentals();

        System.out.println("\n🛑 Ending trip...");
        if (bikeID != null) {
            removeTrip(bikeID, 5, 3.0);
        }

        System.out.println("\n📋 Active Rentals (After Trip End):");
        viewActiveRentals();

        scanner.close();
    }

    private String analyseRequest(boolean isRegistered, String emailAddress, String location) {
        if (isRegistered) {
            System.out.printf("👋 Welcome back, %s!%n", emailAddress);
        } else {
            System.out.println("⚠️ You're not a registered user. Starting registration...");
            userRegistration.startRegistration();
        }
        return validateLocation(location);
    }

    private String validateLocation(String location) {
        for (Bike bike : BikeDatabase.bikes) {
            if (bike.getLocation().equals(location) && bike.isAvailable()) {
                System.out.printf("✅ A bike (%s) is available at %s.%n", bike.getBikeID(), location);
                this.locationValid = true;
                return bike.getBikeID();
            }
        }
        System.out.printf("❌ No bikes available at %s.%n", location);
        this.locationValid = false;
        return null;
    }

    private void reserveBike(String bikeID) {
        if (bikeID == null) {
            System.out.println("❌ Cannot reserve bike (invalid bike ID).");
            return;
        }

        for (Bike bike : BikeDatabase.bikes) {
            if (bike.getBikeID().equals(bikeID)) {
                this.tripStartTime = LocalDateTime.now();
                bike.setAvailable(false);
                bike.setLastUsedTime(tripStartTime);

                Eryder eryderBike = new Eryder(Integer.parseInt(bikeID.substring(1)), bike.getBatteryLevel(), false, 0);
                eryderBike.ride();

                this.activeRental = new ActiveRental(bikeID, emailAddress, tripStartTime);
                activeRentalsList.add(activeRental);

                System.out.printf("✅ Reserved bike %s. Please locate the bike to start your trip.%n", bikeID);
                break;
            }
        }
    }
    private void viewActiveRentals() {
        if (activeRentalsList.isEmpty()) {
            System.out.println("   No active rentals.");
            return;
        }
        for (ActiveRental rental : activeRentalsList) {
            System.out.printf("   %s%n", rental);
        }
    }

    private void removeTrip(String bikeID, int usageMinutes, double kmRidden) {
        Iterator<ActiveRental> iterator = activeRentalsList.iterator();
        while (iterator.hasNext()) {
            ActiveRental rental = iterator.next();
            if (rental.getBikeID().equals(bikeID)) {
                iterator.remove();
                break;
            }
        }

        for (Bike bike : BikeDatabase.bikes) {
            if (bike.getBikeID().equals(bikeID)) {
                Eryder eryderBike = new Eryder(Integer.parseInt(bikeID.substring(1)), bike.getBatteryLevel(), false, 0);
                eryderBike.returnBike(usageMinutes, kmRidden);

                bike.setAvailable(true);
                bike.setLastUsedTime(LocalDateTime.now());
                bike.setBatteryLevel(eryderBike.getBatteryLevel());

                System.out.printf("✅ Trip ended for bike %s. Thank you for riding with ERyder!%n", bikeID);
                break;
            }
        }
    }

    private void removeTrip(String bikeID) {
        removeTrip(bikeID, 10, 5.0);
    }
}
