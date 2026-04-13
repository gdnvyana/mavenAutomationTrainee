package com.automation;

import java.io.Serializable;

// Абстрактный класс
public abstract class Issue implements Serializable {
    private static final long serialVersionUID = 1L;

    // Поля класса
    private String name;
    private String description;
    private int priority;
    private Status status;

    // Конструктор
    public Issue(String name, String description, int priority) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = Status.NEW;
    }

    // Геттеры (получение значений)
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    // Сеттеры (изменение значений)
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // Абстрактные методы (реализуются в Bug и Story)
    public abstract String getType();           // Тип задачи

    public abstract String getSpecificInfo();   // Специфичная информация

    // Вывод информации о задаче
    public void displayInfo(int number) {
        System.out.println("\n[" + getType() + "] #" + number);
        System.out.println("  Name: " + name);
        System.out.println("  Description: " + description);
        System.out.println("  Priority: " + priority + "/10");
        System.out.println("  Status: " + status.getDisplayName());
        System.out.println("  Details: " + getSpecificInfo());
    }
}