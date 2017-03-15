package nguyengiale.android.v1.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class PieChart extends View {
    private RectF rect = new RectF();
    private Paint paint = new Paint();
    private String mTextPieChart = "";
    private float percentage;
    private float mCurrAngle;
    private float sweepAngle = 0f;
    private double animationTime;
    private int cx, cy;
    int mWidth, mHeight;

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.pieChartElement);
        try {
            percentage = Float.parseFloat(a.getString(R.styleable.pieChartElement_percentage));
            mTextPieChart = a.getString(R.styleable.pieChartElement_text);
        } catch (Exception e) {
            percentage = 0;
        }
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
        invalidate();
    }

    public String getTextPieChart() {
        return mTextPieChart;
    }

    public void setTextPieChart(String mTextPieChart) {
        this.mTextPieChart = mTextPieChart;
        invalidate();
    }

    public float getPercentage() {
        return percentage;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        cx = w / 2;
        cy = h / 2;
        if (w > h) {
            rect.set(w / 2 - h / 2, 0, w / 2 + h / 2, h);
        } else {
            rect.set(0, h / 2 - w / 2, w, h / 2 + w / 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (percentage != 0) {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setStyle(Paint.Style.FILL);
            mCurrAngle = 360 * percentage / 100;
            canvas.drawColor(Color.WHITE);
            paint.setColor(Color.parseColor("#1d81cc"));
            canvas.drawArc(rect, -90, 360 * percentage / 100, true, paint);
            paint.setColor(Color.parseColor("#f0f0f0"));
            canvas.drawArc(rect, 360 * percentage / 100 - 90, 360 - 360 * percentage / 100, true, paint);
            //  animateArc(-90, 360 * percentage / 100,1000);

            paint.setColor(Color.WHITE);
            paint.setTextSize(32f);
            canvas.drawText(mTextPieChart, cx + 30, cy, paint);
        } else {
            Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            Canvas tempCanvas = new Canvas(bitmap);

            paint.setTextSize(mWidth / 15f);
            float xPos = (cx - (int) paint.measureText(String.valueOf("No data")) / 2);
            float yPos = (int) (cy - ((paint.descent() + paint.ascent()) / 2));
            tempCanvas.drawText(String.valueOf("No data"), xPos, yPos, paint);

            canvas.save();
            canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.restore();
        }

    }
}
