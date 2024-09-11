import com.yb.sparadrap.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressTest {

    private Address address;

    @BeforeEach
    public void setUp() {
        // Initialiser une instance d'Address avant chaque test
        address = new Address("10 rue de la Paix", "75001", "Paris");
    }

    @Test
    public void testGetters() {
        // Vérifier que les getters retournent les bonnes valeurs
        assertEquals("10 rue de la Paix", address.getStreet());
        assertEquals("75001", address.getPostalCode());
        assertEquals("Paris", address.getCity());
    }

    @Test
    public void testSetters() {
        // Changer les valeurs des attributs
        address.setStreet("20 avenue des Champs");
        address.setPostalCode("75008");
        address.setCity("Paris");

        // Vérifier que les setters ont bien changé les valeurs
        assertEquals("20 avenue des Champs", address.getStreet());
        assertEquals("75008", address.getPostalCode());
        assertEquals("Paris", address.getCity());
    }
}
