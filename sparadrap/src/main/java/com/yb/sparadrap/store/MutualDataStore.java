package com.yb.sparadrap.store;

import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Mutual;
import com.yb.sparadrap.model.enums.Department;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Singleton gérant le stockage des mutuelles dans l'application.
 * Utilise une liste observable pour stocker et manipuler les mutuelles.
 */
public class MutualDataStore {
    private static MutualDataStore instance;
    private final ObservableList<Mutual> mutuals = FXCollections.observableArrayList();

    /**
     * Constructeur privé pour le pattern Singleton.
     * Initialise quelques mutuelles fictives pour le stockage.
     */
    private MutualDataStore() {
        initializeSampleData(); // Ajouter des données fictives
    }

    /**
     * Retourne l'instance unique de MutualDataStore.
     * Si l'instance n'existe pas encore, elle est créée.
     *
     * @return L'instance unique de MutualDataStore.
     */
    public static MutualDataStore getInstance() {
        if (instance == null) {
            instance = new MutualDataStore();
        }
        return instance;
    }

    /**
     * Retourne la liste observable des mutuelles.
     *
     * @return La liste observable des mutuelles.
     */
    public ObservableList<Mutual> getMutuals() {
        return mutuals;
    }

    /**
     * Ajoute une nouvelle mutuelle à la liste.
     *
     * @param mutual La mutuelle à ajouter.
     */
    public void addMutual(Mutual mutual) {
        mutuals.add(mutual);
    }

    /**
     * Supprime une mutuelle de la liste.
     *
     * @param mutual La mutuelle à supprimer.
     */
    public void removeMutual(Mutual mutual) {
        mutuals.remove(mutual);
    }

    /**
     * Recherche une mutuelle par son nom.
     *
     * @param name Le nom de la mutuelle à rechercher.
     * @return La mutuelle correspondant au nom, ou null si aucune correspondance n'est trouvée.
     */
    public Mutual getMutualByName(String name) {
        for (Mutual mutual : mutuals) {
            if (mutual.getName().equals(name)) {
                return mutual;
            }
        }
        return null;
    }

    /**
     * Initialise quelques mutuelles fictives
     */
    private void initializeSampleData() {
        mutuals.addAll(
                new Mutual("Mutuelle MGEN",
                        new Address("3 Square Max Hymans", "75748", "Paris"),
                        Department.PARIS,  // Département avant le numéro de téléphone
                        "0140621220", "contact@mgen.fr", 80.0),

                new Mutual("Mutuelle Harmonie",
                        new Address("143 Rue Blomet", "75015", "Paris"),
                        Department.PARIS,
                        "0144885555", "contact@harmonie.fr", 85.0),

                new Mutual("Mutuelle MACIF",
                        new Address("17-21 Place Etienne Pernet", "75015", "Paris"),
                        Department.PARIS,
                        "0970809809", "contact@macif.fr", 78.0),

                new Mutual("Mutuelle AG2R La Mondiale",
                        new Address("104-110 Boulevard Haussmann", "75008", "Paris"),
                        Department.PARIS,
                        "0144218800", "contact@ag2rlamondiale.fr", 82.0),

                new Mutual("Mutuelle AÉSIO",
                        new Address("37 Rue de Villeneuve", "94200", "Ivry-sur-Seine"),
                        Department.VAL_DE_MARNE,
                        "0149225000", "contact@aesio.fr", 88.0)
        );
    }
}
