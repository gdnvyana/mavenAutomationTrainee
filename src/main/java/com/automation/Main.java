package com.automation;

import java.util.*;
import java.io.*;

public class Main {


    // Константы
    private static final String SAVE_FILE = "tasks.dat";
    private static final int MENU_ADD_BUG = 1;
    private static final int MENU_ADD_STORY = 2;
    private static final int MENU_SHOW_ALL = 3;
    private static final int MENU_CHANGE_STATUS = 4;
    private static final int MENU_EXIT = 0;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String userName = greetUser(scanner);
        List<Issue> issues = loadIssuesFromFile();

        if (issues == null) {
            issues = new ArrayList<>();
            System.out.println("Created new empty list");
        }

        System.out.println("Loaded " + issues.size() + " issues from file.");

        runMainLoop(scanner, userName, issues);

        saveIssuesToFile(issues);
        System.out.println("Issues saved to file.");

        scanner.close();
    }

    private static String greetUser(Scanner scanner) {
        System.out.println("Hi! I'm a task tracker.");
        System.out.print("Enter your name: ");
        String userName = scanner.nextLine();
        System.out.println(userName + ", Welcome to the task tracker!");
        return userName;
    }

    private static void runMainLoop(Scanner scanner, String userName, List<Issue> issues) {
        int choice;
        do {
            choice = showMenuAndGetChoice(scanner, userName);
            processChoice(scanner, choice, issues, userName);
        } while (choice != MENU_EXIT);
    }

    private static int showMenuAndGetChoice(Scanner scanner, String userName) {
        System.out.println("\n" + userName + ", select an option:");
        System.out.println(MENU_ADD_BUG + " - Add Bug");
        System.out.println(MENU_ADD_STORY + " - Add Story");
        System.out.println(MENU_SHOW_ALL + " - Show all issues");
        System.out.println(MENU_CHANGE_STATUS + " - Change issue status");
        System.out.println(MENU_EXIT + " - Exit");
        System.out.print("Your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    private static void processChoice(Scanner scanner, int choice, List<Issue> issues, String userName) {
        switch (choice) {
            case MENU_ADD_BUG:
                addBug(scanner, issues, userName);
                break;
            case MENU_ADD_STORY:
                addStory(scanner, issues, userName);
                break;
            case MENU_SHOW_ALL:
                showAllIssues(issues);
                break;
            case MENU_CHANGE_STATUS:
                changeIssueStatus(scanner, issues);
                break;
            case MENU_EXIT:
                exitProgram(userName);
                break;
            default:
                showInvalidChoiceMessage();
        }
    }

    private static void addBug(Scanner scanner, List<Issue> issues, String userName) {
        System.out.println("\n--- Adding New Bug ---");

        String name = getStringInput(scanner, "Enter bug name: ");
        String description = getStringInput(scanner, "Enter description: ");
        int priority = getPriorityInput(scanner);
        String steps = getStringInput(scanner, "Enter steps to reproduce: ");
        SeverityBug severity = getSeverityInput(scanner);  // ← ИЗМЕНЕНО: Severity вместо String

        Bug bug = new Bug(name, description, priority, steps, severity);
        issues.add(bug);

        System.out.println(userName + ", Bug added successfully!");
    }

    private static void addStory(Scanner scanner, List<Issue> issues, String userName) {
        System.out.println("\n--- Adding New Story ---");

        String name = getStringInput(scanner, "Enter story name: ");
        String description = getStringInput(scanner, "Enter description: ");
        int priority = getPriorityInput(scanner);
        String criteria = getStringInput(scanner, "Enter acceptance criteria: ");
        double hours = getHoursInput(scanner);
        String assignee = getStringInput(scanner, "Enter assignee name: ");

        Story story = new Story(name, description, priority, criteria, hours, assignee);
        issues.add(story);

        System.out.println(userName + ", Story added successfully!");
    }

    private static void showAllIssues(List<Issue> issues) {
        if (issues.isEmpty()) {
            System.out.println("\nNo issues added yet.");
            return;
        }
        System.out.println("\n========== ALL ISSUES ==========");
        for (int i = 0; i < issues.size(); i++) {
            issues.get(i).displayInfo(i + 1);
        }
        System.out.println("\nTotal issues: " + issues.size());
        System.out.println("================================");
    }

    private static void changeIssueStatus(Scanner scanner, List<Issue> issues) {
        if (issues.isEmpty()) {
            System.out.println("\nNo issues to update.");
            return;
        }
        System.out.println("\n--- Change Issue Status ---");
        for (int i = 0; i < issues.size(); i++) {
            System.out.println((i + 1) + ". [" + issues.get(i).getType() + "] "
                    + issues.get(i).getName() + " - Current status: "
                    + issues.get(i).getStatus().getDisplayName());
        }
        System.out.print("Select issue number: ");
        int issueNumber = scanner.nextInt();
        scanner.nextLine();
        if (issueNumber < 1 || issueNumber > issues.size()) {
            System.out.println("Invalid issue number!");
            return;
        }
        Issue selectedIssue = issues.get(issueNumber - 1);
        System.out.println("\nSelect new status:");
        System.out.println("  1 - New");
        System.out.println("  2 - In Progress");
        System.out.println("  3 - Done");
        System.out.print("Your choice: ");
        int statusChoice = scanner.nextInt();
        scanner.nextLine();
        Status newStatus = switch (statusChoice) {
            case 1 -> Status.NEW;
            case 2 -> Status.IN_PROGRESS;
            case 3 -> Status.DONE;
            default -> {
                System.out.println("Invalid choice! Status unchanged.");
                yield selectedIssue.getStatus();
            }
        };
        if (newStatus != selectedIssue.getStatus()) {
            selectedIssue.setStatus(newStatus);
            System.out.println("Status updated to: " + newStatus.getDisplayName());
        }
    }

    // ========== НОВЫЙ МЕТОД ВВОДА SEVERITY (возвращает Enum) ==========
    private static SeverityBug getSeverityInput(Scanner scanner) {
        System.out.println("Select severity:");
        System.out.println("  1 - Blocker");
        System.out.println("  2 - Critical");
        System.out.println("  3 - Major");
        System.out.println("  4 - Minor");
        System.out.println("  5 - Trivial");
        System.out.print("Your choice (1-5): ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        return switch (choice) {
            case 1 -> SeverityBug.BLOCKER;
            case 2 -> SeverityBug.CRITICAL;
            case 3 -> SeverityBug.MAJOR;
            case 4 -> SeverityBug.MINOR;
            case 5 -> SeverityBug.TRIVIAL;
            default -> SeverityBug.MAJOR;
        };
    }

    // ========== МЕТОДЫ РАБОТЫ С ФАЙЛАМИ ==========

    private static void saveIssuesToFile(List<Issue> issues) {
        if (issues == null) {
            System.out.println("Cannot save: issues list is null");
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(issues);
            System.out.println("Saved " + issues.size() + " issues");
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Issue> loadIssuesFromFile() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            System.out.println("No save file found. Starting fresh.");
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                return (List<Issue>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // ========== ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ==========

    private static String getStringInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int getPriorityInput(Scanner scanner) {
        System.out.print("Enter priority (1-10): ");
        int priority = scanner.nextInt();
        scanner.nextLine();
        return priority;
    }

    private static double getHoursInput(Scanner scanner) {
        System.out.print("Enter estimated hours: ");
        double hours = scanner.nextDouble();
        scanner.nextLine();
        return hours;
    }

    private static void exitProgram(String userName) {
        System.out.println("\nGoodbye, " + userName + "! Have a nice day!");
    }

    private static void showInvalidChoiceMessage() {
        System.out.println("\nInvalid choice! Please select 1, 2, 3, 4, or 0.");
    }
}