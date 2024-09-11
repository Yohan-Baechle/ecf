import com.yb.sparadrap.model.*;
import com.yb.sparadrap.model.enums.Specialty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PrescriptionTest {

    private Prescription prescription;
    private Doctor doctor;
    private Specialist specialist;
    private Customer customer;
    private Medication med1;
    private Medication med2;

    @BeforeEach
    public void setUp() {
        // Initialiser des objets pour chaque test
        doctor = new Doctor("Bernard", "Martin", new Address("10 rue de la Santé", "75001", "Paris"), "0612345678", "bernard.martin@example.com", "123456789");
        specialist = new Specialist("Anne", "Dupont", new Address("5 avenue des Hôpitaux", "75002", "Paris"), "0623456789", "anne.dupont@example.com", "1234567890", Specialty.CARDIOLOGIE);
        customer = new Customer("John", "Doe", new Address("20 avenue des Champs", "75008", "Paris"), "0612345678", "john.doe@example.com", "1234567890123", LocalDate.of(1990, 1, 1), null, doctor);
        med1 = new Medication("Paracétamol", com.yb.sparadrap.model.enums.MedicationCategory.ANALGESIQUE, LocalDate.of(2020, 1, 1), 20, 2.5);
        med2 = new Medication("Ibuprofène", com.yb.sparadrap.model.enums.MedicationCategory.ANTI_INFLAMMATOIRE, LocalDate.of(2020, 5, 1), 30, 3.0);

        prescription = new Prescription(LocalDate.now(), doctor, customer, Arrays.asList(med1, med2));
    }

    @Test
    public void testGetters() {
        // Vérifier les getters
        assertEquals(LocalDate.now(), prescription.getPrescriptionDate());
        assertEquals(doctor, prescription.getDoctor());
        assertEquals(customer, prescription.getPatient());
        assertEquals(Arrays.asList(med1, med2), prescription.getMedications());
        assertNull(prescription.getSpecialist()); // Pas de spécialiste dans ce cas
    }

    @Test
    public void testSetters() {
        // Tester les setters
        LocalDate newDate = LocalDate.of(2023, 9, 1);
        prescription.setPrescriptionDate(newDate);
        assertEquals(newDate, prescription.getPrescriptionDate());

        prescription.setDoctor(specialist);
        assertEquals(specialist, prescription.getDoctor());

        Customer newCustomer = new Customer("Jane", "Smith", new Address("50 avenue de la République", "75011", "Paris"), "0698765432", "jane.smith@example.com", "9876543210987", LocalDate.of(1985, 5, 10), null, doctor);
        prescription.setPatient(newCustomer);
        assertEquals(newCustomer, prescription.getPatient());

        prescription.setMedications(Collections.singletonList(med2));
        assertEquals(1, prescription.getMedications().size());
        assertEquals(med2, prescription.getMedications().getFirst());

        prescription.setSpecialist(specialist);
        assertEquals(specialist, prescription.getSpecialist());
    }

    @Test
    public void testToStringWithoutSpecialist() {
        // Vérifier toString() sans spécialiste
        String expectedString = "Prescription{" +
                "date=" + LocalDate.now() +
                ", médecin=Bernard Martin (spécialité : Généraliste)" +
                ", patient=John Doe" +
                ", médicaments=[" + med1.toString() + ", " + med2.toString() + "]" +
                '}';

        assertEquals(expectedString, prescription.toString());
    }

    @Test
    public void testToStringWithSpecialist() {
        // Vérifier toString() avec un spécialiste
        Prescription prescriptionWithSpecialist = new Prescription(LocalDate.now(), specialist, customer, Arrays.asList(med1, med2), specialist);
        String expectedString = "Prescription{" +
                "date=" + LocalDate.now() +
                ", médecin=Anne Dupont (spécialité : Cardiologie)" +
                ", patient=John Doe" +
                ", médicaments=[" + med1.toString() + ", " + med2.toString() + "]" +
                '}';

        assertEquals(expectedString, prescriptionWithSpecialist.toString());
    }
}
