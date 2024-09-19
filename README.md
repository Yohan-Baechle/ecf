# Sparadrap

![Démonstration de l'application](https://github.com/Yohan-Baechle/ecf/blob/main/assets/sparadrap-ccp1.gif)

## Description

**Sparadrap** est un projet réalisé dans le cadre de la formation Concepteur Développeur d'Applications (CDA) à l'AFPA de Pompey. Il s'agit d'un système de gestion de pharmacie développé en **Java** avec **JavaFX** pour l'interface utilisateur. L'application permet de gérer les clients, les médicaments, les médecins, et les achats, avec la possibilité d'ajouter des ordonnances pour certains achats. Le prix total d'un achat est calculé en fonction du prix unitaire, de la quantité et du taux de remboursement de la mutuelle.

Le **dossier de conception** du projet est situé à la racine du dépôt dans le dossier `analyse`.

## Fonctionnalités principales

- **Gestion des clients** : Ajouter, modifier et supprimer des clients.
- **Gestion des médicaments** : Gestion du stock de médicaments.
- **Gestion des médecins** : Ajout et modification des médecins.
- **Gestion des mutuelles** : Gestion et modification des mutuelles.
- **Gestion des ordonnances** : Gestion et modification des orodnnances.
- **Achats** : 
  - Création d'achats avec ou sans ordonnance.
  - Ajout de plusieurs médicaments dans un panier.
  - Calcul automatique du prix total.
  - Affichage d'une liste des achats avec possibilité de filtrer et rechercher.

## Technologies utilisées

- **Java 21**
- **JavaFX** pour l'interface graphique
- **Maven** pour la gestion du projet

## Installation

1. Cloner le dépôt :
   ```bash
   git clone https://github.com/votre-repository/sparadrap.git
   cd sparadrap
## Lancer les tests dans IntelliJ IDEA

Pour lancer les tests dans IntelliJ IDEA, suivez les étapes suivantes :

1. **Accéder au dossier des tests** :
   - Dans l'explorateur de projets, localisez le dossier nommé **"tests"**.

2. **Marquer le dossier comme racine de ressources de test** :
   - Faites un **clic droit** sur le dossier **"tests"**.
   - Sélectionnez **"Mark Directory As"**.
   - Dans le sous-menu, choisissez **"Test Resources Root"**. Cela indique à IntelliJ que ce dossier contient des ressources de test.

3. **Exécuter tous les tests** :
   - Une fois le dossier marqué, faites à nouveau un **clic droit** sur le dossier **"tests"**.
   - Sélectionnez **"Run 'All Tests'"**. Cela lancera tous les tests présents dans le dossier.
