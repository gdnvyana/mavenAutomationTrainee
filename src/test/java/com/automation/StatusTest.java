package com.automation;

import org.testng.Assert;
import org.testng.annotations.Test;

// Тесты для Enum Status (статусы задач)
public class StatusTest {

    // ==================== ПОЗИТИВНЫЕ ТЕСТЫ ====================

    // Тест 1: проверяем отображаемые имена статусов
    @Test(groups = "positive", priority = 1)
    public void testStatusValues() {
        Assert.assertEquals(Status.NEW.getDisplayName(), "New");
        Assert.assertEquals(Status.IN_PROGRESS.getDisplayName(), "In Progress");
        Assert.assertEquals(Status.DONE.getDisplayName(), "Done");
    }

    // Тест 2: проверяем количество статусов (должно быть ровно 3)
    @Test(groups = "positive", priority = 2)
    public void testStatusCount() {
        Status[] statuses = Status.values();
        Assert.assertEquals(statuses.length, 3);
    }

    // Тест 3: проверяем порядок статусов (NEW, IN_PROGRESS, DONE)
    @Test(groups = "positive", priority = 3)
    public void testStatusOrder() {
        Status[] statuses = Status.values();
        Assert.assertEquals(statuses[0], Status.NEW);
        Assert.assertEquals(statuses[1], Status.IN_PROGRESS);
        Assert.assertEquals(statuses[2], Status.DONE);
    }

    // Тест 4: проверяем метод valueOf() для корректных значений
    @Test(groups = "positive", priority = 4)
    public void testValueOfMethod() {
        Assert.assertEquals(Status.valueOf("NEW"), Status.NEW);
        Assert.assertEquals(Status.valueOf("IN_PROGRESS"), Status.IN_PROGRESS);
        Assert.assertEquals(Status.valueOf("DONE"), Status.DONE);
    }

    // ==================== НЕГАТИВНЫЕ ТЕСТЫ ====================

    // Тест 1: проверяем вызов valueOf() с несуществующим статусом
    @Test(groups = "negative", priority = 1,
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = ".*No enum constant.*")
    public void testInvalidStatusValueOf() {
        Status.valueOf("INVALID_STATUS");
    }

    // Тест 2: проверяем вызов valueOf() с null
    @Test(groups = "negative", priority = 2,
            expectedExceptions = NullPointerException.class)
    public void testNullStatusValueOf() {
        Status.valueOf(null);
    }

    // Тест 3: проверяем вызов valueOf() с пустой строкой
    @Test(groups = "negative", priority = 3,
            expectedExceptions = IllegalArgumentException.class)
    public void testEmptyStringValueOf() {
        Status.valueOf("");
    }
}