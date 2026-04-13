package com.automation;

import java.io.Serializable;

// Класс для багов (наследник Issue)
public class Bug extends Issue implements Serializable {
    private static final long serialVersionUID = 1L;

    private String stepsToReproduce;
    private String severity;

    // Константы для уровней серьезности бага
    public static final String SEVERITY_BLOCKER = "Blocker";
    public static final String SEVERITY_CRITICAL = "Critical";
    public static final String SEVERITY_MAJOR = "Major";
    public static final String SEVERITY_MINOR = "Minor";
    public static final String SEVERITY_TRIVIAL = "Trivial";

    // Конструктор + вызов конструктора родителя
    public Bug(String name, String description, int priority,
               String stepsToReproduce, String severity) {
        super(name, description, priority);
        this.stepsToReproduce = stepsToReproduce;
        this.severity = severity;
    }

    // Геттеры
    public String getStepsToReproduce() {
        return stepsToReproduce;
    }

    public String getSeverity() {
        return severity;
    }

    // Переопределенные методы
    @Override
    public String getType() {
        return "Bug";
    }

    @Override
    public String getSpecificInfo() {
        return "Severity: " + severity;
    }
}
