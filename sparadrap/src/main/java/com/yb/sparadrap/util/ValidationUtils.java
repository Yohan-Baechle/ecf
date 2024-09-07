package com.yb.sparadrap.util;

public class ValidationUtils {

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.matches("[\\p{L}\\s-]+");
    }

    public static boolean isValidStreet(String street) {
        return street != null && !street.trim().isEmpty();
    }

    public static boolean isValidZipCode(String zipCode) {
        return zipCode != null && zipCode.matches("\\d{5}");
    }

    public static boolean isValidCity(String city) {
        return city != null && !city.trim().isEmpty();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("[\\d\\s\\.]+");
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean isValidSocialSecurityNumber(String socialSecurityNumber) {
        if (socialSecurityNumber == null) return false;

        // Supprimer les espaces
        String cleanedNumber = socialSecurityNumber.replaceAll("\\s+", "");

        // Vérifier que le numéro a 15 caractères
        if (cleanedNumber.length() != 15) {
            return false;
        }

        // Extraire les 13 premiers chiffres pour le calcul
        String numberWithoutKey = cleanedNumber.substring(0, 13);

        // Calculer la clé de contrôle
        try {
            long number = Long.parseLong(numberWithoutKey);
            int expectedKey = 97 - (int) (number % 97);
            if (expectedKey == 97) {
                expectedKey = 0; // Si la clé calculée est 97, la clé attendue est 0
            }

            // Comparer avec la clé extraite
            String providedKey = cleanedNumber.substring(13);
            return Integer.parseInt(providedKey) == expectedKey;
        } catch (NumberFormatException e) {
            // Si la conversion échoue, retourner false
            return false;
        }
    }
}
