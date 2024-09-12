import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Specialist;
import com.yb.sparadrap.model.enums.Specialty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpecialistTest {

    private Specialist specialist;

    @BeforeEach
    public void setUp() {
        // Initialisation des objets nécessaires avant chaque test
        Address address = new Address("10 rue de la Santé", "75001", "Paris");
        specialist = new Specialist("John", "Doe", address, "0612345678", "john.doe@example.com", "1234567890123", Specialty.CARDIOLOGIE);
    }

    @Test
    public void testGetters() {
        // Vérification des getters
        assertEquals("John", specialist.getFirstName());
        assertEquals("Doe", specialist.getLastName());
        assertEquals("0612345678", specialist.getPhoneNumber());
        assertEquals("john.doe@example.com", specialist.getEmail());
        assertEquals("1234567890123", specialist.getRegistrationNumber());
        assertEquals(Specialty.CARDIOLOGIE, specialist.getSpecialty());
    }

    @Test
    public void testSetters() {
        // Modification des attributs et vérification des setters
        specialist.setFirstName("Jane");
        assertEquals("Jane", specialist.getFirstName());

        specialist.setLastName("Smith");
        assertEquals("Smith", specialist.getLastName());

        specialist.setPhoneNumber("0698765432");
        assertEquals("0698765432", specialist.getPhoneNumber());

        specialist.setEmail("jane.smith@example.com");
        assertEquals("jane.smith@example.com", specialist.getEmail());

        specialist.setRegistrationNumber("9876543210987");
        assertEquals("9876543210987", specialist.getRegistrationNumber());

        specialist.setSpecialty(Specialty.DERMATOLOGIE);
        assertEquals(Specialty.DERMATOLOGIE, specialist.getSpecialty());
    }

    @Test
    public void testToString() {
        // Vérification de la méthode toString pour s'assurer qu'elle inclut les informations de spécialité
        String expectedString = "John Doe 1234567890123 - Spécialité : Cardiologie";
        assertEquals(expectedString, specialist.toString());
    }

    @Test
    public void testSpecialtyProperty() {
        // Vérification de la propriété specialty
        assertEquals(Specialty.CARDIOLOGIE, specialist.specialtyProperty().get());

        specialist.specialtyProperty().set(Specialty.NEUROLOGIE);
        assertEquals(Specialty.NEUROLOGIE, specialist.getSpecialty());
    }

    @Test
    public void testInheritedProperties() {
        // Vérification des propriétés héritées de la classe Doctor
        assertEquals("10 rue de la Santé", specialist.getAddress().getStreet());
        assertEquals("75001", specialist.getAddress().getZipCode());
        assertEquals("Paris", specialist.getAddress().getCity());
    }
}
