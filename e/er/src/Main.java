public class Main {
    public static void main(String[] args) {
        UserRegistration user = new UserRegistration();
        user.registration();
        System.out.println(user); 
        AdminPanel admin = new AdminPanel();
        admin.userManagementOptions();
    }
}
