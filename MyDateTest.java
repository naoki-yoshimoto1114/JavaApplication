import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MyDateTest {
    MyDate md;

    MyDateTest() {
        md = new MyDate();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void validateAndParseDate() throws ParseException {
        String inputDate = "2023/03/02";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date expectedDate = sdf.parse(inputDate);
        Date actualDate = MyDate.validateAndParseDate(inputDate);
        assertEquals(actualDate, expectedDate);
    }

    @Test
    void addDaysToDate() throws ParseException{
        Date date = MyDate.validateAndParseDate("2023/05/08");
        Date actual = MyDate.addDaysToDate(date, 20);
        Date expected = MyDate.validateAndParseDate("2023/05/28");

        assertEquals(expected, actual);

        Date date2 = MyDate.validateAndParseDate("2023/05/08");
        Date actual2 = MyDate.addDaysToDate(date2, -5);
        Date expected2 = MyDate.validateAndParseDate("2023/05/03");

        assertEquals(expected2, actual2);
    }

    @Test
    void getDaysBetweenDates() throws ParseException {
        Date date1_1 = MyDate.validateAndParseDate("2023/03/04");
        Date date1_2 = MyDate.validateAndParseDate("2023/03/06");
        assertEquals(2, MyDate.getDaysBetweenDates(date1_1, date1_2));
        Date date2_1 = MyDate.validateAndParseDate("2023/03/30");
        Date date2_2 = MyDate.validateAndParseDate("2023/04/01");
        assertEquals(2, MyDate.getDaysBetweenDates(date2_1, date2_2));
        Date date3_1 = MyDate.validateAndParseDate("2023/05/31");
        Date date3_2 = MyDate.validateAndParseDate("2023/06/01");
        assertEquals(1, MyDate.getDaysBetweenDates(date3_1, date3_2));
        Date date4_1 = MyDate.validateAndParseDate("2023/05/31");
        Date date4_2 = MyDate.validateAndParseDate("2023/05/31");
        assertEquals(0, MyDate.getDaysBetweenDates(date4_1, date4_2));
    }

    @Test
    void isWeekday() {
        assertEquals(false, MyDate.isWeekday("2023/05/14"));
        assertEquals(true, MyDate.isWeekday("2023/05/15"));
        assertEquals(true, MyDate.isWeekday("2023/05/17"));
        assertEquals(true, MyDate.isWeekday("2023/05/19"));
        assertEquals(false, MyDate.isWeekday("2023/05/20"));
    }

    @Test
    void isSaturdayOrSunday() {
        assertEquals(false, MyDate.isSaturdayOrSunday("2023/05/17"));
        assertEquals(false, MyDate.isSaturdayOrSunday("2023/05/18"));
        assertEquals(false, MyDate.isSaturdayOrSunday("2023/05/19"));
        assertEquals(true, MyDate.isSaturdayOrSunday("2023/05/20"));
        assertEquals(true, MyDate.isSaturdayOrSunday("2023/05/21"));
    }

    @Test
    void countWorkingDays() throws URISyntaxException, IOException, InterruptedException, ParseException {
        assertEquals(2, MyDate.countWorkingDays("2023/05/11", "2023/05/12"));
        assertEquals(6, MyDate.countWorkingDays("2023/05/11", "2023/05/18"));
        assertEquals(13, MyDate.countWorkingDays("2023/05/23", "2023/06/08"));
        assertEquals(7, MyDate.countWorkingDays("2023/06/03", "2023/06/13"));
    }

    @Test
    void getNextWorkingDayOf() throws ParseException {
        assertEquals("2023/05/24", MyDate.getNextWorkingDayOf("2023/05/23"));
        assertEquals("2023/05/29", MyDate.getNextWorkingDayOf("2023/05/26"));
        assertEquals("2023/06/05", MyDate.getNextWorkingDayOf("2023/06/02"));
        assertEquals("2023/06/06", MyDate.getNextWorkingDayOf("2023/06/05"));
    }
}