package com.automation;
import java.io.Serializable;

// Enum для уровней серьезности бага
public enum SeverityBug implements Serializable {
    BLOCKER("Blocker"),
    CRITICAL("Critical"),
    MAJOR("Major"),
    MINOR("Minor"),
    TRIVIAL("Trivial");

    private final String displayName;

    SeverityBug(String displayName) {
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