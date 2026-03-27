public class ERyder {
    public static final String COMPANY_NAME = "ERyder";
    public static final double BASE_FARE = 1.0;
    public static final double PER_MINUTE_FARE = 0.5;
    private final String LINKED_ACCOUNT;
    private final String LINKED_PHONE_NUMBER;
    
    private int bikeID;
    private int batteryLevel;
    private boolean isAvailable;
    private double kmDriven;

    private int totalUsageInMinutes;
    private double totalFare;

    public ERyder(int bikeID, int batteryLevel, boolean isAvailable, double kmDriven) {
        this.bikeID = bikeID;
        if (batteryLevel >= 0 && batteryLevel <= 100) {
            this.batteryLevel = batteryLevel;
        } else {
            System.out.println("Battery level error! Set to 0.");
            this.batteryLevel = 0;
        }
        this.isAvailable = isAvailable;
        this.kmDriven = kmDriven;
        this.LINKED_ACCOUNT = "";
        this.LINKED_PHONE_NUMBER = "";
    }

    public ERyder(int bikeID, int batteryLevel, boolean isAvailable, double kmDriven,
                  String linkedAccount, String linkedPhoneNumber) {
        this.bikeID = bikeID;
        if (batteryLevel >= 0 && batteryLevel <= 100) {
            this.batteryLevel = batteryLevel;
        } else {
            System.out.println("Battery level error! Set to 0.");
            this.batteryLevel = 0;
        }
        this.isAvailable = isAvailable;
        this.kmDriven = kmDriven;
        this.LINKED_ACCOUNT = linkedAccount;
        this.LINKED_PHONE_NUMBER = linkedPhoneNumber;
    }

    public void ride() {
        if (isAvailable && batteryLevel > 0) {
            System.out.println("The bike is available.");
        } else {
            System.out.println("The bike is not available.");
        }
    }

    public void printBikeDetails() {
        System.out.println("Bike ID: " + bikeID);
        System.out.println("Battery Level: " + batteryLevel + "%");
        System.out.println("Is Available: " + isAvailable);
        System.out.println("Total Distance: " + kmDriven + " km");
        System.out.println("---------------------------");
    }

    private double calculateFare(int usageInMinutes) {
        return BASE_FARE + (PER_MINUTE_FARE * usageInMinutes);
    }

    public void printRideDetails(int usageInMinutes) {
        this.totalUsageInMinutes = usageInMinutes;
        this.totalFare = calculateFare(usageInMinutes);

        System.out.println("Company: " + COMPANY_NAME);
        System.out.println("Linked Account: " + LINKED_ACCOUNT);
        System.out.println("Linked Phone: " + LINKED_PHONE_NUMBER);
        System.out.println("Bike ID: " + bikeID);
        System.out.println("Usage Minutes: " + totalUsageInMinutes);
        System.out.println("Total Fare: $" + totalFare);
        System.out.println("---------------------------");
    }

}
