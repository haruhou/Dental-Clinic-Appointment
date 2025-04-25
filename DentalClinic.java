import java.util.ArrayList;
import java.util.List;

// Represents a Patient requesting an appointment
class Patient {
    private String name;
    private int patientId;
    private String contactInfo;

    public Patient(String name, int patientId, String contactInfo) {
        this.name = name;
        this.patientId = patientId;
        this.contactInfo = contactInfo;
    }

    public int getPatientId() { return patientId; }
    public String getName() { return name; }
    public String getContactInfo() { return contactInfo; }
}

// Stores appointment details
class Appointment {
    private int appointmentId;
    private Patient patient;
    private String dateTime;
    private String dentistName;
    private boolean confirmed;

    public Appointment(int appointmentId, Patient patient, String dateTime, String dentistName) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.dateTime = dateTime;
        this.dentistName = dentistName;
        this.confirmed = false;
    }

    public void confirmAppointment() {
        confirmed = true;
        NotificationService.sendConfirmation(patient, this);
    }

    @Override
    public String toString() {
        return "Appointment [ID=" + appointmentId + ", Patient=" + patient.getName() +
               ", DateTime=" + dateTime + ", Dentist=" + dentistName + ", Confirmed=" + confirmed + "]";
    }
}

// Manages available appointment slots to prevent scheduling conflicts
class ClinicSchedule {
    private List<String> availableSlots = new ArrayList<>();

    public ClinicSchedule() {
        // Sample available slots
        availableSlots.add("2025-04-25 10:00 AM");
        availableSlots.add("2025-04-25 02:00 PM");
        availableSlots.add("2025-04-26 09:00 AM");
    }

    public boolean isSlotAvailable(String dateTime) {
        return availableSlots.contains(dateTime);
    }

    public void bookSlot(String dateTime) {
        availableSlots.remove(dateTime);
    }
}

// Handles appointment bookings, confirmations, cancellations, and rescheduling
class Receptionist {
    private List<Appointment> appointments = new ArrayList<>();
    private ClinicSchedule clinicSchedule;

    public Receptionist(ClinicSchedule clinicSchedule) {
        this.clinicSchedule = clinicSchedule;
    }

    public Appointment bookAppointment(Patient patient, String dateTime, String dentistName) {
        if (!clinicSchedule.isSlotAvailable(dateTime)) {
            System.out.println("No available slots for selected time.");
            return null;
        }

        int appointmentId = appointments.size() + 1;
        Appointment newAppointment = new Appointment(appointmentId, patient, dateTime, dentistName);
        clinicSchedule.bookSlot(dateTime);
        appointments.add(newAppointment);
        System.out.println("Appointment booked successfully!");
        return newAppointment;
    }

    public void confirmAppointment(Appointment appointment) {
        if (appointment != null) {
            appointment.confirmAppointment();
            System.out.println("Appointment confirmed: " + appointment);
        }
    }

    public void cancelAppointment(Appointment appointment) {
        if (appointment != null) {
            appointments.remove(appointment);
            System.out.println("Appointment canceled: " + appointment);
        }
    }

    public void rescheduleAppointment(Appointment appointment, String newDateTime) {
        if (clinicSchedule.isSlotAvailable(newDateTime)) {
            appointment.confirmAppointment();
            System.out.println("Appointment rescheduled to: " + newDateTime);
        } else {
            System.out.println("Reschedule failed: No available slots for " + newDateTime);
        }
    }
}

// Sends appointment notifications
class NotificationService {
    public static void sendConfirmation(Patient patient, Appointment appointment) {
        System.out.println("Notification Sent to " + patient.getContactInfo() + 
                           ": Your appointment is confirmed on " + appointment.toString());
    }

    public static void sendReminder(Patient patient, String appointmentDetails) {
        System.out.println("Reminder Sent to " + patient.getContactInfo() + 
                           ": Upcoming appointment - " + appointmentDetails);
    }
}

// Main system controlling the appointment workflow
class DentalClinicSystem {
    private Receptionist receptionist;
    
    public DentalClinicSystem(Receptionist receptionist) {
        this.receptionist = receptionist;
    }

    public void scheduleAppointment(Patient patient, String dateTime, String dentistName) {
        Appointment appointment = receptionist.bookAppointment(patient, dateTime, dentistName);
        receptionist.confirmAppointment(appointment);
    }

    public void cancelOrReschedule(Appointment appointment, boolean reschedule, String newDateTime) {
        if (reschedule) {
            receptionist.rescheduleAppointment(appointment, newDateTime);
        } else {
            receptionist.cancelAppointment(appointment);
        }
    }
}

