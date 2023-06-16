import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Task {
    private String name;
    private String deadLine;
    int actualTime;
    int plannedTime;
    Status status;


    /**
     * 期日が今日時点で期限切れか否かを判定する
     * @return 期限切れ:true 否:false
     * @throws ParseException
     */
    // 演習21(期日が今日時点で期限切れか否かを判定)
    // 期限切れ:true
    // 否     :false
    public boolean isExpired() throws ParseException {
        Date today = new Date();
        Date expiredDate = DateUtil.validateAndParseDate(deadLine);

        return today.after(expiredDate);
    }

    /**
     * 期日が丁度今日までか否かを判定
     * @return 今日:true 否:false
     */
    // 演習22(期日が丁度今日までか否かを判定)
    // 今日:true
    // 否　:false
    public boolean onDeadLine() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate expiredDate = LocalDate.parse(deadLine, formatter);

        return today.isEqual(expiredDate);
    }

    /**
     * 今日から期日までの残り日数を取得
     * @return 日数
     * @throws ParseException
     */
    // 演習23(今日から期日までの残り日数を取得)
    public int countRemainingDays() throws ParseException {
        Date today = new Date();
        Date expiredDate = DateUtil.validateAndParseDate(deadLine);

        // 今日の日付が期日を過ぎていたら0を返す
        if (today.after(expiredDate)) {
            return 0;
        }
        int remainingDays = DateUtil.getDaysBetweenDates(today, expiredDate);
        return remainingDays + 1;
    }

    /**
     * 今日から期日までの残り平日日数を取得
     * @return 日数
     * @throws ParseException
     */
    // 演習24(今日から期日までの残り平日日数を取得)
    public int countRemainingWeekDays() throws ParseException {
        Date today = new Date();
        Date expiredDate = DateUtil.validateAndParseDate(deadLine);

        // 今日の日付が期日を過ぎていたら0を返す
        if (today.after(expiredDate)) {
            return 0;
        }
        String f_today = DateUtil.formatDateToString(today);
        String f_expiredDate = DateUtil.formatDateToString(deadLine);

        int remainingWeekDays = DateUtil.countWorkingDays(f_today, f_expiredDate);
        return remainingWeekDays - 1; // 今日の日付が入ってしまうため、カウントを1減らす
    }

    /**
     * intの配列に対し、合計を計算
     * @param array
     * @return intの配列の合計
     */
    // 演習25(intの配列に対し、合計を計算)
    public static int sumOf(int[] array) {
        int sum = 0;
        for (int num : array) {
            sum += num;
        }
        return sum;
    }

    /**
     * Taskの配列に対する実績時間を集計
     * @param tasks
     * @return 実績時間の合計
     */
    // 演習26(タスクの配列に対する実績時間を集計)
    public static int getSumOfActualTime(Task[] tasks) {
        int sum = 0;
        for (Task task : tasks) {
            sum += task.getActualTime();
        }
        return sum;
    }

    /**
     * タスクの配列に対する予定時間を集計stream使用
     * @param tasks
     * @return 予定時間の合計
     */
    // 演習27(タスクの配列に対する予定時間を集計stream使用)
    public static int getSumOfPlannedTime(List<Task> tasks) {
        return tasks.stream()
                .mapToInt(Task::getPlannedTime)
                .sum();
    }

    // 演習28(Enumを使用してStatusを表すインスタンス変数を追加)
    public enum Status {
        UNHANDLED("未対応"),
        PROCESSING("対応中"),
        DONE("完了"),
        UNDER_VERIFICATION("検証中");

        private final String displayValue;

        Status (String displayValue) {
            this.displayValue = displayValue;
        }
    }

    public int getActualTime() {
        return actualTime;
    }

    public int getPlannedTime() {
        return plannedTime;
    }

    public Task(String name, String deadLine) {
        this.name = name;
        this.deadLine = deadLine;
        this.actualTime = 0;
        this.status = Status.UNHANDLED;
    }

    public Task(String name, String deadLine, int actualTime) {
        this.name = name;
        this.deadLine = deadLine;
        this.actualTime = actualTime;
    }

    public Task(String name, String deadLine, int actualTime, int plannedTime) {
        this.name = name;
        this.deadLine = deadLine;
        this.actualTime = actualTime;
        this.plannedTime = plannedTime;
    }
}
