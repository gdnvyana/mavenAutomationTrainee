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
        // Создаем сканер для ввода с клавиатуры
        Scanner scanner = new Scanner(System.in);

        // Приветствие пользователя
        String userName = greetUser(scanner);

        // Загружаем задачи из файла
        List<Issue> issues = loadIssuesFromFile();

        // Если загрузка вернула null (ошибка), создаем новый пустой список
        if (issues == null) {
            issues = new ArrayList<>();
            System.out.println("️Created new empty list (load returned null)");
        }

        // Выводим количество загруженных задач
        System.out.println("Loaded " + issues.size() + " issues from file.");

        // Запускаем основной цикл программы
        runMainLoop(scanner, userName, issues);

        // Сохраняем задачи в файл перед выходом
        saveIssuesToFile(issues);
        System.out.println("Issues saved to file.");

        // Закрываем сканер для освобождения ресурсов
        scanner.close();
    }


    // Приветствие пользователя и запрос имени
    private static String greetUser(Scanner scanner) {
        System.out.println("Hi! I'm a task tracker.");
        System.out.print("Enter your name: ");
        String userName = scanner.nextLine();
        System.out.println(userName + ", Welcome to the task tracker!");
        return userName;
    }

    // Отображает меню и обрабатывает выбор пользователя
    private static void runMainLoop(Scanner scanner, String userName, List<Issue> issues) {
        int choice;

        do {
            // Показываем меню и получаем выбор
            choice = showMenuAndGetChoice(scanner, userName);
            // Обрабатываем выбор
            processChoice(scanner, choice, issues, userName);
            // Повторяем, пока пользователь не выберет "Выход"
        } while (choice != MENU_EXIT);
    }


    // Отображает меню и возвращает выбор пользователя
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


    // Обрабатывает выбор пользователя и вызывает соответствующий метод
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


    // Добавляет новый баг (ошибку) в список
    private static void addBug(Scanner scanner, List<Issue> issues, String userName) {
        System.out.println("\n--- Adding New Bug ---");

        // Запрашиваем данные у пользователя
        String name = getStringInput(scanner, "Enter bug name: ");
        String description = getStringInput(scanner, "Enter description: ");
        int priority = getPriorityInput(scanner);
        String steps = getStringInput(scanner, "Enter steps to reproduce: ");
        String severity = getSeverityInput(scanner);

        // Создаем новый объект Bug и добавляем в список
        Bug bug = new Bug(name, description, priority, steps, severity);
        issues.add(bug);

        System.out.println(userName + ", Bug added successfully!");
    }

    // Добавляет новую стори (Story) в список
    private static void addStory(Scanner scanner, List<Issue> issues, String userName) {
        System.out.println("\n--- Adding New Story ---");

        // Запрашиваем данные у пользователя
        String name = getStringInput(scanner, "Enter story name: ");
        String description = getStringInput(scanner, "Enter description: ");
        int priority = getPriorityInput(scanner);
        String criteria = getStringInput(scanner, "Enter acceptance criteria: ");
        double hours = getHoursInput(scanner);
        String assignee = getStringInput(scanner, "Enter assignee name: ");

        // Создаем новый объект Story и добавляем в список
        Story story = new Story(name, description, priority, criteria, hours, assignee);
        issues.add(story);

        System.out.println(userName + ", Story added successfully!");
    }


    // Отображает все добавленные задачи
    private static void showAllIssues(List<Issue> issues) {
        if (issues.isEmpty()) {
            System.out.println("\nNo issues added yet.");
            return;
        }

        // Выводим все задачи с нумерацией
        System.out.println("\n========== ALL ISSUES ==========");
        for (int i = 0; i < issues.size(); i++) {
            issues.get(i).displayInfo(i + 1);
        }
        System.out.println("\nTotal issues: " + issues.size());
        System.out.println("================================");
    }


    // Изменяет статус выбранной задачи
    private static void changeIssueStatus(Scanner scanner, List<Issue> issues) {

        if (issues.isEmpty()) {
            System.out.println("\nNo issues to update.");
            return;
        }

        System.out.println("\n--- Change Issue Status ---");

        // Показываем список всех задач с их текущими статусами
        for (int i = 0; i < issues.size(); i++) {
            System.out.println((i + 1) + ". [" + issues.get(i).getType() + "] "
                    + issues.get(i).getName() + " - Current status: "
                    + issues.get(i).getStatus().getDisplayName());
        }

        // Запрашиваем номер задачи
        System.out.print("Select issue number: ");
        int issueNumber = scanner.nextInt();
        scanner.nextLine();

        // Проверяем корректность номера
        if (issueNumber < 1 || issueNumber > issues.size()) {
            System.out.println("Invalid issue number!");
            return;
        }

        // Получаем выбранную задачу
        Issue selectedIssue = issues.get(issueNumber - 1);

        // Запрашиваем новый статус
        System.out.println("\nSelect new status:");
        System.out.println("  1 - New");
        System.out.println("  2 - In Progress");
        System.out.println("  3 - Done");
        System.out.print("Your choice: ");

        int statusChoice = scanner.nextInt();
        scanner.nextLine();

        // Определяем новый статус на основе выбора
        Status newStatus = switch (statusChoice) {
            case 1 -> Status.NEW;
            case 2 -> Status.IN_PROGRESS;
            case 3 -> Status.DONE;
            default -> {
                System.out.println("Invalid choice! Status unchanged.");
                yield selectedIssue.getStatus();
            }
        };

        // Если статус изменился - обновляем
        if (newStatus != selectedIssue.getStatus()) {
            selectedIssue.setStatus(newStatus);
            System.out.println("Status updated to: " + newStatus.getDisplayName());
        }
    }


    // Сохраняет список задач в файл
    private static void saveIssuesToFile(List<Issue> issues) {
        // Проверяем, что список не null
        if (issues == null) {
            System.out.println("Cannot save: issues list is null");
            return;
        }

        // Используем try-with-resources для автоматического закрытия потока
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(issues);
            System.out.println("Saved " + issues.size() + " issues");
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Загружает список задач из файла
    @SuppressWarnings("unchecked")
    private static List<Issue> loadIssuesFromFile() {
        File file = new File(SAVE_FILE);

        // Если файла не существует - создаем новый пустой список
        if (!file.exists()) {
            System.out.println("📂 No save file found. Starting fresh.");
            return new ArrayList<>();
        }

        // Пытаемся загрузить данные из файла
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();

            // Проверяем, что объект является списком
            if (obj instanceof List) {
                List<Issue> loaded = (List<Issue>) obj;
                System.out.println("Loaded " + loaded.size() + " issues");
                return loaded;
            } else {
                System.out.println("File contains invalid data. Starting fresh.");
                return new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Starting fresh.");
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("IO Error loading: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found error: " + e.getMessage());
            System.out.println("This may be due to changes in class definitions.");
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    // Запрашивает у пользователя строковый ввод
    private static String getStringInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // Запрашивает у пользователя приоритет задачи (1-10)
    private static int getPriorityInput(Scanner scanner) {
        System.out.print("Enter priority (1-10): ");
        int priority = scanner.nextInt();
        scanner.nextLine();
        return priority;
    }

    // Запрашивает у пользователя количество часов
    private static double getHoursInput(Scanner scanner) {
        System.out.print("Enter estimated hours: ");
        double hours = scanner.nextDouble();
        scanner.nextLine();
        return hours;
    }

    // Запрашивает у пользователя серьезность бага
    private static String getSeverityInput(Scanner scanner) {
        System.out.println("Select severity:");
        System.out.println("  1 - Blocker");
        System.out.println("  2 - Critical");
        System.out.println("  3 - Major");
        System.out.println("  4 - Minor");
        System.out.println("  5 - Trivial");
        System.out.print("Your choice (1-5): ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        // Возвращаем соответствующую константу из класса Bug
        return switch (choice) {
            case 1 -> Bug.SEVERITY_BLOCKER;
            case 2 -> Bug.SEVERITY_CRITICAL;
            case 3 -> Bug.SEVERITY_MAJOR;
            case 4 -> Bug.SEVERITY_MINOR;
            case 5 -> Bug.SEVERITY_TRIVIAL;
            default -> Bug.SEVERITY_MAJOR;
        };
    }

    // Выводит прощальное сообщение
    private static void exitProgram(String userName) {
        System.out.println("\nGoodbye, " + userName + "! Have a nice day!");
    }

    // Выводит сообщение о неверном выборе в меню
    private static void showInvalidChoiceMessage() {
        System.out.println("\nInvalid choice! Please select 1, 2, 3, 4, or 0.");
    }
}