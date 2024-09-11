import com.yb.sparadrap.model.Medication;
import com.yb.sparadrap.model.enums.MedicationCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MedicationTest {

    private Medication medication;

    @BeforeEach
    public void setUp() {
        // Initialisation d'un objet Medication avant chaque test
        medication = new Medication("Paracétamol", MedicationCategory.ANALGESIQUE, LocalDate.of(2020, 1, 1), 50, 2.5);
    }

    @Test
    public void testGetters() {
        // Vérification des getters
        assertEquals("Paracétamol", medication.getName());
        assertEquals(MedicationCategory.ANALGESIQUE, medication.getCategory());
        assertEquals(LocalDate.of(2020, 1, 1), medication.getLaunchDate());
        assertEquals(50, medication.getQuantity());
        assertEquals(2.5, medication.getPrice());
    }

    @Test
    public void testSetters() {
        // Test des setters pour s'assurer que les valeurs sont bien modifiées
        medication.setName("Ibuprofène");
        assertEquals("Ibuprofène", medication.getName());

        medication.setCategory(MedicationCategory.ANTI_INFLAMMATOIRE);
        assertEquals(MedicationCategory.ANTI_INFLAMMATOIRE, medication.getCategory());

        LocalDate newDate = LocalDate.of(2022, 6, 15);
        medication.setLaunchDate(newDate);
        assertEquals(newDate, medication.getLaunchDate());

        medication.setQuantity(100);
        assertEquals(100, medication.getQuantity());

        medication.setPrice(3.0);
        assertEquals(3.0, medication.getPrice());
    }

    @Test
    public void testToString() {
        // Test de la méthode toString pour s'assurer qu'elle retourne le nom du médicament
        assertEquals("Paracétamol", medication.toString());
    }

    @Test
    public void testDefaultConstructor() {
        // Test du constructeur par défaut
        Medication defaultMedication = new Medication();
        assertNull(defaultMedication.getName());
        assertNull(defaultMedication.getCategory());
        assertNull(defaultMedication.getLaunchDate());
        assertEquals(0, defaultMedication.getQuantity());
        assertEquals(0.0, defaultMedication.getPrice());
    }

    @Test
    public void testPropertyMethods() {
        // Test des méthodes properties pour s'assurer qu'elles renvoient les bonnes propriétés
        assertEquals("Paracétamol", medication.nameProperty().get());
        assertEquals(MedicationCategory.ANALGESIQUE, medication.categoryProperty().get());
        assertEquals(LocalDate.of(2020, 1, 1), medication.launchDateProperty().get());
        assertEquals(50, medication.quantityProperty().get());
        assertEquals(2.5, medication.priceProperty().get());
    }
}
