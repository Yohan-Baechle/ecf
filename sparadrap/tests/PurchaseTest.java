import com.yb.sparadrap.model.*;
import com.yb.sparadrap.model.enums.MedicationCategory;
import com.yb.sparadrap.store.DoctorDataStore;
import com.yb.sparadrap.store.MutualDataStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PurchaseTest {

    private Customer customer;
    private Doctor doctor;
    private Medication med1;
    private Medication med2;
    private Map<Medication, Integer> medicationBasket;

    @BeforeEach
    public void setup() {
        // Initialisation d'une adresse fictive pour le client
        Address address = new Address("123 rue du Test", "75001", "Paris");

        // Initialisation d'un client, avec une mutuelle et un médecin référent
        Mutual mutual = MutualDataStore.getInstance().getMutualByName("Mutuelle Alpha");
        Doctor referringDoctor = DoctorDataStore.getInstance().getDoctorByName("Dr. Bernard Martin");
        customer = new Customer("John", "Doe", address, "0612345678", "john.doe@example.com", "123456789012345",
                LocalDate.of(1990, 1, 1), mutual, referringDoctor);

        // Initialisation d'un médecin prescripteur
        doctor = new Doctor("Dr. Bernard", "Smith", address, "0612345678", "dr.smith@example.com", "1234567890123");

        // Création de quelques médicaments
        med1 = new Medication("Paracétamol", MedicationCategory.ANALGESIQUE, LocalDate.of(2010, 1, 1), 100, 2.5);
        med2 = new Medication("Ibuprofène", MedicationCategory.ANTI_INFLAMMATOIRE, LocalDate.of(2012, 5, 15), 50, 3.0);

        // Création d'un panier avec les médicaments
        medicationBasket = new HashMap<>();
        medicationBasket.put(med1, 2); // 2 Paracétamols
        medicationBasket.put(med2, 1); // 1 Ibuprofène
    }

    @Test
    public void testCreatePurchaseWithPrescription() {
        // Création d'un achat avec ordonnance
        Purchase purchase = new Purchase(customer, LocalDate.now(), medicationBasket, doctor, LocalDate.now());

        // Validation des données du client, médecin et médicaments
        assertEquals(customer, purchase.getCustomer(), "Le client doit correspondre.");
        assertEquals(doctor, purchase.getPrescribingDoctor(), "Le médecin doit correspondre.");
        assertEquals(2, purchase.getMedicationBasket().get(med1), "La quantité de Paracétamol doit être correcte.");
        assertEquals(1, purchase.getMedicationBasket().get(med2), "La quantité d'Ibuprofène doit être correcte.");
    }

    @Test
    public void testCreatePurchaseWithoutPrescription() {
        // Création d'un achat sans ordonnance
        Purchase purchase = new Purchase(customer, LocalDate.now(), medicationBasket, null, null);

        // Validation que l'achat ne contient pas d'ordonnance
        assertEquals(customer, purchase.getCustomer(), "Le client doit correspondre.");
        assertNull(purchase.getPrescribingDoctor(), "Le médecin ne doit pas être défini.");
        assertNull(purchase.getPrescriptionDate(), "La date de prescription ne doit pas être définie.");
        assertEquals(2, purchase.getMedicationBasket().get(med1), "La quantité de Paracétamol doit être correcte.");
        assertEquals(1, purchase.getMedicationBasket().get(med2), "La quantité d'Ibuprofène doit être correcte.");
    }

    @Test
    public void testTotalPriceCalculation() {
        // Création d'un achat et validation du montant total
        Purchase purchase = new Purchase(customer, LocalDate.now(), medicationBasket, doctor, LocalDate.now());

        // Calculer le prix total attendu
        double expectedTotalPrice = (2 * med1.getPrice()) + (1 * med2.getPrice());
        assertEquals(expectedTotalPrice, purchase.getTotalAmount(), "Le montant total doit être correctement calculé.");
    }

    @Test
    public void testAddMedicationToBasket() {
        // Création d'un achat et ajout de médicaments
        Purchase purchase = new Purchase(customer, LocalDate.now(), medicationBasket, null, null);
        Medication newMed = new Medication("Amoxicilline", MedicationCategory.ANTIBIOTIQUE, LocalDate.of(2015, 6, 1), 30, 5.0);
        purchase.addMedication(newMed, 3); // Ajouter 3 Amoxicillines

        // Validation des quantités après ajout
        assertEquals(2, purchase.getMedicationBasket().get(med1), "La quantité de Paracétamol doit être correcte.");
        assertEquals(1, purchase.getMedicationBasket().get(med2), "La quantité d'Ibuprofène doit être correcte.");
        assertEquals(3, purchase.getMedicationBasket().get(newMed), "La quantité d'Amoxicilline doit être correcte.");

        // Validation du montant total après ajout
        double expectedTotalPrice = (2 * med1.getPrice()) + (1 * med2.getPrice()) + (3 * newMed.getPrice());
        assertEquals(expectedTotalPrice, purchase.getTotalAmount(), "Le montant total doit être correctement mis à jour.");
    }
}
