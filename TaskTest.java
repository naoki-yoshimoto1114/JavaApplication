import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isExpired() throws ParseException {
        Task task1 = new Task("タスク1", "2023/06/01");
        assertEquals(true, task1.isExpired());
        Task task2 = new Task("タスク2", "2023/05/30");
        assertEquals(true, task2.isExpired());
        Task task3 = new Task("タスク3", "2023/06/10");
        assertEquals(false, task3.isExpired());
    }

    @Test
    void onDeadLine() {
        Task task1 = new Task("タスク1", "2023/06/07");
        assertEquals(false, task1.onDeadLine());
        Task task2 = new Task("タスク2", "2023/06/05");
        assertEquals(true, task2.onDeadLine());
        Task task3 = new Task("タスク3", "2023/05/30");
        assertEquals(false, task3.onDeadLine());
    }

    @Test
    void countRemainingDays() throws ParseException {
        Task task1 = new Task("タスク1", "2023/06/10");
        assertEquals(3, task1.countRemainingDays());
        Task task2 = new Task("タスク2", "2023/06/12");
        assertEquals(5, task2.countRemainingDays());
        Task task3 = new Task("タスク3", "2023/06/06");
        assertEquals(0, task3.countRemainingDays());
        Task task4 = new Task("タスク4", "2023/06/05");
        assertEquals(0, task4.countRemainingDays());
    }

    @Test
    void countRemainingWeekDays() throws ParseException {
        Task task1 = new Task("タスク1", "2023/06/10");
        assertEquals(2, task1.countRemainingWeekDays());
        Task task2 = new Task("タスク2", "2023/06/13");
        assertEquals(4, task2.countRemainingWeekDays());
        Task task3 = new Task("タスク3", "2023/06/01");
        assertEquals(0, task3.countRemainingWeekDays());
    }

    @Test
    void sumOf() {
        int[] array1 = {1, 2, 3, 4, 5};
        assertEquals(15, Task.sumOf(array1));
        int[] array2 = {-1, -2, -3, -4, -5};
        assertEquals(-15, Task.sumOf(array2));
        int[] array3 = {3, 1, 6, 12, 9, 20, 4, 2};
        assertEquals(57, Task.sumOf(array3));
    }

    @Test
    void getSumOfActualTime() {
        Task task1_1 = new Task("タスク1", "2023/05/31", 30);
        Task task1_2 = new Task("タスク2", "2023/06/01", 120);
        Task task1_3 = new Task("タスク3", "2023/06/02", 34);
        Task tasks1[] = {task1_1, task1_2, task1_3};
        assertEquals(184, Task.getSumOfActualTime(tasks1));
        Task task2_1 = new Task("タスク1", "2023/05/29", 10);
        Task task2_2 = new Task("タスク2", "2023/05/30", 90);
        Task task2_3 = new Task("タスク3", "2023/06/07", 32);
        Task tasks2[] = {task2_1, task2_2, task2_3};
        assertEquals(132, Task.getSumOfActualTime(tasks2));
    }

    @Test
    void getSumOfPlannedTime() {
        List<Task> tasks1 = new ArrayList<>();
        tasks1.add(new Task("タスク1", "2023/05/31", 10, 10));
        tasks1.add(new Task("タスク2", "2023/06/01", 30, 40));
        tasks1.add(new Task("タスク3", "2023/06/02", 50, 50));
        assertEquals(100, Task.getSumOfPlannedTime(tasks1));
        List<Task> tasks2 = new ArrayList<>();
        tasks2.add(new Task("タスク1", "2023/05/29", 60, 50));
        tasks2.add(new Task("タスク2", "2023/05/30", 20, 40));
        tasks2.add(new Task("タスク3", "2023/06/07", 90, 50));
        assertEquals(140, Task.getSumOfPlannedTime(tasks2));
    }
}