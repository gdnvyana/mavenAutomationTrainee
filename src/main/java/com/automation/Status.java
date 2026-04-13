package com.automation;

import java.io.Serializable;
// Статусы задач
public enum Status implements Serializable {
    NEW("New"),
    IN_PROGRESS("In Progress"),
    DONE("Done");

    private final String displayName;

    // Конструктор
    Status(String displayName) {
        this.displayName = displayName;
    }

    // Получить отображаемое имя
    public String getDisplayName() {
        return displayName;
    }
}