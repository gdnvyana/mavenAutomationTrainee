package com.automation;

import com.automation.Issue;
import com.automation.SeverityBug;

import java.io.Serializable;

// Класс для багов (наследник Issue)
public class Bug extends Issue implements Serializable {
    private static final long serialVersionUID = 1L;

    private String stepsToReproduce;
    private SeverityBug severity;  //теперь Enum SeverityBug

    // Конструктор + вызов конструктора родителя
    public Bug(String name, String description, int priority,
               String stepsToReproduce, SeverityBug severity) {
        super(name, description, priority);
        this.stepsToReproduce = stepsToReproduce;
        this.severity = severity;
    }

    // Геттеры
    public String getStepsToReproduce() {
        return stepsToReproduce;
    }

    public SeverityBug getSeverity() {  //возвращает SeverityBug
        return severity;
    }

    // Сеттеры
    public void setStepsToReproduce(String stepsToReproduce) {
        this.stepsToReproduce = stepsToReproduce;
    }

    public void setSeverity(SeverityBug severity) {  //принимает SeverityBug
        this.severity = severity;
    }

    // Переопределенные методы
    @Override
    public String getType() {
        return "Bug";
    }

    @Override
    public String getSpecificInfo() {
        return "Severity: " + (severity != null ? severity.getDisplayName() : "null");
    }
}