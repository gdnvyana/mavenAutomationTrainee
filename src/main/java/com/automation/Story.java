package com.automation;
import java.io.Serializable;

// Класс для стори (наследник Issue)
public class Story extends Issue implements Serializable {
    private static final long serialVersionUID = 1L;

    private String acceptanceCriteria;
    private double estimatedHours;
    private String assignee;

    // Конструктор + вызов конструктора родителя
    public Story(String name, String description, int priority,
                 String acceptanceCriteria, double estimatedHours, String assignee) {
        super(name, description, priority);
        this.acceptanceCriteria = acceptanceCriteria;
        this.estimatedHours = estimatedHours;
        this.assignee = assignee;
    }

    // Геттеры
    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public double getEstimatedHours() {
        return estimatedHours;
    }

    public String getAssignee() {
        return assignee;
    }

    // Переопределенные методы
    @Override
    public String getType() {
        return "Story";
    }

    @Override
    public String getSpecificInfo() {
        return "Assignee: " + assignee + " | Est: " + estimatedHours + "h";
    }
}