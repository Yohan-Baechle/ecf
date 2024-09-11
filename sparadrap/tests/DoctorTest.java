import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoctorTest {

    private Doctor doctor;

    @BeforeEach
    public void setUp() {
        // Initialiser une instance d'Address et Doctor avant chaque test
        Address address = new Address("10 rue de la Santé", "75001", "Paris");
        doctor = new Doctor("Bernard", "Martin", address, "0612345678", "bernard.martin@example.com", "123456789");
    }

    @Test
    public void testGetters() {
        // Vérifier que les getters renvoient les bonnes valeurs
        assertEquals("Bernard", doctor.getFirstName());
        assertEquals("Martin", doctor.getLastName());
        assertEquals("0612345678", doctor.getPhoneNumber());
        assertEquals("bernard.martin@example.com", doctor.getEmail());
        assertEquals("123456789", doctor.getRegistrationNumber());

        // Tester l'adresse associée au médecin
        assertEquals("10 rue de la Santé", doctor.getAddress().getStreet());
        assertEquals("75001", doctor.getAddress().getPostalCode());
        assertEquals("Paris", doctor.getAddress().getCity());
    }

    @Test
    public void testSetters() {
        // Changer les valeurs des attributs
        doctor.setFirstName("Jean");
        doctor.setLastName("Dupont");
        doctor.setPhoneNumber("0698765432");
        doctor.setEmail("jean.dupont@example.com");
        doctor.setRegistrationNumber("987654321");

        Address newAddress = new Address("20 avenue des Champs", "75008", "Paris");
        doctor.setAddress(newAddress);

        // Vérifier que les setters ont bien changé les valeurs
        assertEquals("Jean", doctor.getFirstName());
        assertEquals("Dupont", doctor.getLastName());
        assertEquals("0698765432", doctor.getPhoneNumber());
        assertEquals("jean.dupont@example.com", doctor.getEmail());
        assertEquals("987654321", doctor.getRegistrationNumber());

        // Vérifier les nouvelles valeurs de l'adresse
        assertEquals("20 avenue des Champs", doctor.getAddress().getStreet());
        assertEquals("75008", doctor.getAddress().getPostalCode());
        assertEquals("Paris", doctor.getAddress().getCity());
    }

    @Test
    public void testRegistrationNumberProperty() {
        // Vérifier la propriété registrationNumber
        assertEquals("123456789", doctor.registrationNumberProperty().get());

        // Modifier le numéro d'enregistrement via la propriété et vérifier
        doctor.registrationNumberProperty().set("987654321");
        assertEquals("987654321", doctor.getRegistrationNumber());
    }
}
