package com.automation;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializationTest {

    // Тест 1: проверяем сохранение и загрузку Bug
    @Test(groups = "positive", priority = 1)
    public void testBugSerialization() throws IOException, ClassNotFoundException {
        Bug originalBug = new Bug(
                "Единороги не летают",
                "При попытке полетать единорог отказывается",
                9,
                "1. Оседлать единорога\n2. Попросить взлететь\n3. Стоит на месте",
                SeverityBug.CRITICAL
        );
        originalBug.setStatus(Status.IN_PROGRESS);

        File tempFile = new File("test-bug.dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            oos.writeObject(originalBug);
        }

        Bug loadedBug;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tempFile))) {
            loadedBug = (Bug) ois.readObject();
        }

        tempFile.delete();

        Assert.assertEquals(loadedBug.getName(), originalBug.getName());
        Assert.assertEquals(loadedBug.getDescription(), originalBug.getDescription());
        Assert.assertEquals(loadedBug.getPriority(), originalBug.getPriority());
        Assert.assertEquals(loadedBug.getStepsToReproduce(), originalBug.getStepsToReproduce());
        Assert.assertEquals(loadedBug.getSeverity(), originalBug.getSeverity());
        Assert.assertEquals(loadedBug.getStatus(), originalBug.getStatus());
    }

    // Тест 2: проверяем сохранение и загрузку Story
    @Test(groups = "positive", priority = 2)
    public void testStorySerialization() throws IOException, ClassNotFoundException {
        Story originalStory = new Story(
                "Магический шар предсказаний",
                "Трясешь телефон и получаешь предсказание",
                8,
                "При встряхивании телефона появляется случайное предсказание",
                3.0,
                "Яна Годунова"
        );
        originalStory.setStatus(Status.DONE);

        File tempFile = new File("test-story.dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            oos.writeObject(originalStory);
        }

        Story loadedStory;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tempFile))) {
            loadedStory = (Story) ois.readObject();
        }

        tempFile.delete();

        Assert.assertEquals(loadedStory.getName(), originalStory.getName());
        Assert.assertEquals(loadedStory.getDescription(), originalStory.getDescription());
        Assert.assertEquals(loadedStory.getPriority(), originalStory.getPriority());
        Assert.assertEquals(loadedStory.getAcceptanceCriteria(), originalStory.getAcceptanceCriteria());
        Assert.assertEquals(loadedStory.getEstimatedHours(), originalStory.getEstimatedHours(), 0.001);
        Assert.assertEquals(loadedStory.getAssignee(), originalStory.getAssignee());
        Assert.assertEquals(loadedStory.getStatus(), originalStory.getStatus());
    }

    // Тест 3: проверяем сохранение и загрузку списка задач
    @Test(groups = "positive", priority = 3)
    public void testListSerialization() throws IOException, ClassNotFoundException {
        // 1. Создаём список с разными типами задач
        List<Issue> originalList = new ArrayList<>();

        // Bug 1: Единороги не летают
        originalList.add(new Bug(
                "Единороги не летают",
                "При попытке полетать единорог отказывается",
                9,
                "1. Оседлать единорога\n2. Попросить взлететь\n3. Стоит на месте",
                SeverityBug.CRITICAL
        ));

        // Story: Магический шар предсказаний
        originalList.add(new Story(
                "Магический шар предсказаний",
                "Трясешь телефон и получаешь предсказание",
                8,
                "При встряхивании телефона появляется случайное предсказание",
                3.0,
                "Яна Годунова"
        ));

        // Bug 2: Корзина не очищается
        originalList.add(new Bug(
                "Корзина не очищается",
                "После оформления заказа товары остаются в корзине",
                7,
                "1. Добавить товар в корзину\n2. Оформить заказ\n3. Корзина не пуста",
                SeverityBug.MAJOR
        ));

        // 2. Сохраняем в файл
        File tempFile = new File("test-list.dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            oos.writeObject(originalList);
        }

        // 3. Загружаем из файла
        List<Issue> loadedList;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tempFile))) {
            loadedList = (List<Issue>) ois.readObject();
        }

        // 4. Удаляем временный файл
        tempFile.delete();

        // 5. Проверяем
        Assert.assertEquals(loadedList.size(), originalList.size());
        Assert.assertEquals(loadedList.get(0).getName(), "Единороги не летают");
        Assert.assertEquals(loadedList.get(1).getName(), "Магический шар предсказаний");
        Assert.assertEquals(loadedList.get(2).getName(), "Корзина не очищается");
        Assert.assertTrue(loadedList.get(0) instanceof Bug);
        Assert.assertTrue(loadedList.get(1) instanceof Story);
        Assert.assertTrue(loadedList.get(2) instanceof Bug);
    }
}