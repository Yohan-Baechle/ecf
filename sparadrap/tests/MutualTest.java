import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Mutual;
import com.yb.sparadrap.model.enums.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MutualTest {

    private Mutual mutual;
    private Address address;

    @BeforeEach
    public void setUp() {
        // Création d'une adresse pour initialiser la mutuelle
        address = new Address("30 rue de la Liberté", "75008", "Paris");
        mutual = new Mutual("Mutuelle Alpha", address, Department.PARIS, "0123456789", "contact@mutuelle-alpha.com", 75.0);
    }

    @Test
    public void testConstructor() {
        // Vérifier que les valeurs sont correctement initialisées par le constructeur
        assertEquals("Mutuelle Alpha", mutual.getName());
        assertEquals(address, mutual.getAddress());
        assertEquals(Department.PARIS, mutual.getDepartment());
        assertEquals("0123456789", mutual.getPhoneNumber());
        assertEquals("contact@mutuelle-alpha.com", mutual.getEmail());
        assertEquals(75.0, mutual.getReimbursementRate());
    }

    @Test
    public void testSettersAndGetters() {
        Address newAddress = new Address("50 avenue des Champs", "75008", "Paris");
        mutual.setAddress(newAddress);

        // Vérifier chaque attribut individuellement
        assertEquals("50 avenue des Champs", mutual.getAddress().getStreet());
        assertEquals("75008", mutual.getAddress().getPostalCode());
        assertEquals("Paris", mutual.getAddress().getCity());
    }

    @Test
    public void testReimbursementRateBounds() {
        // Tester les bornes de taux de remboursement
        mutual.setReimbursementRate(0.0);
        assertEquals(0.0, mutual.getReimbursementRate());

        mutual.setReimbursementRate(100.0);
        assertEquals(100.0, mutual.getReimbursementRate());

        // Vérifier que les valeurs au-delà des bornes logiques sont bien gérées
        mutual.setReimbursementRate(-10.0);
        assertEquals(-10.0, mutual.getReimbursementRate());

        mutual.setReimbursementRate(150.0);
        assertEquals(150.0, mutual.getReimbursementRate());
    }

    @Test
    public void testNullAddress() {
        // Tester que l'adresse ne doit pas être null
        assertThrows(NullPointerException.class, () -> mutual.setAddress(null));
    }
}
