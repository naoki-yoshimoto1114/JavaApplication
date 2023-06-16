import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetDateTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getLastMonthDates() throws ParseException {
        List<String> actual1 = GetDate.getLastMonthDates("2024/03/25");
        List<String> expected1 = new ArrayList<>(Arrays.asList(
                "2024/03/25",
                "2024/03/24",
                "2024/03/23",
                "2024/03/22",
                "2024/03/21",
                "2024/03/20",
                "2024/03/19",
                "2024/03/18",
                "2024/03/17",
                "2024/03/16",
                "2024/03/15",
                "2024/03/14",
                "2024/03/13",
                "2024/03/12",
                "2024/03/11",
                "2024/03/10",
                "2024/03/09",
                "2024/03/08",
                "2024/03/07",
                "2024/03/06",
                "2024/03/05",
                "2024/03/04",
                "2024/03/03",
                "2024/03/02",
                "2024/03/01",
                "2024/02/29",
                "2024/02/28",
                "2024/02/27",
                "2024/02/26",
                "2024/02/25"
        ));
        assertEquals(expected1, actual1);

        List<String> actual2 = GetDate.getLastMonthDates("2023/03/25");
        List<String> expected2 = new ArrayList<>(Arrays.asList(
                "2023/03/25",
                "2023/03/24",
                "2023/03/23",
                "2023/03/22",
                "2023/03/21",
                "2023/03/20",
                "2023/03/19",
                "2023/03/18",
                "2023/03/17",
                "2023/03/16",
                "2023/03/15",
                "2023/03/14",
                "2023/03/13",
                "2023/03/12",
                "2023/03/11",
                "2023/03/10",
                "2023/03/09",
                "2023/03/08",
                "2023/03/07",
                "2023/03/06",
                "2023/03/05",
                "2023/03/04",
                "2023/03/03",
                "2023/03/02",
                "2023/03/01",
                "2023/02/28",
                "2023/02/27",
                "2023/02/26",
                "2023/02/25"
                ));
        assertEquals(expected2, actual2);

        List<String> actual3 = GetDate.getLastMonthDates("2023/01/25");
        List<String> expected3 = new ArrayList<>(Arrays.asList(
                "2023/01/25",
                "2023/01/24",
                "2023/01/23",
                "2023/01/22",
                "2023/01/21",
                "2023/01/20",
                "2023/01/19",
                "2023/01/18",
                "2023/01/17",
                "2023/01/16",
                "2023/01/15",
                "2023/01/14",
                "2023/01/13",
                "2023/01/12",
                "2023/01/11",
                "2023/01/10",
                "2023/01/09",
                "2023/01/08",
                "2023/01/07",
                "2023/01/06",
                "2023/01/05",
                "2023/01/04",
                "2023/01/03",
                "2023/01/02",
                "2023/01/01",
                "2022/12/31",
                "2022/12/30",
                "2022/12/29",
                "2022/12/28",
                "2022/12/27",
                "2022/12/26",
                "2022/12/25"
        ));
        assertEquals(expected3, actual3);


        List<String> actual4 = GetDate.getLastMonthDates("2023/12/25");
        List<String> expected4 = new ArrayList<>(Arrays.asList(
                "2023/12/25",
                "2023/12/24",
                "2023/12/23",
                "2023/12/22",
                "2023/12/21",
                "2023/12/20",
                "2023/12/19",
                "2023/12/18",
                "2023/12/17",
                "2023/12/16",
                "2023/12/15",
                "2023/12/14",
                "2023/12/13",
                "2023/12/12",
                "2023/12/11",
                "2023/12/10",
                "2023/12/09",
                "2023/12/08",
                "2023/12/07",
                "2023/12/06",
                "2023/12/05",
                "2023/12/04",
                "2023/12/03",
                "2023/12/02",
                "2023/12/01",
                "2023/11/30",
                "2023/11/29",
                "2023/11/28",
                "2023/11/27",
                "2023/11/26",
                "2023/11/25"
        ));
        assertEquals(expected4, actual4);
    }

    @Test
    void getDaysBetweenDates() throws ParseException {
        Date date1_1 = DateUtilAnswer.validateAndParseDate("2023/03/04");
        Date date1_2 = DateUtilAnswer.validateAndParseDate("2023/03/06");
        assertEquals(2, GetDate.getDaysBetweenDates(date1_1, date1_2));
        Date date2_1 = DateUtilAnswer.validateAndParseDate("2023/03/30");
        Date date2_2 = DateUtilAnswer.validateAndParseDate("2023/04/01");
        assertEquals(2, GetDate.getDaysBetweenDates(date2_1, date2_2));
        Date date3_1 = DateUtilAnswer.validateAndParseDate("2023/03/30");
        Date date3_2 = DateUtilAnswer.validateAndParseDate("2023/04/04");
        assertEquals(5, GetDate.getDaysBetweenDates(date3_1, date3_2));
        Date date4_1 = DateUtilAnswer.validateAndParseDate("2023/03/30");
        Date date4_2 = DateUtilAnswer.validateAndParseDate("2023/04/30");
        assertEquals(31, GetDate.getDaysBetweenDates(date4_1, date4_2));
    }

}