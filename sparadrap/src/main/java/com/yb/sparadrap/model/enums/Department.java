package com.yb.sparadrap.model.enums;

/**
 * Énumération représentant les départements avec leur code associé.
 */
public enum Department {
    AIN("01"),
    AISNE("02"),
    ALLIER("03"),
    ALPES_DE_HAUTE_PROVENCE("04"),
    HAUTES_ALPES("05"),
    ALPES_MARITIMES("06"),
    ARDECHE("07"),
    ARDENNES("08"),
    ARIEGE("09"),
    AUBE("10"),
    AUDE("11"),
    AVEYRON("12"),
    BOUCHES_DU_RHONE("13"),
    CALVADOS("14"),
    CANTAL("15"),
    CHARENTE("16"),
    CHARENTE_MARITIME("17"),
    CHER("18"),
    CORREZE("19"),
    CORSE_DU_SUD("2A"),
    HAUTE_CORSE("2B"),
    COTE_D_OR("21"),
    COTES_D_ARMOR("22"),
    CREUSE("23"),
    DORDOGNE("24"),
    DOUBS("25"),
    DROME("26"),
    EURE("27"),
    EURE_ET_LOIR("28"),
    FINISTERE("29"),
    GARD("30"),
    HAUTE_GARONNE("31"),
    GERS("32"),
    GIRONDE("33"),
    HERAULT("34"),
    ILLE_ET_VILAINE("35"),
    INDRE("36"),
    INDRE_ET_LOIRE("37"),
    ISERE("38"),
    JURA("39"),
    LANDES("40"),
    LOIR_ET_CHER("41"),
    LOIRE("42"),
    HAUTE_LOIRE("43"),
    LOIRE_ATLANTIQUE("44"),
    LOIRET("45"),
    LOT("46"),
    LOT_ET_GARONNE("47"),
    LOZERE("48"),
    MAINE_ET_LOIRE("49"),
    MANCHE("50"),
    MARNE("51"),
    HAUTE_MARNE("52"),
    MAYENNE("53"),
    MEURTHE_ET_MOSELLE("54"),
    MEUSE("55"),
    MORBIHAN("56"),
    MOSELLE("57"),
    NIEVRE("58"),
    NORD("59"),
    OISE("60"),
    ORNE("61"),
    PAS_DE_CALAIS("62"),
    PUY_DE_DOME("63"),
    PYRENEES_ATLANTIQUES("64"),
    HAUTES_PYRENEES("65"),
    PYRENEES_ORIENTALES("66"),
    BAS_RHIN("67"),
    HAUT_RHIN("68"),
    RHONE("69"),
    HAUTE_SAONE("70"),
    SAONE_ET_LOIRE("71"),
    SARTHE("72"),
    SAVOIE("73"),
    HAUTE_SAVOIE("74"),
    PARIS("75"),
    SEINE_MARITIME("76"),
    SEINE_ET_MARNE("77"),
    YVELINES("78"),
    DEUX_SEVRES("79"),
    SOMME("80"),
    TARN("81"),
    TARN_ET_GARONNE("82"),
    VAR("83"),
    VAUCLUSE("84"),
    VENDEE("85"),
    VIENNE("86"),
    HAUTE_VIENNE("87"),
    VOSGES("88"),
    YONNE("89"),
    TERRITOIRE_DE_BELFORT("90"),
    ESSONNE("91"),
    HAUTS_DE_SEINE("92"),
    SEINE_SAINT_DENIS("93"),
    VAL_DE_MARNE("94"),
    VAL_D_OISE("95"),
    GUADELOUPE("971"),
    MARTINIQUE("972"),
    GUYANE("973"),
    REUNION("974"),
    MAYOTTE("976");

    /**
     * Constructeur de l'énumération Department.
     *
     * @param code Le code du département.
     */
    Department(String code) {
    }

    /**
     * Retourne le nom formaté du département, remplaçant les underscores par des espaces et capitalisant chaque mot.
     *
     * @return Le nom formaté du département.
     */
    public String getDisplayName() {
        String name = name().replace("_", " ");
        String[] parts = name.split(" ");
        StringBuilder formattedName = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                formattedName.append(part.substring(0, 1).toUpperCase())
                        .append(part.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return formattedName.toString().trim();
    }

    /**
     * Retourne le nom formaté du département lorsque la méthode toString() est appelée.
     *
     * @return Le nom formaté du département.
     */
    @Override
    public String toString() {
        return getDisplayName();
    }
}
