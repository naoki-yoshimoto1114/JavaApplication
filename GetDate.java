import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;


public class GetDate {
    public static List<String> getLastMonthDates(String inputDate) throws ParseException {
        ArrayList<String> dates = new ArrayList<>();
        dates.add(inputDate);
        String[] dateParts = inputDate.split("/");
        int year = Integer.parseInt(dateParts[0]);
        //注意：例えば"2023/03/04"がinputDateであれば、2023/02/05までの日付のリストを返したいから、何日まであるのかを見る月は3-1=2で2月にする必要がある。
        int month = Integer.parseInt(dateParts[1]) - 1;
        int howManyDays = GetDate.getHowManyDaysOf(year, month);
        for(int i = -1; i > -1 * howManyDays - 1; i --) {
            Date date = DateUtilAnswer.addDaysToDate(DateUtilAnswer.validateAndParseDate(inputDate),i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String formattedDate = sdf.format(date);
            dates.add(formattedDate);
        }
        return dates;
    }


    private static boolean isLeapYear(int year) {
        if(year % 4 == 0) return true;
        else return false;
    }

    private static int getHowManyDaysOf(int year, int month) {
        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12 || month == 0) {
            return 31;
        } else if(month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else {
            if(isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
    }

    public static int getDaysBetweenDates(Date date1, Date date2) {
        long difference = date2.getTime() - date1.getTime();
        return (int) ( difference / (24 * 60 * 60 * 1000));
    }
}