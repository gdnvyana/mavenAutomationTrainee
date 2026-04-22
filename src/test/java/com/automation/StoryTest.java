package com.automation;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

// Тесты для класса Story
public class StoryTest {

    private Story story;

    // Создаем тестовые данные для Story
    @BeforeMethod
    public void setUp() {
        story = new Story(
                "Список избранного",
                "Добавление товаров в избранное",
                7,
                "Пользователь может добавлять/удалять товары в избранное",
                1.0,
                "Яна Годунова"
        );
    }

    // ==================== ПОЗИТИВНЫЕ ТЕСТЫ ====================

    // Тест 1: проверяем создания объекта Story
    @Test(groups = "positive", priority = 1)
    public void testStoryCreation() {
        Assert.assertNotNull(story);
        Assert.assertEquals(story.getType(), "Story");
        Assert.assertEquals(story.getName(), "Список избранного");
        Assert.assertEquals(story.getPriority(), 7);
    }

    // Тест 2: проверяем получение критериев приемки
    @Test(groups = "positive", priority = 2)
    public void testGetAcceptanceCriteria() {
        String criteria = story.getAcceptanceCriteria();
        Assert.assertNotNull(criteria);
        Assert.assertTrue(criteria.contains("добавлять"));
        Assert.assertTrue(criteria.contains("удалять"));
    }

    // Тест 3: проверяем получение оценочных часов
    @Test(groups = "positive", priority = 3)
    public void testGetEstimatedHours() {
        Assert.assertEquals(story.getEstimatedHours(), 1.0, 0.001);
    }

    // Тест 4: проверяем получение исполнителя
    @Test(groups = "positive", priority = 4)
    public void testGetAssignee() {
        Assert.assertEquals(story.getAssignee(), "Яна Годунова");
    }

    // Тест 5: проверяем статус по умолчанию (должен быть NEW)
    @Test(groups = "positive", priority = 5)
    public void testDefaultStatusIsNew() {
        Assert.assertEquals(story.getStatus(), Status.NEW);
    }

    // Тест 6: проверяем изменения статуса
    @Test(groups = "positive", priority = 6)
    public void testSetStatus() {
        story.setStatus(Status.IN_PROGRESS);
        Assert.assertEquals(story.getStatus(), Status.IN_PROGRESS);
        story.setStatus(Status.DONE);
        Assert.assertEquals(story.getStatus(), Status.DONE);
    }

    // Тест 7: проверяем получение специфичной информации
    @Test(groups = "positive", priority = 7)
    public void testGetSpecificInfo() {
        String info = story.getSpecificInfo();
        Assert.assertNotNull(info);
        Assert.assertTrue(info.contains("Яна Годунова"));
        Assert.assertTrue(info.contains("1.0"));
    }

    // ==================== НЕГАТИВНЫЕ ТЕСТЫ ====================

    // Тест 1: создаем Story с null названием
    @Test(groups = "negative", priority = 1)
    public void testStoryWithNullName() {
        Story storyWithNullName = new Story(null, "Description", 5, "Criteria", 4.0, "Assignee");
        Assert.assertNull(storyWithNullName.getName());
    }

    // Тест 2: создаем Story с пустым названием
    @Test(groups = "negative", priority = 2)
    public void testStoryWithEmptyName() {
        Story storyWithEmptyName = new Story("", "Description", 5, "Criteria", 4.0, "Assignee");
        Assert.assertEquals(storyWithEmptyName.getName(), "");
    }

    // Тест 3: создаем Story с нулевым приоритетом
    @Test(groups = "negative", priority = 3)
    public void testStoryWithZeroPriority() {
        Story storyWithZeroPriority = new Story("Name", "Description", 0, "Criteria", 4.0, "Assignee");
        Assert.assertEquals(storyWithZeroPriority.getPriority(), 0);
    }

    // Тест 4: создаем Story с отрицательным приоритетом
    @Test(groups = "negative", priority = 4)
    public void testStoryWithNegativePriority() {
        Story storyWithNegativePriority = new Story("Name", "Description", -3, "Criteria", 4.0, "Assignee");
        Assert.assertEquals(storyWithNegativePriority.getPriority(), -3);
    }

    // Тест 5: создаем Story с null исполнителем
    @Test(groups = "negative", priority = 5)
    public void testStoryWithNullAssignee() {
        Story storyWithNullAssignee = new Story("Name", "Description", 5, "Criteria", 4.0, null);
        Assert.assertNull(storyWithNullAssignee.getAssignee());
    }

    // Тест 6: создаем Story с отрицательными часами
    @Test(groups = "negative", priority = 6)
    public void testStoryWithNegativeHours() {
        Story storyWithNegativeHours = new Story("Name", "Description", 5, "Criteria", -2.5, "Assignee");
        Assert.assertEquals(storyWithNegativeHours.getEstimatedHours(), -2.5, 0.001);
        Assert.assertTrue(storyWithNegativeHours.getEstimatedHours() < 0);
    }

    // Тест 7: создаем Story с нулевыми часами
    @Test(groups = "negative", priority = 7)
    public void testStoryWithZeroHours() {
        Story storyWithZeroHours = new Story("Name", "Description", 5, "Criteria", 0.0, "Assignee");
        Assert.assertEquals(storyWithZeroHours.getEstimatedHours(), 0.0, 0.001);
    }

    // Тест 8: создаем Story с null критериями
    @Test(groups = "negative", priority = 8)
    public void testStoryWithNullCriteria() {
        Story storyWithNullCriteria = new Story("Name", "Description", 5, null, 4.0, "Assignee");
        Assert.assertNull(storyWithNullCriteria.getAcceptanceCriteria());
    }
}