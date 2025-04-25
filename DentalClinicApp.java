// Test Application for Appointment Booking
public class DentalClinicApp {
    public static void main(String[] args) {
        // Setup system
        ClinicSchedule clinicSchedule = new ClinicSchedule();
        Receptionist receptionist = new Receptionist(clinicSchedule);

        // Test Case 1: Successful Appointment Booking
        System.out.println("Test Case 1: Booking a new appointment");
        Patient patient1 = new Patient("Alice Smith", 101, "alice@example.com");
        Appointment appointment1 = receptionist.bookAppointment(patient1, "2025-04-25 10:00 AM", "Dr. John Doe");
        receptionist.confirmAppointment(appointment1);

        // Test Case 2: Unavailable Slots Handling
        System.out.println("\nTest Case 2: Booking when no slots are available");
        Patient patient2 = new Patient("Bob Johnson", 102, "bob@example.com");
        receptionist.bookAppointment(patient2, "2025-04-25 10:00 AM", "Dr. Emma Green");

        // Test Case 3: Rescheduling Appointment
        System.out.println("\nTest Case 3: Rescheduling an appointment");
        receptionist.rescheduleAppointment(appointment1, "2025-04-26 09:00 AM");

        // Test Case 4: Cancel Appointment
        System.out.println("\nTest Case 4: Canceling an appointment");
        receptionist.cancelAppointment(appointment1);

        // Test Case 5: Handling No-Show
        System.out.println("\nTest Case 5: No-Show Scenario");
        System.out.println("Receptionist marks Alice Smith's appointment as missed.");

        // Test Case 6: Invalid Input Handling (Attempting to book an appointment with incorrect details)
        System.out.println("\nTest Case 6: Handling invalid input");
        receptionist.bookAppointment(null, "Invalid date", "Unknown Dentist");
    }
}