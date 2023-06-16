import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class MyDate {

    public static void main(String[] args) {

        //コマンドライン引数なら
        Scanner scanner = new Scanner(System.in);

        // 日付加算
        System.out.print("日付を入力してください (yyyy/MM/dd): ");
        String inputDate = scanner.next(); // コマンドラインから日付を読み取ります。
        System.out.print("加算する日数を整数で入力してください: ");
        int daysToAdd = Integer.parseInt(scanner.next()); // コマンドラインから日付を読み取ります。

        try {
            Date date = validateAndParseDate(inputDate);
            Date newDate = addDaysToDate(date, daysToAdd);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            System.out.println("新しい日付: " + sdf.format(newDate));
        } catch (ParseException e) {
            System.err.println("エラー: 日付の形式が正しくありません。");
        }

        // 2つの日付の間の日数
        System.out.print("2つの日付の間の日数を返します。1つ目(yyyy/MM/dd):");
        String date1 = scanner.next();
        System.out.print("2つ目(yyyy/MM/dd):");
        String date2 = scanner.next();

        try {
            Date fromDate = validateAndParseDate(date1);
            Date toDate = validateAndParseDate(date2);
            int betweenDates = getDaysBetweenDates(fromDate, toDate);
            System.out.println("2つの日付の間の日数:" + betweenDates);
        } catch (ParseException e) {
            System.out.println("エラー: 日付の形式が正しくありません。");
        }

        // 指定された日付が平日か否か
        System.out.println("指定された日付が平日(月火水木金)か否かを判定します。");
        System.out.print("日付を入力してください。(yyyy/MM/dd): ");
        String inputWeekDate1 = scanner.next();

        boolean isWeekday = isWeekday(inputWeekDate1);
        if (isWeekday) {
            System.out.println(inputWeekDate1 + "は平日です。");
        } else {
            System.out.println(inputWeekDate1 + "は休日です。");
        }

        // 指定された日付が土日か否か
        System.out.println("指定された日付が土日か否かを判定します。");
        System.out.print("日付を入力してください。(yyyy/MM/dd): ");
        String inputWeekDate2 = scanner.next();

        boolean isSaturdayOrSunday = isSaturdayOrSunday(inputWeekDate2);
        if (isSaturdayOrSunday) {
            System.out.println(inputWeekDate2 + "は土日です。");
        } else {
            System.out.println(inputWeekDate2 + "は平日です。");
        }

        scanner.close();
    }

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

    public static Date addDaysToDate(Date date, int days) {
        // 現在の日付と時間を表すcalenderオブジェクトを取得
        Calendar calendar = getCalendarFor(date);
        // 指定された日数を加算
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    // 2日の間の日数を返すメソッド(演習10)
    public static int getDaysBetweenDates(Date date1, Date date2) {
        Calendar calendar1 = getCalendarFor(date1);
        Calendar calendar2 = getCalendarFor(date2);

        // カレンダーオブジェクトを日数に変換するため、ミリ秒の差を計算する
        long millisDiff = Math.abs(calendar2.getTimeInMillis() - calendar1.getTimeInMillis());

        // ミリ秒を日数に変換
        int daysDiff = (int) Math.round((millisDiff / (1000 * 60 * 60 * 24)));
        return daysDiff;
    }

    // 与えられた日付が平日か否かを判定するメソッド(演習12)
    // 平日:true
    // 休日:false
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

    public static boolean isWeekday(Date date) {
        Calendar calendar = getCalendarFor(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY);
    }

    // 与えられた日付が土日か否かを判定するメソッド(演習13)
    // 土日:true
    // 平日:false
    public static boolean isSaturdayOrSunday(String yyyymmdd) {
        return !isWeekday(yyyymmdd);
    }


    // ある期間内の平日の数をカウントするメソッド(演習17)
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
        int howManyDays = MyDate.countDaysBetween(fromC.getTime(), toC.getTime()) + 1;
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

    // 指定された日付の次の平日を計算するメソッド(演習18)
    public static String getNextWorkingDayOf(String date) throws ParseException {
        Date d_date = validateAndParseDate(date);
        Date next = addDaysToDate(d_date,1);
        while(!isWeekday(next)){
            next = addDaysToDate(next,1);
        }
        String s_next = new SimpleDateFormat("yyyy/MM/dd").format(next);
        return s_next;
    }

    private static Calendar getCalendarFor(String yyyymmdd) throws ParseException {
        final Date date = MyDate.validateAndParseDate(yyyymmdd);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }


    // Calendarオブジェクトを取得
    public static Calendar getCalendarFor(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}