package nguyengiale.android.v1.chart.object;

/**
 * Created by tryczson on 19/01/2017.
 */

public class SplitDate {
    private final int year;
    private final int month;
    private final int day;

    public SplitDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}