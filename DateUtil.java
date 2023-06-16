import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

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


    // 演習15(与えられた日付が祝日か否か)


    // 演習16(与えられた日付が休日(土日または祝日)か否か)

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
