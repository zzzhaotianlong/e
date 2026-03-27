import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class UserRegistration {
    public static final double VIP_DISCOUNT_UNDER_18_BIRTHDAY = 25.0;
    public static final double VIP_DISCOUNT_UNDER_18 = 20.0;
    public static final double VIP_BASE_FEE = 100.0;

    private String fullName;
    private String emailAddress;
    private String dateOfBirth;
    private long cardNumber;
    private String cardProvider;
    private String cardExpiryDate;
    private double feeToCharge;
    private int cvv;
    private String userType;
    private boolean emailValid;
    private boolean minorAndBirthday;
    private boolean minor;
    private boolean ageValid;
    private boolean cardNumberValid;
    private boolean cardStillValid;
    private boolean validCVV;

    public void registration() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ERyder Registration.");
        System.out.println("Here are your two options:");
        System.out.println("1. Register as a Regular User");
        System.out.println("2. Register as a VIP User");
        System.out.print("Please enter your choice (1 or 2): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 

        if (choice == 1) {
            userType = "Regular User";
        } else {
            userType = "VIP User";
        }
        System.out.print("Enter your full name: ");
        fullName = scanner.nextLine();
        System.out.print("Enter your email address: ");
        emailAddress = scanner.nextLine();
        emailValid = analyseEmail();
        if (!emailValid) {
            System.out.println("Invalid email address. Going back to the start of the registration");
            registration();
            return;
        }

        System.out.print("Enter your date of birth (YYYY-MM-DD): ");
        dateOfBirth = scanner.nextLine();
        LocalDate dob = LocalDate.parse(dateOfBirth);
        ageValid = analyseAge(dob);
        if (!ageValid) {
            System.exit(0);
        }

        System.out.print("Enter your card number (only VISA, MasterCard, American Express accepted): ");
        cardNumber = scanner.nextLong();
        scanner.nextLine();
        cardNumberValid = analyseCardNumber(cardNumber);
        if (!cardNumberValid) {
            System.out.println("Sorry, but we accept only VISA, MasterCard, or American Express cards. Please try again with a valid card.");
            System.out.println("Going back to the start of the registration.");
            registration();
            return;
        }

        System.out.print("Enter your card expiry date (MM/YY): ");
        cardExpiryDate = scanner.nextLine();
        cardStillValid = analyseCardExpiryDate(cardExpiryDate);
        if (!cardStillValid) {
            System.out.println("Sorry, your card has expired. Please use a different card.");
            System.out.println("Going back to the start fo the registration process...");
            registration();
            return;
        }

        System.out.print("Enter your card's CVV: ");
        cvv = scanner.nextInt();
        validCVV = analyseCVV(cvv);
        if (!validCVV) {
            System.out.println("Invalid CVV for the given card.");
            System.out.println("Going back to the start of the registration process.");
            registration();
            return;
        }
        finalCheckpoint();
        scanner.close();
    }

    private boolean analyseEmail() {
        if (emailAddress.contains("@") && emailAddress.contains(".")) {
            System.out.println("Email is valid");
            return true;
        }
        return false;
    }

    private boolean analyseAge(LocalDate dob) {
        LocalDate today = LocalDate.now();
        int age = Period.between(dob, today).getYears();
        boolean isBirthday = (dob.getMonth() == today.getMonth() && dob.getDayOfMonth() == today.getDayOfMonth());

        if (userType.equals("VIP User")) {
            if (isBirthday && age <= 18 && age > 12) {
                System.out.println("Happy Birthday!");
                System.out.println("You get 25% discount on the VIP subscription fee for being born today and being under 18!");
                minorAndBirthday = true;
            } else if (age <= 18 && age > 12) {
                System.out.println("You get 20% discount on the VIP subscription fee for being under 18!");
                minor = true;
            }
        }

        if (age <= 12 || age > 120) {
            System.out.println("Looks like you are either too young or already dead. Sorry, you can't be our user. Have a nice day");
            return false;
        }
        return true;
    }

    private boolean analyseCardNumber(long cardNumber) {
        String cardNumStr = Long.toString(cardNumber);
        int len = cardNumStr.length();
        int firstTwoDigits = Integer.parseInt(cardNumStr.substring(0, 2));
        int firstFourDigits = Integer.parseInt(cardNumStr.substring(0, 4));

        if ((len == 13 || len == 15) && cardNumStr.startsWith("4")) {
            cardProvider = "VISA";
            return true;
        } else if (len == 16 && ((firstTwoDigits >= 51 && firstTwoDigits <= 55) || (firstFourDigits >= 2221 && firstFourDigits <= 2720))) {
            cardProvider = "MasterCard";
            return true;
        } else if (len == 15 && (cardNumStr.startsWith("34") || cardNumStr.startsWith("37"))) {
            cardProvider = "American Express";
            return true;
        }
        return false;
    }
   
    private boolean analyseCardExpiryDate(String expiryDate) {
        int month = Integer.parseInt(expiryDate.substring(0, 2));
        int year = Integer.parseInt(expiryDate.substring(3, 5)) + 2000;
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();

        if (year > currentYear || (year == currentYear && month >= currentMonth)) {
            System.out.println("The card is still valid");
            return true;
        }
        return false;
    }

    private boolean analyseCVV(int cvv) {
        String cvvStr = Integer.toString(cvv);
        if ((cardProvider.equals("American Express") && cvvStr.length() == 4) ||
            (cardProvider.equals("VISA") && cvvStr.length() == 3) ||
            (cardProvider.equals("MasterCard") && cvvStr.length() == 3)) {
            System.out.println("Card CVV is valid.");
            return true;
        }
        return false;
    }

    private void finalCheckpoint() {
        if (emailValid && ageValid && cardNumberValid && cardStillValid && validCVV) {
            chargeFees();
        } else {
            System.out.println("Sorry, your registration was unsuccessful due to the following reason(s)");
            if (!emailValid) System.out.println("Invalid email address");
            if (!ageValid) System.out.println("Invalid age");
            if (!cardNumberValid) System.out.println("Invalid card number");
            if (!cardStillValid) System.out.println("Card has expired");
            if (!validCVV) System.out.println("Invalid CVV");
            System.out.println("Going back to the start of the registration process.");
            registration();
        }
    }

    private void chargeFees() {
        if (minorAndBirthday) {
            feeToCharge = VIP_BASE_FEE * (1 - VIP_DISCOUNT_UNDER_18_BIRTHDAY / 100);
        } else if (minor) {
            feeToCharge = VIP_BASE_FEE * (1 - VIP_DISCOUNT_UNDER_18 / 100);
        } else {
            feeToCharge = VIP_BASE_FEE;
        }

        String cardNumStr = Long.toString(cardNumber);
        String lastFourDigits = cardNumStr.substring(cardNumStr.length() - 4);
        System.out.println("Thank you for your payment.");
        System.out.println("A fee of " + feeToCharge + " has been charged to your card ending with ****" + lastFourDigits);
    }

    @Override
    public String toString() {
        String s;
        String cardNumStr = Long.toString(cardNumber);
        s=cardNumStr.substring(0, cardNumStr.length() - 4);
        String censoredPart = s.replaceAll(".", "*");
        String lastFourDigits = cardNumStr.substring(cardNumStr.length() - 4);
        String censoredNumber = censoredPart + lastFourDigits;

        return "Registration successful! Here are your details:\n" +
               "User Type: " + userType + "\n" +
               "Full Name: " + fullName + "\n" +
               "Email Address: " + emailAddress + "\n" +
               "Date of Birth: " + dateOfBirth + "\n" +
               "Card Number: " + censoredNumber + "\n" +
               "Card Provider: " + cardProvider + "\n" +
               "Card Expiry Date: " + cardExpiryDate;
    }
}