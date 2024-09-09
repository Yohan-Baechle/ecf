package com.yb.sparadrap.util;

import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.model.Doctor;
import com.yb.sparadrap.model.Medication;

import java.time.LocalDate;

public class ValidationUtil {

    // Regex pour un prénom ou nom : lettres avec accents, espaces, et traits d'union (min 2 caractères)
    private static final String NAME_REGEX = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]{2,50}$";
    private static final String PHONE_REGEX = "^((\\+\\d{1,3}[- ]?)?\\d{10})$";
    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

    // Vérifie si une chaîne est vide ou nulle
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    // Validation du nom
    public static String validateLastName(String lastName) {
        if (isEmpty(lastName)) return "Le nom ne peut pas être vide.";
        if (!lastName.matches(NAME_REGEX)) return "Le nom est invalide.";
        return null;
    }

    // Validation du prénom
    public static String validateFirstName(String firstName) {
        if (isEmpty(firstName)) return "Le prénom ne peut pas être vide.";
        if (!firstName.matches(NAME_REGEX)) return "Le prénom est invalide.";
        return null;
    }

    // Validation de l'adresse
    public static String validateStreet(String street) {
        if (isEmpty(street)) return "L'adresse ne peut pas être vide.";
        if (street.length() < 5) return "L'adresse doit avoir au moins 5 caractères.";
        return null;
    }

    // Validation du code postal
    public static String validateZipCode(String zipCode) {
        if (isEmpty(zipCode)) return "Le code postal ne peut pas être vide.";
        if (!zipCode.matches("\\d{5}")) return "Le code postal est invalide.";
        return null;
    }

    // Validation de la ville
    public static String validateCity(String city) {
        if (isEmpty(city)) return "La ville ne peut pas être vide.";
        if (!city.matches(NAME_REGEX)) return "La ville est invalide.";
        return null;
    }

    // Validation du numéro de téléphone
    public static String validatePhoneNumber(String phoneNumber) {
        if (isEmpty(phoneNumber)) return "Le numéro de téléphone ne peut pas être vide.";
        if (!phoneNumber.matches(PHONE_REGEX)) return "Le numéro de téléphone est invalide.";
        return null;
    }

    // Validation de l'email
    public static String validateEmail(String email) {
        if (isEmpty(email)) return "L'email ne peut pas être vide.";
        if (!email.matches(EMAIL_REGEX)) return "L'email est invalide.";
        return null;
    }

    // Validation du numéro de sécurité sociale
    public static String validateSocialSecurityNumber(String ssn) {
        if (isEmpty(ssn)) return "Le n° de sécurité sociale ne peut pas être vide.";
        if (!ssn.matches("\\d{15}")) return "Le n°de sécurité sociale est invalide.";

        String numberWithoutKey = ssn.substring(0, 13);
        try {
            long number = Long.parseLong(numberWithoutKey);
            int expectedKey = 97 - (int) (number % 97);
            String providedKey = ssn.substring(13);
            if (Integer.parseInt(providedKey) != (expectedKey == 97 ? 0 : expectedKey)) {
                return "La clé de sécurité sociale est invalide.";
            }
        } catch (NumberFormatException e) {
            return "Le n° de sécurité sociale est invalide.";
        }
        return null;
    }

    // Validation de la date de naissance
    public static String validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) return "La date de naissance ne peut pas être vide.";
        LocalDate today = LocalDate.now();
        if (!birthDate.isBefore(today.minusYears(18))) return "Le client doit avoir plus de 18 ans.";
        return null;
    }

    // Validation du client
    public static String validateCustomer(Customer customer) {
        if (customer == null) {
            return "Veuillez sélectionner un client.";
        }
        // Tu peux ajouter d'autres règles spécifiques si nécessaire
        return null;
    }

    // Validation du médecin
    public static String validateDoctor(Doctor doctor) {
        if (doctor == null) {
            return "Veuillez sélectionner un prescripteur";
        }
        // Tu peux ajouter d'autres règles spécifiques si nécessaire
        return null;
    }

    // Validation de la quantité
    public static String validateQuantity(String quantity) {
        if (isEmpty(quantity)) return "La quantité ne peut pas être vide.";
        try {
            int qty = Integer.parseInt(quantity);
            if (qty <= 0) return "La quantité doit être un nombre positif.";
        } catch (NumberFormatException e) {
            return "La quantité doit être un nombre valide.";
        }
        return null;
    }

    // Validation de la saisie de la date de prescription
    public static String validatePrescriptionDate(LocalDate prescriptionDate) {
        if (prescriptionDate == null) {
            return "La date de prescription ne peut pas être vide.";
        }
        if (prescriptionDate.isAfter(LocalDate.now())) {
            return "La date de prescription est invalide.";
        }
        return null;
    }



}
