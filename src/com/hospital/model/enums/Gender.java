package com.hospital.model.enums;

public enum Gender {
    MALE,
    FEMALE,
    OTHER;

    public static Gender fromString(String text) {
        if (text == null) return OTHER;
        for (Gender g : Gender.values()) {
            if (g.name().equalsIgnoreCase(text.trim())) {
                return g;
            }
        }
        return OTHER;
    }
}
