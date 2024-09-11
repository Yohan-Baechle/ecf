import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.model.Doctor;
import com.yb.sparadrap.model.Mutual;
import com.yb.sparadrap.model.enums.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {

    private Customer customer;
    private Mutual mutual;
    private Doctor doctor;

    @BeforeEach
    public void setUp() {
        // Création de l'adresse et du département pour la mutuelle
        Address mutualAddress = new Address("10 rue de la Mutualité", "75015", "Paris");
        Department mutualDepartment = Department.PARIS;  // Supposons que PARIS est une constante dans l'énumération Department

        // Création de l'objet Mutual avec les informations complètes
        mutual = new Mutual("Mutuelle Alpha", mutualAddress, mutualDepartment, "0123456789", "contact@mutuelle-alpha.com", 75.0);

        // Création d'un médecin
        doctor = new Doctor("Bernard", "Martin", new Address("10 rue de la Santé", "75001", "Paris"), "0612345678", "bernard.martin@example.com", "10123456789");

        // Création de l'objet Customer avec toutes les informations nécessaires
        customer = new Customer("John", "Doe", new Address("10 rue de la Paix", "75001", "Paris"), "0612345678", "john.doe@example.com",
                "123456789012345", LocalDate.of(1990, 1, 1), mutual, doctor);
    }


    @Test
    public void testCustomerCreation() {
        // Vérifier que les valeurs initiales sont bien définies
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("10 rue de la Paix", customer.getAddress().getStreet());
        assertEquals("0612345678", customer.getPhoneNumber());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals("123456789012345", customer.getSocialSecurityNumber());
        assertEquals(LocalDate.of(1990, 1, 1), customer.getBirthDate());
        assertEquals(mutual, customer.getMutual());
        assertEquals(doctor, customer.getReferringDoctor());
    }

    @Test
    public void testSettersAndGetters() {
        // Changer les valeurs des attributs du client
        customer.setFirstName("Jane");
        customer.setLastName("Smith");
        customer.setPhoneNumber("0698765432");
        customer.setEmail("jane.smith@example.com");
        customer.setSocialSecurityNumber("543210987654321");
        customer.setBirthDate(LocalDate.of(1985, 5, 15));

        // Créer une nouvelle adresse
        Address newAddress = new Address("20 avenue des Champs", "75008", "Paris");
        customer.setAddress(newAddress);

        // Initialiser correctement la nouvelle mutuelle avec tous les paramètres requis
        Address mutualAddress = new Address("30 rue de la Liberté", "75008", "Paris");
        Department mutualDepartment = Department.PARIS;  // Utilisation d'une valeur de l'énumération Department
        Mutual newMutual = new Mutual("Mutuelle Beta", mutualAddress, mutualDepartment, "0123456789", "contact@mutuelle-beta.com", 80.0);
        customer.setMutual(newMutual);

        // Créer un nouveau docteur
        Doctor newDoctor = new Doctor("Sophie", "Dupont", newAddress, "0698765432", "sophie.dupont@example.com", "10987654321");
        customer.setReferringDoctor(newDoctor);

        // Vérifier que les valeurs ont bien été modifiées
        assertEquals("Jane", customer.getFirstName());
        assertEquals("Smith", customer.getLastName());
        assertEquals("20 avenue des Champs", customer.getAddress().getStreet());
        assertEquals("0698765432", customer.getPhoneNumber());
        assertEquals("jane.smith@example.com", customer.getEmail());
        assertEquals("543210987654321", customer.getSocialSecurityNumber());
        assertEquals(LocalDate.of(1985, 5, 15), customer.getBirthDate());
        assertEquals(newMutual, customer.getMutual());
        assertEquals(newDoctor, customer.getReferringDoctor());
    }


    @Test
    public void testToString() {
        // Vérifier que la méthode toString() fonctionne correctement
        String expected = "John Doe 123456789012345";
        assertEquals(expected, customer.toString());
    }
}
