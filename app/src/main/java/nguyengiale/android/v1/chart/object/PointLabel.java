package nguyengiale.android.v1.chart.object;

import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by giale on 11/28/16.
 */

public class PointLabel {
    private String label, pointLabel;
    private int point, pullLabel, pullPoint;
    private float labelTextSize, pointTextSize;
    public PointLabel(String label, int point){
        this.label = label;
        this.point = point;
    }
    public PointLabel(String label, int point, float labelTextSize, float pointTextSize){
        this.label = label;
        this.point = point;
        this.pointLabel = String.valueOf(point);
        this.labelTextSize = labelTextSize;
        this.pointTextSize = pointTextSize;
    }
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPullLabel() {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextSize(labelTextSize);
        paint.getTextBounds(this.label, 0, this.label.length(), bounds);
        int widthLabel = bounds.width();
        paint.setTextSize(pointTextSize);
        paint.getTextBounds(String.valueOf(this.point)+"pt", 0, String.valueOf(this.point).length()+2, bounds);
        int widthPoint = bounds.width();
        if(widthLabel > widthPoint){
            return 0;
        }else{
            return widthPoint/2 - widthLabel/2;
        }
    }

    public int getPullPoint() {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextSize(labelTextSize);
        paint.getTextBounds(this.label, 0, this.label.length(), bounds);
        int widthLabel = bounds.width();
        paint.setTextSize(pointTextSize);
        paint.getTextBounds(String.valueOf(this.point)+"pt", 0, String.valueOf(this.point).length()+2, bounds);
        int widthPoint = bounds.width();
        if(widthLabel > widthPoint){
            return widthLabel/2 - widthPoint/2;
        }else{
            return 0;
        }
    }

    public String getPointLabel() {
        return pointLabel;
    }

    public void setPointLabel(String pointLabel) {
        this.pointLabel = pointLabel;
    }
}
