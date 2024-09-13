package com.yb.sparadrap.model.enums;

/**
 * Énumération représentant les catégories de médicaments avec leur nom affichable.
 */
public enum MedicationCategory {
    ANALGESIQUE("Analgésique"),
    ANTI_INFLAMMATOIRE("Anti-inflammatoire"),
    ANTIVIRAL("Antiviral"),
    ANTIBIOTIQUE("Antibiotique"),
    VACCIN("Vaccin"),
    ANTI_DEPRESSEUR("Antidépresseur"),
    ANTI_HISTAMINIQUE("Antihistaminique"),
    DIURETIQUE("Diurétique"),
    ANTIFONGIQUE("Antifongique"),
    ANTIPYRETIQUE("Antipyrétique"),
    ANTIHYPERTENSEUR("Antihypertenseur"),
    ANTI_DIABETIQUE("Antidiabétique"),
    ANTI_HELMINTIQUE("Antihelmintique"),
    BRONCHODILATATEUR("Bronchodilatateur"),
    CORTICOSTEROIDE("Corticostéroïde"),
    IMMUNOSUPPRESSEUR("Immunosuppresseur"),
    LAXATIF("Laxatif"),
    SEDATIF("Sédatif"),
    STATINE("Statine"),
    VASODILATATEUR("Vasodilatateur");

    private final String displayName;

    MedicationCategory(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Retourne le nom affichable de la catégorie de médicament.
     *
     * @return Le nom affichable.
     */
    public String getDisplayName() {
        return displayName;
    }
    

    /**
     * Retourne le nom affichable de la catégorie lorsque toString() est appelé.
     *
     * @return Le nom affichable de la catégorie.
     */
    @Override
    public String toString() {
        return getDisplayName();
    }
}
