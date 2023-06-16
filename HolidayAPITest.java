import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HolidayAPITest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getNationalHoliday() throws Exception {
        String[] expected = {
                "2023-01-01",
                "2023-01-02",
                "2023-01-09",
                "2023-02-11",
                "2023-02-23",
                "2023-03-21",
                "2023-04-29",
                "2023-05-03",
                "2023-05-04",
                "2023-05-05",
                "2023-07-17",
                "2023-08-11",
                "2023-09-18",
                "2023-09-23",
                "2023-10-09",
                "2023-11-03",
                "2023-11-23"
        };
        assertArrayEquals(expected, HolidayAPI.getNationalHoliday(2023));
    }
}