package com.yb.sparadrap.model.enums;

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

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
