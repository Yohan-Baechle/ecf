package com.yb.sparadrap.model.enums;

public enum Specialty {
    GENERALE("Générale"),
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

    Specialty(String displayName) {
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
