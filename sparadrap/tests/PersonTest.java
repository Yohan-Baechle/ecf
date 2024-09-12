import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {

    private Customer customer;

    @BeforeEach
    public void setUp() {
        // Initialisation d'un objet Customer et d'une adresse avant chaque test
        Address address = new Address("10 rue de la Santé", "75001", "Paris");
        customer = new Customer("John", "Doe", address, "0612345678", "john.doe@example.com",
                "123456789012345", LocalDate.of(1990, 1, 1), null, null);
    }

    @Test
    public void testGetters() {
        // Test des getters hérités de la classe Person
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("0612345678", customer.getPhoneNumber());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals("10 rue de la Santé", customer.getAddress().getStreet());
        assertEquals("75001", customer.getAddress().getZipCode());
        assertEquals("Paris", customer.getAddress().getCity());
    }

    @Test
    public void testSetters() {
        // Test des setters hérités de la classe Person
        customer.setFirstName("Jane");
        customer.setLastName("Smith");
        customer.setPhoneNumber("0698765432");
        customer.setEmail("jane.smith@example.com");
        Address newAddress = new Address("20 avenue des Champs", "75008", "Paris");
        customer.setAddress(newAddress);

        assertEquals("Jane", customer.getFirstName());
        assertEquals("Smith", customer.getLastName());
        assertEquals("0698765432", customer.getPhoneNumber());
        assertEquals("jane.smith@example.com", customer.getEmail());
        assertEquals("20 avenue des Champs", customer.getAddress().getStreet());
        assertEquals("75008", customer.getAddress().getZipCode());
        assertEquals("Paris", customer.getAddress().getCity());
    }

    @Test
    public void testFirstNameProperty() {
        // Test de la propriété firstName
        assertEquals("John", customer.firstNameProperty().get());
        customer.firstNameProperty().set("Jane");
        assertEquals("Jane", customer.getFirstName());
    }

    @Test
    public void testLastNameProperty() {
        // Test de la propriété lastName
        assertEquals("Doe", customer.lastNameProperty().get());
        customer.lastNameProperty().set("Smith");
        assertEquals("Smith", customer.getLastName());
    }

    @Test
    public void testPhoneNumberProperty() {
        // Test de la propriété phoneNumber
        assertEquals("0612345678", customer.phoneNumberProperty().get());
        customer.phoneNumberProperty().set("0698765432");
        assertEquals("0698765432", customer.getPhoneNumber());
    }

    @Test
    public void testEmailProperty() {
        // Test de la propriété email
        assertEquals("john.doe@example.com", customer.emailProperty().get());
        customer.emailProperty().set("jane.smith@example.com");
        assertEquals("jane.smith@example.com", customer.getEmail());
    }

    @Test
    public void testAddress() {
        // Test de la méthode setAddress et des attributs d'address
        Address newAddress = new Address("15 rue Victor Hugo", "69002", "Lyon");
        customer.setAddress(newAddress);

        assertEquals("15 rue Victor Hugo", customer.getAddress().getStreet());
        assertEquals("69002", customer.getAddress().getZipCode());
        assertEquals("Lyon", customer.getAddress().getCity());
    }
}
