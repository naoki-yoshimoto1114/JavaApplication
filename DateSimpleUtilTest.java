import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateSimpleUtilTest {

    @Test
    void isWeekDay() throws Exception{
        assertEquals(false, DateSimpleUtil.isWeekDay("2023/05/14"));
        assertEquals(true, DateSimpleUtil.isWeekDay("2023/05/15"));
        assertEquals(true, DateSimpleUtil.isWeekDay("2023/05/16"));
        assertEquals(true, DateSimpleUtil.isWeekDay("2023/05/17"));
        assertEquals(true, DateSimpleUtil.isWeekDay("2023/05/18"));
        assertEquals(true, DateSimpleUtil.isWeekDay("2023/05/19"));
        assertEquals(false, DateSimpleUtil.isWeekDay("2023/05/20"));
        assertEquals(false, DateSimpleUtil.isWeekDay("2023/05/21"));
        assertEquals(true, DateSimpleUtil.isWeekDay("2023/05/22"));
        assertEquals(true, DateSimpleUtil.isWeekDay("2023/05/23"));
        assertEquals(false, DateSimpleUtil.isWeekDay("2023/05/27"));
        assertEquals(false, DateSimpleUtil.isWeekDay("2023/05/28"));
        assertThrows(ParseException.class, () -> DateSimpleUtil.isWeekDay("2023-03-01"));
        assertThrows(ParseException.class, () -> DateSimpleUtil.isWeekDay("アイウエオ"));
        assertThrows(ParseException.class, () -> DateSimpleUtil.isWeekDay("2023-03-1"));
        assertThrows(ParseException.class, () -> DateSimpleUtil.isWeekDay("2023-3-01"));
    }

    @Test
    void isSaturdayOrSunday() throws Exception{
        assertEquals(true, DateSimpleUtil.isSaturdayOrSunday("2023/05/14"));
        assertEquals(false, DateSimpleUtil.isSaturdayOrSunday("2023/05/15"));
        assertEquals(false, DateSimpleUtil.isSaturdayOrSunday("2023/05/16"));
        assertEquals(false, DateSimpleUtil.isSaturdayOrSunday("2023/05/17"));
        assertEquals(false, DateSimpleUtil.isSaturdayOrSunday("2023/05/18"));
        assertEquals(false, DateSimpleUtil.isSaturdayOrSunday("2023/05/19"));
        assertEquals(true, DateSimpleUtil.isSaturdayOrSunday("2023/05/20"));
        assertEquals(true, DateSimpleUtil.isSaturdayOrSunday("2023/05/21"));
        assertEquals(false, DateSimpleUtil.isSaturdayOrSunday("2023/05/22"));
        assertEquals(false, DateSimpleUtil.isSaturdayOrSunday("2023/05/23"));
        assertEquals(true, DateSimpleUtil.isSaturdayOrSunday("2023/05/27"));
        assertEquals(true, DateSimpleUtil.isSaturdayOrSunday("2023/05/28"));
    }

    @Test
    void validateAndParseDate() throws ParseException {
        String inputDate = "2023-05-07";
        assertThrows(ParseException.class, () -> DateSimpleUtil.validateAndParseDate(inputDate));
        String inputDate2 = "2023/05/07";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 5 - 1, 7, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertEquals(calendar.getTime(), DateSimpleUtil.validateAndParseDate(inputDate2));
    }

    @Test
    void getDaysBetweenDates() throws ParseException {
        Date date1_1 = DateSimpleUtil.validateAndParseDate("2023/03/04");
        Date date1_2 = DateSimpleUtil.validateAndParseDate("2023/03/06");
        assertEquals(2, DateSimpleUtil.countDaysBetween(date1_1, date1_2));
        Date date2_1 = DateSimpleUtil.validateAndParseDate("2023/03/30");
        Date date2_2 = DateSimpleUtil.validateAndParseDate("2023/04/01");
        assertEquals(2, DateSimpleUtil.countDaysBetween(date2_1, date2_2));
        Date date3_1 = DateSimpleUtil.validateAndParseDate("2023/03/30");
        Date date3_2 = DateSimpleUtil.validateAndParseDate("2023/04/04");
        assertEquals(5, DateSimpleUtil.countDaysBetween(date3_1, date3_2));
        Date date4_1 = DateSimpleUtil.validateAndParseDate("2023/03/30");
        Date date4_2 = DateSimpleUtil.validateAndParseDate("2023/04/30");
        assertEquals(31, DateSimpleUtil.countDaysBetween(date4_1, date4_2));
    }

    @Test
    void getDaysStrBetween() throws ParseException {
        String[] actual1 = DateSimpleUtil.getDaysStrBetween("2023/05/11", "2023/05/21");
        String[] expected1 = {
                "2023/05/11",
                "2023/05/12",
                "2023/05/13",
                "2023/05/14",
                "2023/05/15",
                "2023/05/16",
                "2023/05/17",
                "2023/05/18",
                "2023/05/19",
                "2023/05/20",
                "2023/05/21"
        };
        assertArrayEquals(expected1, actual1);

        String[] actual2 = DateSimpleUtil.getDaysStrBetween("2022/12/27", "2023/01/04");
        String[] expected2 = {
                "2022/12/27",
                "2022/12/28",
                "2022/12/29",
                "2022/12/30",
                "2022/12/31",
                "2023/01/01",
                "2023/01/02",
                "2023/01/03",
                "2023/01/04"
        };
        assertArrayEquals(expected2, actual2);
    }

    @Test
    void countWorkingDays() throws URISyntaxException, IOException, InterruptedException, ParseException {
        assertEquals(2, DateSimpleUtil.countWorkingDays("2023/05/11", "2023/05/12"));
        assertEquals(6, DateSimpleUtil.countWorkingDays("2023/05/11", "2023/05/18"));
        assertEquals(13, DateSimpleUtil.countWorkingDays("2023/05/23", "2023/06/08"));
        assertEquals(7, DateSimpleUtil.countWorkingDays("2023/06/03", "2023/06/13"));
    }
}