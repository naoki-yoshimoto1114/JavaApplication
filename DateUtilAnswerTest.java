import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilAnswerTest {

    @Test
    void isWeekDay() throws Exception{
        assertEquals(false, DateUtilAnswer.isWeekDay("2023/05/14"));
        assertEquals(true, DateUtilAnswer.isWeekDay("2023/05/15"));
        assertEquals(true, DateUtilAnswer.isWeekDay("2023/05/16"));
        assertEquals(true, DateUtilAnswer.isWeekDay("2023/05/17"));
        assertEquals(true, DateUtilAnswer.isWeekDay("2023/05/18"));
        assertEquals(true, DateUtilAnswer.isWeekDay("2023/05/19"));
        assertEquals(false, DateUtilAnswer.isWeekDay("2023/05/20"));
        assertEquals(false, DateUtilAnswer.isWeekDay("2023/05/21"));
        assertEquals(true, DateUtilAnswer.isWeekDay("2023/05/22"));
        assertEquals(true, DateUtilAnswer.isWeekDay("2023/05/23"));
        assertEquals(false, DateUtilAnswer.isWeekDay("2023/05/27"));
        assertEquals(false, DateUtilAnswer.isWeekDay("2023/05/28"));
        assertThrows(ParseException.class, () -> DateUtilAnswer.isWeekDay("2023-03-01"));
        assertThrows(ParseException.class, () -> DateUtilAnswer.isWeekDay("アイウエオ"));
        assertThrows(ParseException.class, () -> DateUtilAnswer.isWeekDay("2023-03-1"));
        assertThrows(ParseException.class, () -> DateUtilAnswer.isWeekDay("2023-3-01"));
    }

    @Test
    void isSaturdayOrSunday() throws Exception{
        assertEquals(true, DateUtilAnswer.isSaturdayOrSunday("2023/05/14"));
        assertEquals(false, DateUtilAnswer.isSaturdayOrSunday("2023/05/15"));
        assertEquals(false, DateUtilAnswer.isSaturdayOrSunday("2023/05/16"));
        assertEquals(false, DateUtilAnswer.isSaturdayOrSunday("2023/05/17"));
        assertEquals(false, DateUtilAnswer.isSaturdayOrSunday("2023/05/18"));
        assertEquals(false, DateUtilAnswer.isSaturdayOrSunday("2023/05/19"));
        assertEquals(true, DateUtilAnswer.isSaturdayOrSunday("2023/05/20"));
        assertEquals(true, DateUtilAnswer.isSaturdayOrSunday("2023/05/21"));
        assertEquals(false, DateUtilAnswer.isSaturdayOrSunday("2023/05/22"));
        assertEquals(false, DateUtilAnswer.isSaturdayOrSunday("2023/05/23"));
        assertEquals(true, DateUtilAnswer.isSaturdayOrSunday("2023/05/27"));
        assertEquals(true, DateUtilAnswer.isSaturdayOrSunday("2023/05/28"));
    }

    @Test
    void getNationalHoliday() throws Exception {
        String[] expected = {
                "2023/01/01",
                "2023/01/02",
                "2023/01/09",
                "2023/02/11",
                "2023/02/23",
                "2023/03/21",
                "2023/04/29",
                "2023/05/03",
                "2023/05/04",
                "2023/05/05",
                "2023/07/17",
                "2023/08/11",
                "2023/09/18",
                "2023/09/23",
                "2023/10/09",
                "2023/11/03",
                "2023/11/23"
        };
        System.out.println("actual");
        String[] actual = DateUtilAnswer.getNationalHoliday(2023);
        System.out.println(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    void isNationalHoliday() throws Exception {
        assertEquals(true, DateUtilAnswer.isNationalHoliday("2023/01/01"));
        assertEquals(true, DateUtilAnswer.isNationalHoliday("2023/01/02"));
        assertEquals(false, DateUtilAnswer.isNationalHoliday("2023/01/03"));
        assertEquals(false, DateUtilAnswer.isNationalHoliday("2023/01/04"));
        assertEquals(false, DateUtilAnswer.isNationalHoliday("2023/01/05"));
        assertEquals(false, DateUtilAnswer.isNationalHoliday("2023/01/06"));
        assertEquals(false, DateUtilAnswer.isNationalHoliday("2023/01/07"));
    }

    @Test
    void isHoliday() throws Exception {
        assertEquals(true, DateUtilAnswer.isHoliday("2023/01/01"));
        assertEquals(true, DateUtilAnswer.isHoliday("2023/01/02"));
        assertEquals(false, DateUtilAnswer.isHoliday("2023/01/03"));
        assertEquals(false, DateUtilAnswer.isHoliday("2023/01/04"));
        assertEquals(false, DateUtilAnswer.isHoliday("2023/01/05"));
        assertEquals(false, DateUtilAnswer.isHoliday("2023/01/06"));
        assertEquals(true, DateUtilAnswer.isHoliday("2023/01/07"));
        assertEquals(true, DateUtilAnswer.isHoliday("2023/01/08"));
        assertEquals(true, DateUtilAnswer.isHoliday("2023/01/09"));
        assertEquals(true, DateUtilAnswer.isHoliday("2022/01/08"));
        assertEquals(true, DateUtilAnswer.isHoliday("2022/01/09"));
        assertEquals(true, DateUtilAnswer.isHoliday("2022/01/10"));
        assertEquals(false, DateUtilAnswer.isHoliday("2022/01/11"));
    }

    @Test
    void validateAndParseDate() throws ParseException {
        String inputDate = "2023-05-07";
        assertThrows(ParseException.class, () -> DateUtilAnswer.validateAndParseDate(inputDate));
        String inputDate2 = "2023/05/07";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 5 - 1, 7, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertEquals(calendar.getTime(), DateUtilAnswer.validateAndParseDate(inputDate2));
    }

}