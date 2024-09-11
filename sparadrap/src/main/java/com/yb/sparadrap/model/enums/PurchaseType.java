package com.yb.sparadrap.model.enums;

public enum PurchaseType {
    DIRECT("Direct"),
    PRESCRIPTION("Avec Ordonnance");

    private final String displayName;

    PurchaseType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
