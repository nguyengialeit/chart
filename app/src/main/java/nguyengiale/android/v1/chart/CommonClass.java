package nguyengiale.android.v1.chart;


import nguyengiale.android.v1.chart.object.SplitDate;

/**
 * Created by giale on 3/15/17.
 */

public class CommonClass {
    public static SplitDate splitDate(String date) {
        String mDate = date;
        String[] items;
        int year = 0, month = 0, day = 0;

        if (date.contains("-")) {
            items = mDate.split("-");
            if (items.length == 2) {
                year = Integer.parseInt(items[0]);
                month = Integer.parseInt(items[1]);
            } else if (items.length == 3) {
                year = Integer.parseInt(items[0]);
                month = Integer.parseInt(items[1]);
                day = Integer.parseInt(items[2]);
            }
        } else if (date.contains("/")) {
            items = mDate.split("/");
            if (items.length == 2) {
                year = Integer.parseInt(items[0]);
                month = Integer.parseInt(items[1]);
            } else if (items.length == 3) {
                year = Integer.parseInt(items[0]);
                month = Integer.parseInt(items[1]);
                day = Integer.parseInt(items[2]);
            }
        }
        return new SplitDate(year, month, day);
    }
}
