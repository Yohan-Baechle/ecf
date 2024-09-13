package com.yb.sparadrap.model.enums;

/**
 * Énumération représentant les spécialités médicales avec leur nom affichable.
 */
public enum Specialty {
    GENERALISTE("Générale"),
    CARDIOLOGIE("Cardiologie"),
    DERMATOLOGIE("Dermatologie"),
    GYNECOLOGIE("Gynécologie"),
    NEUROLOGIE("Neurologie"),
    PEDIATRIE("Pédiatrie"),
    RADIOLOGIE("Radiologie"),
    ORTHOPEDIE("Orthopédie"),
    PSYCHIATRIE("Psychiatrie"),
    ONCOLOGIE("Oncologie"),
    OPHTALMOLOGIE("Ophtalmologie"),
    GASTROENTEROLOGIE("Gastroentérologie"),
    ENDOCRINOLOGIE("Endocrinologie"),
    RHEUMATOLOGIE("Rhumatologie"),
    PNEUMOLOGIE("Pneumologie"),
    UROLOGIE("Urologie"),
    NEPHROLOGIE("Néphrologie"),
    HEMATOLOGIE("Hématologie"),
    IMMUNOLOGIE("Immunologie"),
    GERONTOLOGIE("Gérontologie"),
    INFECTIOLOGIE("Infectiologie"),
    ANESTHESIOLOGIE("Anesthésiologie");

    private final String displayName;

    /**
     * Constructeur de l'énumération Specialty.
     *
     * @param displayName Le nom affichable de la spécialité.
     */
    Specialty(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Retourne le nom affichable de la spécialité.
     *
     * @return Le nom affichable.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Retourne le nom affichable de la spécialité lorsque toString() est appelée.
     *
     * @return Le nom affichable de la spécialité.
     */
    @Override
    public String toString() {
        return getDisplayName();
    }
}
