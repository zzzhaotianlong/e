import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class AdminPanel {
    private ArrayList<RegisteredUsers> registeredUsersList = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
        private static BikeService bikeService = new BikeService();

    public void userManagementOptions() {
        while (true) {
            System.out.println("\nWelcome to E-Ryder Administrator Panel.");
            System.out.println("What do you want to do?");
            System.out.println("1. Add New Users");
            System.out.println("2. View Registered Users");
            System.out.println("3. Remove Registered Users");
            System.out.println("4. Update Registered Users");
            System.out.println("5. View System Logs");
            System.out.println("6. Manage Pending Bike Requests");
            System.out.println("7. EXIT");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: addNewUsers(); break;
                case 2: viewRegisteredUsers(); break;
                case 3: removeRegisteredUsers(); break;
                case 4: updateRegisteredUsers(); break;
                case 5: bikeService.viewSystemLogs();break;
                case 6: managePendingRequests();break;
                case 7: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addNewUsers() {
        System.out.print("How many users would you like to add? ");
        int count = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < count; i++) {
            System.out.println("\n--- Adding User " + (i+1) + " ---");
            System.out.print("Full Name: ");
            String fullName = scanner.nextLine();
            System.out.print("Email Address: ");
            String email = scanner.nextLine();
            System.out.print("Date of Birth (YYYY-MM-DD): ");
            String dob = scanner.nextLine();
            System.out.print("Card Number: ");
            long cardNum = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Card Expiry Date (MM/YY): ");
            String expiry = scanner.nextLine();
            System.out.print("Card Provider: ");
            String provider = scanner.nextLine();
            System.out.print("CVV: ");
            int cvv = scanner.nextInt();
            scanner.nextLine();
            System.out.print("User Type (Regular/VIP): ");
            String userType = scanner.nextLine();

            String[] trips = new String[3];
            for (int t = 0; t < 3; t++) {
                System.out.println("\n--- Trip " + (t+1) + " ---");
                System.out.print("Trip Date (YYYY-MM-DD): ");
                String date = scanner.nextLine();
                System.out.print("Source: ");
                String source = scanner.nextLine();
                System.out.print("Destination: ");
                String dest = scanner.nextLine();
                System.out.print("Fare (€): ");
                String fare = scanner.nextLine();
                System.out.print("Feedback (NULL if none): ");
                String feedback = scanner.nextLine();

                StringBuilder sb = new StringBuilder();
                sb.append("Date: ").append(date)
                  .append(", Source: ").append(source)
                  .append(", Destination: ").append(dest)
                  .append(", Fare (€): ").append(fare)
                  .append(", Feedback: ").append(feedback);
                trips[t] = sb.toString();
            }

            RegisteredUsers user = new RegisteredUsers(fullName, email, dob, cardNum, expiry, provider, cvv, userType, trips);
            registeredUsersList.add(user);
            System.out.println("User added successfully!");
        }
    }

    private void viewRegisteredUsers() {
        if (registeredUsersList.isEmpty()) {
            System.out.println("No registered users to display");
            return;
        }
        System.out.println("\n--- All Registered Users ---");
        for (RegisteredUsers user : registeredUsersList) {
            System.out.println(user);
            System.out.println("---------------------------");
        }
    }

    private void removeRegisteredUsers() {
        if (registeredUsersList.isEmpty()) {
            System.out.println("No registered users to remove");
            return;
        }
        System.out.print("Enter email of user to remove: ");
        String email = scanner.nextLine();
        boolean found = false;
        Iterator<RegisteredUsers> it = registeredUsersList.iterator();
        while (it.hasNext()) {
            RegisteredUsers user = it.next();
            if (user.getEmailAddress().equals(email)) {
                it.remove();
                found = true;
                System.out.println("User removed successfully!");
            }
        }
        if (!found) System.out.println("No user found with this email address");
    }

    private void updateRegisteredUsers() {
        if (registeredUsersList.isEmpty()) {
            System.out.println("No registered users to update");
            return;
        }
        System.out.print("Enter email of user to update: ");
        String email = scanner.nextLine();
        RegisteredUsers target = null;
        for (RegisteredUsers user : registeredUsersList) {
            if (user.getEmailAddress().equals(email)) {
                target = user;
                break;
            }
        }
        if (target == null) {
            System.out.println("No user found with this email address");
            return;
        }

        System.out.println("\n--- Update User (Press ENTER to keep old value) ---");
        System.out.print("Type new full name: ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) target.setFullName(newName);

        System.out.print("Type new email: ");
        String newEmail = scanner.nextLine();
        if (!newEmail.isEmpty()) target.setEmailAddress(newEmail);

        System.out.print("Type new DOB (YYYY-MM-DD): ");
        String newDob = scanner.nextLine();
        if (!newDob.isEmpty()) target.setDateOfBirth(newDob);

        System.out.print("Type new card number (0 for no change): ");
        long newCard = scanner.nextLong();
        scanner.nextLine();
        if (newCard != 0) target.setCardNumber(newCard);

        System.out.print("Type new expiry date (MM/YY): ");
        String newExpiry = scanner.nextLine();
        if (!newExpiry.isEmpty()) target.setCardExpiryDate(newExpiry);

        System.out.print("Type new card provider: ");
        String newProvider = scanner.nextLine();
        if (!newProvider.isEmpty()) target.setCardProvider(newProvider);

        System.out.print("Type new CVV (0 for no change): ");
        int newCvv = scanner.nextInt();
        scanner.nextLine();
        if (newCvv != 0) target.setCvv(newCvv);

        System.out.print("Type new user type: ");
        String newType = scanner.nextLine();
        if (!newType.isEmpty()) target.setUserType(newType);

        System.out.println("User updated successfully!");
    }

}