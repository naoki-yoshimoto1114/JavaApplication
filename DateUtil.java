import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
    /**
     * バリデーションとパースを行う
     * @param inputDate
     * @return inputDateをバリデーションとパースした結果
     * @throws ParseException
     */
    public static Date validateAndParseDate(String inputDate) throws ParseException {
        // 「0000/00/00」の形式に合致するか
        if (!inputDate.matches("^\\d{4}/\\d{2}/\\d{2}$")) {
            throw new ParseException("Invalid date format", 0);
        }
        // 指定された形式で日付を解析orフォーマットする
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setLenient(false);

        return sdf.parse(inputDate);
    }

    /**
     * Date型の引数をString型にする
     * @param date
     * @return String型に変換した結果
     */
    public static String formatDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(date);
    }

    /**
     * String型の文字列をフォーマットする
     * @param inputDate
     * @return フォーマットされた結果
     * @throws ParseException
     */
    public static String formatDateToString(String inputDate) throws ParseException {
        Date date = validateAndParseDate(inputDate);
        return formatDateToString(date);
    }

    /**
     * 日付を加算する
     * @param date
     * @param days
     * @return 現在の日付から指定された日数分加算する
     */
    // 演習4(日付演算)
    public static Date addDaysToDate(Date date, int days) {
        // 現在の日付と時間を表すcalenderオブジェクトを取得
        Calendar calendar = getCalendarFor(date);
        // 指定された日数を加算
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    // 演習8(リストから過去一か月の日付リストを取得)
    public static List<String> getLastMonthDates(String inputDate) throws ParseException {
        ArrayList<String> dates = new ArrayList<>();
        dates.add(inputDate);
        String[] dateParts = inputDate.split("/");
        int year = Integer.parseInt(dateParts[0]);
        //注意：例えば"2023/03/04"がinputDateであれば、2023/02/05までの日付のリストを返したいから、何日まであるのかを見る月は3-1=2で2月にする必要がある。
        int month = Integer.parseInt(dateParts[1]) - 1;
        int howManyDays = DateUtil.getHowManyDaysOf(year, month);
        for(int i = -1; i > -1 * howManyDays - 1; i --) {
            Date date = DateUtilAnswer.addDaysToDate(DateUtilAnswer.validateAndParseDate(inputDate),i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String formattedDate = sdf.format(date);
            dates.add(formattedDate);
        }
        return dates;
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

    private static boolean isLeapYear(int year) {
        if(year % 4 == 0) return true;
        else return false;
    }

    /**
     * 2日の間の日数を返す
     * @param date1
     * @param date2
     * @return 2日の間の日数
     */
    // 演習10(2日の間の日数を返す)
    public static int getDaysBetweenDates(Date date1, Date date2) {
        Calendar calendar1 = getCalendarFor(date1);
        Calendar calendar2 = getCalendarFor(date2);

        // カレンダーオブジェクトを日数に変換するため、ミリ秒の差を計算する
        long millisDiff = Math.abs(calendar2.getTimeInMillis() - calendar1.getTimeInMillis());

        // ミリ秒を日数に変換
        int daysDiff = (int) (millisDiff / (1000 * 60 * 60 * 24));
        return daysDiff;
    }

    /**
     * 与えられた日付が平日か否かを判定する
     * @param yyyymmdd
     * @return 平日:true 休日:false
     */
    // 演習12(与えられた日付が平日か否か)
    // 引数 String型
    public static boolean isWeekday(String yyyymmdd) {
        try {
            Date date = validateAndParseDate(yyyymmdd);
            Calendar calendar = getCalendarFor(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            return (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY);
        } catch (ParseException e) {
            throw new IllegalArgumentException("エラー：日付の形式が正しくありません。");
        }
    }

    /**
     * 与えられた日付が平日か否かを判定する(引数Date型)
     * @param date
     * @return 平日:true 休日:false
     */
    // 引数 Date型
    public static boolean isWeekday(Date date) {
        Calendar calendar = getCalendarFor(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY);
    }

    /**
     * 与えられた日付が土日か否かを判定する
     * @param yyyymmdd
     * @return 土日:true それ以外:false
     */
    // 演習13(与えられた日付が土日か否か)
    public static boolean isSaturdayOrSunday(String yyyymmdd) {
        return !isWeekday(yyyymmdd);
    }

    // 演習14(祝日の配列を取得)
    public static String[] getNationalHoliday(int yyyy) throws Exception {
        final String responseBody = getResponseBodyTo("https://holidays-jp.github.io/api/v1/" + yyyy + "/date.json");
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode jsonNode = objectMapper.readTree(responseBody);
        return getKeyStringArr(jsonNode);
    }

    public static String getResponseBodyTo(String url) throws Exception {
        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .build();
        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private static String[] getKeyStringArr(JsonNode jsonNode) {
        final int size = jsonNode.size();
        final String[] arr = new String[size];
        final Iterator<String> iterator = jsonNode.fieldNames();
        for (int i = 0; iterator.hasNext(); i++) {
            arr[i] = iterator.next().replace("-", "/");
        }
        return arr;
    }

    // 演習15(与えられた日付が祝日か否か)
    public static boolean isNationalHoliday(String yyyymmdd) throws Exception{
        final int year = getYearOf(yyyymmdd);
        final String[] nationalHolidays = getNationalHoliday(year);
        final ArrayList<String> arrDates = new ArrayList<>(Arrays.asList(nationalHolidays));
        return arrDates.contains(yyyymmdd);
    }

    private static int getYearOf(String yyyymmdd) {
        return Integer.parseInt(yyyymmdd.substring(0, 4));
    }

    // 演習16(与えられた日付が休日(土日または祝日)か否か)
    public static boolean isHoliday(String yyyymmdd) throws Exception {
        return isNationalHoliday(yyyymmdd) || isSaturdayOrSunday(yyyymmdd);
    }

    /**
     * ある期間内の平日の数をカウントする
     * @param from
     * @param to
     * @return 指定された期間の間の平日の数
     * @throws ParseException
     */
    // 演習17(ある期間内の平日の数をカウント)
    public static int countWorkingDays(String from, String to) throws ParseException {
        String[] daysStrings = getDaysStrBetween(from, to);
        int count = 0;
        for (int i = 0; i < daysStrings.length; i++) {
            if(!isSaturdayOrSunday(daysStrings[i]))
                count++;
        }
        return count;
    }

    public static String[] getDaysStrBetween(String from, String to) throws ParseException{
        Calendar fromC = getCalendarFor(from);
        Calendar toC = getCalendarFor(to);
        int howManyDays = DateUtil.countDaysBetween(fromC.getTime(), toC.getTime()) + 1;
        String[] strings = new String[howManyDays];
        int n = 0;
        while (n != howManyDays) {
            Calendar tmp = (Calendar) fromC.clone();
            tmp.add(Calendar.DAY_OF_MONTH, n);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String formattedDate = sdf.format(tmp.getTime());
            strings[n] = formattedDate;
            n++;
        }
        return strings;
    }

    public static int countDaysBetween(Date date1, Date date2) {
        long difference = date2.getTime() - date1.getTime();
        return (int) ( difference / (24 * 60 * 60 * 1000));
    }

    /**
     * 指定された日付の次の平日を取得する
     * @param date
     * @return String型のフォーマットされた日付
     * @throws ParseException
     */
    // 演習18(指定された日付の次の平日を取得)
    public static String getNextWorkingDayOf(String date) throws ParseException {
        Date d_date = validateAndParseDate(date);
        Date next = addDaysToDate(d_date,1);
        while(!isWeekday(next)){
            next = addDaysToDate(next,1);
        }
        String s_next = new SimpleDateFormat("yyyy/MM/dd").format(next);
        return s_next;
    }

    // Calendarオブジェクトを取得
    // 引数 Date型
    public static Calendar getCalendarFor(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    // 引数 String型
    private static Calendar getCalendarFor(String yyyymmdd) throws ParseException {
        final Date date = MyDate.validateAndParseDate(yyyymmdd);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
