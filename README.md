```markdown
# mavenAutomationTrainee

Автотесты для проекта «Консольный трекер задач» (Java).

## Технологии
- TestNG
- Maven
- Java

## Что тестируется
- Классы Bug, Story, Status
- Позитивные и негативные сценарии
- Сериализация / десериализация объектов
- Исключения (expectedExceptions)

## Как запустить тесты

### Через Maven

```bash
mvn clean test
```

### Только позитивные тесты

```bash
mvn test -Dgroups=positive
```

### Только негативные тесты

```bash
mvn test -Dgroups=negative
```
```
