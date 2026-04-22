package com.automation;

import com.automation.Bug;
import com.automation.SeverityBug;
import com.automation.Status;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

// Тесты для класса Bug (ошибки/баги)
public class BugTest {

    private Bug bug;

    // Создаем тестовые данные для Bug
    @BeforeMethod
    public void setUp() {
        bug = new Bug(
                "Корзина не очищается",
                "После оформления заказа товары остаются в корзине",
                9,
                "1. Добавить товар в корзину\n2. Оформить заказ\n3. Проверить корзину",
                SeverityBug.CRITICAL
        );
    }

    // ==================== ПОЗИТИВНЫЕ ТЕСТЫ ====================

    // Тест 1: проверяем создание объекта Bug
    @Test(groups = "positive", priority = 1)
    public void testBugCreation() {
        Assert.assertNotNull(bug);
        Assert.assertEquals(bug.getType(), "Bug");
        Assert.assertEquals(bug.getName(), "Корзина не очищается");
        Assert.assertEquals(bug.getPriority(), 9);
    }

    // Тест 2: проверяем получение шагов воспроизведения
    @Test(groups = "positive", priority = 2)
    public void testGetStepsToReproduce() {
        String steps = bug.getStepsToReproduce();
        Assert.assertNotNull(steps);
        Assert.assertTrue(steps.contains("Добавить товар в корзину"));
        Assert.assertTrue(steps.contains("Оформить заказ"));
        Assert.assertTrue(steps.contains("Проверить корзину"));
    }

    // Тест 3: проверяем получение серьезности
    @Test(groups = "positive", priority = 3)
    public void testGetSeverity() {
        Assert.assertEquals(bug.getSeverity(), SeverityBug.CRITICAL);
    }

    // Тест 4: проверяем изменение шагов воспроизведения
    @Test(groups = "positive", priority = 4)
    public void testSetStepsToReproduce() {
        String newSteps = "1. New step\n2. Another step";
        bug.setStepsToReproduce(newSteps);
        Assert.assertEquals(bug.getStepsToReproduce(), newSteps);
    }

    // Тест 5: проверяем изменение серьезности
    @Test(groups = "positive", priority = 5)
    public void testSetSeverity() {
        bug.setSeverity(SeverityBug.BLOCKER);
        Assert.assertEquals(bug.getSeverity(), SeverityBug.BLOCKER);
    }

    // Тест 6: проверяем статус по умолчанию (должен быть NEW)
    @Test(groups = "positive", priority = 6)
    public void testDefaultStatusIsNew() {
        Assert.assertEquals(bug.getStatus(), Status.NEW);
    }

    // Тест 7: проверяем изменение статуса
    @Test(groups = "positive", priority = 7)
    public void testSetStatus() {
        bug.setStatus(Status.IN_PROGRESS);
        Assert.assertEquals(bug.getStatus(), Status.IN_PROGRESS);

        bug.setStatus(Status.DONE);
        Assert.assertEquals(bug.getStatus(), Status.DONE);
    }

    // Тест 8: проверяем получение специфичной информации
    @Test(groups = "positive", priority = 8)
    public void testGetSpecificInfo() {
        String info = bug.getSpecificInfo();
        Assert.assertNotNull(info);
        Assert.assertTrue(info.contains(SeverityBug.CRITICAL.getDisplayName()));
    }

    // ==================== НЕГАТИВНЫЕ ТЕСТЫ ====================

    // Тест 1: создаем Bug с null названием
    @Test(groups = "negative", priority = 1)
    public void testBugWithNullName() {
        Bug bugWithNullName = new Bug(null, "Description", 5, "Steps", SeverityBug.MAJOR);
        Assert.assertNull(bugWithNullName.getName());
    }

    // Тест 2: создаем Bug с пустым названием
    @Test(groups = "negative", priority = 2)
    public void testBugWithEmptyName() {
        Bug bugWithEmptyName = new Bug("", "Description", 5, "Steps", SeverityBug.MAJOR);
        Assert.assertEquals(bugWithEmptyName.getName(), "");
    }

    // Тест 3: создаем Bug с нулевым приоритетом
    @Test(groups = "negative", priority = 3)
    public void testBugWithZeroPriority() {
        Bug bugWithZeroPriority = new Bug("Name", "Description", 0, "Steps", SeverityBug.MAJOR);
        Assert.assertEquals(bugWithZeroPriority.getPriority(), 0);
    }

    // Тест 4: создаем Bug с отрицательным приоритетом
    @Test(groups = "negative", priority = 4)
    public void testBugWithNegativePriority() {
        Bug bugWithNegativePriority = new Bug("Name", "Description", -5, "Steps", SeverityBug.MAJOR);
        Assert.assertEquals(bugWithNegativePriority.getPriority(), -5);
        Assert.assertTrue(bugWithNegativePriority.getPriority() < 0);
    }

    // Тест 5: создаем Bug с null шагами воспроизведения
    @Test(groups = "negative", priority = 5)
    public void testBugWithNullSteps() {
        Bug bugWithNullSteps = new Bug("Name", "Description", 5, null, SeverityBug.MAJOR);
        Assert.assertNull(bugWithNullSteps.getStepsToReproduce());
    }

    // Тест 6: создаем Bug с null серьезностью
    @Test(groups = "negative", priority = 6)
    public void testBugWithNullSeverity() {
        Bug bugWithNullSeverity = new Bug("Name", "Description", 5, "Steps", null);
        Assert.assertNull(bugWithNullSeverity.getSeverity());
    }

    // Тест 7: создаем Bug с очень высоким приоритетом (100)
    @Test(groups = "negative", priority = 7)
    public void testBugWithVeryHighPriority() {
        Bug bugWithHighPriority = new Bug("Name", "Description", 100, "Steps", SeverityBug.MAJOR);
        Assert.assertEquals(bugWithHighPriority.getPriority(), 100);
    }
}