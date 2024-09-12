package com.yb.sparadrap.util;

import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.model.Doctor;
import com.yb.sparadrap.model.Medication;
import com.yb.sparadrap.model.enums.Department;
import javafx.scene.control.ComboBox;

import java.time.LocalDate;
import java.util.Map;

public class ValidationUtil {

    // Regex pour le nom, n° de téléphone et email
    private static final String NAME_REGEX = "^[A-Za-zÀ-ÖØ-öø-ÿ0-9\\s'-]{2,50}$";
    private static final String PHONE_REGEX = "^((\\+\\d{1,3}[- ]?)?\\d{10})$";
    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

    // Vérifie si une chaîne est vide ou nulle
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    // Validation du nom
    public static String validateLastName(String lastName) {
        if (isEmpty(lastName)) return "Le nom est obligatoire.";
        if (!lastName.matches(NAME_REGEX)) return "Le nom est invalide.";
        return null;
    }

    // Validation du prénom
    public static String validateFirstName(String firstName) {
        if (isEmpty(firstName)) return "Le prénom est obligatoire.";
        if (!firstName.matches(NAME_REGEX)) return "Le prénom est invalide.";
        return null;
    }

    // Validation de l'adresse
    public static String validateStreet(String street) {
        if (isEmpty(street)) return "L'adresse est obligatoire.";
        if (street.length() < 5) return "L'adresse doit avoir au moins 5 caractères.";
        return null;
    }

    // Validation du code postal
    public static String validateZipCode(String zipCode) {
        if (isEmpty(zipCode)) return "Le code postal est obligatoire.";
        if (!zipCode.matches("\\d{5}")) return "Le code postal est invalide.";
        return null;
    }

    // Validation de la ville
    public static String validateCity(String city) {
        if (isEmpty(city)) return "La ville est obligatoire.";
        if (!city.matches(NAME_REGEX)) return "La ville est invalide.";
        return null;
    }

    // Validation du numéro de téléphone
    public static String validatePhoneNumber(String phoneNumber) {
        if (isEmpty(phoneNumber)) return "Le téléphone est obligatoire.";
        if (!phoneNumber.matches(PHONE_REGEX)) return "Le numéro de téléphone est invalide.";
        return null;
    }

    // Validation de l'email
    public static String validateEmail(String email) {
        if (isEmpty(email)) return "L'email est obligatoire.";
        if (!email.matches(EMAIL_REGEX)) return "L'email est invalide.";
        return null;
    }

    // Validation du numéro de sécurité sociale
    public static String validateSocialSecurityNumber(String ssn) {
        if (isEmpty(ssn)) return "Le n° de sécurité sociale est obligatoire.";
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
        if (birthDate == null) return "La date de naissance est obligatoire.";
        LocalDate today = LocalDate.now();
        if (!birthDate.isBefore(today.minusYears(18))) return "Le client doit avoir plus de 18 ans.";
        return null;
    }

    // Validation du client
    public static String validateCustomer(Customer customer, ComboBox<Customer> customerComboBox) {
        // Vérifie si la sélection est vide ou si aucun client n'est sélectionné
        if (customer == null || customerComboBox.getSelectionModel().isEmpty()) {
            return "Veuillez sélectionner un client.";
        }
        return null;
    }


    // Validation du médecin
    public static String validateDoctor(Doctor doctor) {
        if (doctor == null) {
            return "Veuillez sélectionner un prescripteur";
        }
        return null;
    }

    // Validation de la quantité
    public static String validateQuantity(String quantity) {
        if (isEmpty(quantity)) return "La quantité est obligatoire.";
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
            return "La date de prescription est obligatoire.";
        }
        if (prescriptionDate.isAfter(LocalDate.now())) {
            return "La date de prescription est invalide.";
        }
        return null;
    }

    // Validation du numéro d'agrément du médecin
    public static String validateRegistrationNumber(String registrationNumber) {
        if (isEmpty(registrationNumber)) {
            return "Le n° d'agrément est obligatoire.";
        }
        // Un numéro d'agrément de médecin en France est généralement composé de chiffres (15 caractères)
        if (!registrationNumber.matches("\\d{11}")) {
            return "Le numéro d'agrément est invalide. Il doit contenir 11 chiffres.";
        }
        return null;
    }

    // Validation du nom du médicament
    public static String validateName(String name) {
        if (isEmpty(name)) return "Veuillez saisir un nom.";
        if (!name.matches(NAME_REGEX)) return "Le nom est invalide.";
        return null;
    }

    // Validation de la catégorie du médicament
    public static String validateCategory(Object category) {
        if (category == null) {
            return "La catégorie est obligatoire.";
        }
        // Vous pouvez ajouter des validations spécifiques à la catégorie si nécessaire
        return null;
    }

    // Validation du prix du médicament
    public static String validatePrice(String price) {
        if (isEmpty(price)) return "Le prix est obligatoire.";
        try {
            double parsedPrice = Double.parseDouble(price);
            if (parsedPrice < 0) return "Le prix doit être un nombre positif.";
        } catch (NumberFormatException e) {
            return "Le prix doit être un nombre valide.";
        }
        return null;
    }

    // Validation de la date de mise en service du médicament
    public static String validateLaunchDate(LocalDate launchDate) {
        if (launchDate == null) return "La date est obligatoire.";
        if (launchDate.isAfter(LocalDate.now())) return "La date est invalide.";
        return null;
    }

    // Validation du département
    public static String validateDepartment(Department department) {
        if (department == null) {
            return "Le département est obligatoire.";
        }
        return null;
    }

    // Validation du taux de remboursement
    public static String validateReimbursementRate(String reimbursementRate) {
        if (isEmpty(reimbursementRate)) {
            return "Le taux est obligatoire.";
        }
        try {
            double rate = Double.parseDouble(reimbursementRate);
            if (rate < 0 || rate > 100) {
                return "Le taux doit être compris entre 0 et 100.";
            }
        } catch (NumberFormatException e) {
            return "Le taux doit être un nombre valide.";
        }
        return null;
    }

    // Validation du panier de médicaments
    public static String validateBasket(Map<Medication, Integer> medicationBasket) {
        if (medicationBasket == null || medicationBasket.isEmpty()) {
            return "Le panier ne peut pas être vide.";
        }

        for (Map.Entry<Medication, Integer> entry : medicationBasket.entrySet()) {
            if (entry.getValue() <= 0) {
                return "La quantité doit être supérieure à 0.";
            }
        }

        return null; // Le panier est valide
    }
}
