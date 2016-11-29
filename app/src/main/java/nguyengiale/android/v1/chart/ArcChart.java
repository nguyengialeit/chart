package nguyengiale.android.v1.chart;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by giale on 11/1/16.
 */

public class ArcChart extends View {
    private final Paint mPaint;
    private RectF mRect;
    private int mWidth, mHeight;
    private int mChartWidth, mLineWidth;
    private float mPoint;
    private float mMinusRadius;
    private int mMarginLeft, mMarginTop;

    public ArcChart(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.poitChartElement);
        try {
            mPoint = Float.parseFloat(a.getString(R.styleable.poitChartElement_point));
            mPoint = (float) Math.round(mPoint * 100) / 100;
        } catch (Exception e) {
            mPoint = 0;
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mMarginLeft = 30;
        mMarginTop = 30;
        mChartWidth = converDpToPixel(context, 15);
        mLineWidth = converDpToPixel(context, 2);
        setOnMeasureCallback();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        mMinusRadius = ((mChartWidth / 2 + mLineWidth) * 180) / (float) (Math.PI * mWidth / 2);

        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(bitmap);

        mPaint.setStrokeWidth(mChartWidth / 3);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#d6d6d6"));
        tempCanvas.drawCircle(mWidth / 2 + mMarginLeft, mHeight / 2 + mMarginTop, mWidth / 2 - mChartWidth / 2 - mMarginLeft, mPaint);

        if (mPoint > 5.0) {

            Bitmap bitmapStatus1 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_3_active);
            tempCanvas.drawBitmap(bitmapStatus1, 0, mWidth / 2 + mMarginLeft, null);

            Bitmap bitmapStatus2 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_2_deactive);
            tempCanvas.drawBitmap(bitmapStatus2, mWidth - mMarginLeft - mMarginLeft, mHeight / 2 + mHeight / 2.7f, null);

            Bitmap bitmapStatus3 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_1_deactive);
            tempCanvas.drawBitmap(bitmapStatus3, mWidth / 2 + mMarginLeft + (float) (mWidth * Math.tan(Math.toRadians(180 / 10)) / 2), 0, null);

            float leftPoint = mPoint - 5;
            float leftRadius = 180 / 5 * leftPoint;
            mPaint.setStyle(Paint.Style.FILL);
            int[] colors = {Color.parseColor("#f8514f"), Color.parseColor("#f8514f")};
            float[] positions = {0, 1};
            SweepGradient gradient = new SweepGradient(mWidth / 2 + mMarginLeft, mHeight / 2 + mMarginTop, colors, positions);
            mPaint.setShader(gradient);
            mRect = new RectF(mMarginLeft + mMarginLeft, mMarginTop + mMarginTop, mWidth, mHeight);
            tempCanvas.drawArc(mRect, 90, leftRadius - mMinusRadius, true, mPaint);

            mPaint.setStyle(Paint.Style.FILL);
            int[] colors2 = {Color.parseColor("#f5d117"), Color.parseColor("#f8514f")};
            float[] positions2 = {0, 1};
            LinearGradient gradient2 = new LinearGradient(mMarginLeft, mMarginTop, mMarginLeft, mHeight + mMarginTop, colors2, positions2, Shader.TileMode.MIRROR);
            mPaint.setShader(gradient2);
            mRect = new RectF(mMarginLeft + mMarginLeft, mMarginTop + mMarginTop, mWidth, mHeight);
            tempCanvas.drawArc(mRect, 270 + mMinusRadius, 180 - mMinusRadius, true, mPaint);

            double endX = Math.cos(Math.toRadians(90 + leftRadius - mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginLeft) + mWidth / 2 + mMarginLeft;
            double endY = Math.sin(Math.toRadians(90 + leftRadius - mMinusRadius)) * (mHeight / 2 - mChartWidth / 2 - mMarginTop) + mHeight / 2 + mMarginTop;
            mPaint.setShader(gradient);
            tempCanvas.drawCircle((float) endX, (float) endY, mChartWidth / 2, mPaint);

            double startX = Math.cos(Math.toRadians(270 + mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginLeft) + mWidth / 2 + mMarginLeft;
            double startY = Math.sin(Math.toRadians(270 + mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginTop) + mHeight / 2 + mMarginTop;
            mPaint.setShader(gradient2);
            tempCanvas.drawCircle((float) startX, (float) startY, mChartWidth / 2, mPaint);
        } else if (mPoint == 5.0) {
            Bitmap bitmapStatus1 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_3_active);
            tempCanvas.drawBitmap(bitmapStatus1, 0, mWidth / 2 + mMarginLeft, null);

            Bitmap bitmapStatus2 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_2_deactive);
            tempCanvas.drawBitmap(bitmapStatus2, mWidth - mMarginLeft - mMarginLeft, mHeight / 2 + mHeight / 2.7f, null);

            Bitmap bitmapStatus3 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_1_deactive);
            tempCanvas.drawBitmap(bitmapStatus3, mWidth / 2 + mMarginLeft + (float) (mWidth * Math.tan(Math.toRadians(180 / 10)) / 2), 0, null);

            float rightPoint = mPoint;
            float rightRadius = 180 / 5 * rightPoint;
            mPaint.setStyle(Paint.Style.FILL);
            int[] colors2 = {Color.parseColor("#f5d117"), Color.parseColor("#f8514f")};
            float[] positions2 = {0, 1};
            LinearGradient gradient2 = new LinearGradient(0, 0, 0, mHeight, colors2, positions2, Shader.TileMode.MIRROR);
            mPaint.setShader(gradient2);
            mRect = new RectF(mMarginLeft + mMarginLeft, mMarginTop + mMarginTop, mWidth, mHeight);
            tempCanvas.drawArc(mRect, 270 + mMinusRadius, rightRadius - mMinusRadius * 2, true, mPaint);

            double endX = Math.cos(Math.toRadians(-90 + rightRadius - mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginLeft) + mWidth / 2 + mMarginLeft;
            double endY = Math.sin(Math.toRadians(-90 + rightRadius - mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginTop) + mHeight / 2 + mMarginTop;
            mPaint.setShader(gradient2);
            tempCanvas.drawCircle((float) endX, (float) endY, mChartWidth / 2, mPaint);

            double startX = Math.cos(Math.toRadians(270 + mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginLeft) + mWidth / 2 + mMarginLeft;
            double startY = Math.sin(Math.toRadians(270 + mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginTop) + mHeight / 2 + mMarginTop;
            mPaint.setShader(gradient2);
            tempCanvas.drawCircle((float) startX, (float) startY, mChartWidth / 2, mPaint);
        } else if (mPoint > 1.0) {
            Bitmap bitmapStatus1 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_3_deactive);
            tempCanvas.drawBitmap(bitmapStatus1, 0, mWidth / 2 + mMarginLeft, null);

            Bitmap bitmapStatus2 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_2_active);
            tempCanvas.drawBitmap(bitmapStatus2, mWidth - mMarginLeft - mMarginLeft, mHeight / 2 + mHeight / 2.7f, null);

            Bitmap bitmapStatus3 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_1_deactive);
            tempCanvas.drawBitmap(bitmapStatus3, mWidth / 2 + mMarginLeft + (float) (mWidth * Math.tan(Math.toRadians(180 / 10)) / 2), 0, null);

            float rightPoint = mPoint;
            float rightRadius = 180 / 5 * rightPoint;

            mPaint.setStyle(Paint.Style.FILL);
            int[] colors2 = {Color.parseColor("#b1d614"), Color.parseColor("#f5d117")};
            float[] positions2 = {0, 1};
            LinearGradient gradient2 = new LinearGradient(0, 0, 0, mHeight, colors2, positions2, Shader.TileMode.MIRROR);
            mPaint.setShader(gradient2);
            mRect = new RectF(mMarginLeft + mMarginLeft, mMarginTop + mMarginTop, mWidth, mHeight);
            tempCanvas.drawArc(mRect, 270 + mMinusRadius, rightRadius - mMinusRadius * 2, true, mPaint);

            double endX = Math.cos(Math.toRadians(-90 + rightRadius - mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginLeft) + mWidth / 2 + mMarginLeft;
            double endY = Math.sin(Math.toRadians(-90 + rightRadius - mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginTop) + mHeight / 2 + mMarginTop;
            mPaint.setShader(gradient2);
            tempCanvas.drawCircle((float) endX, (float) endY, mChartWidth / 2, mPaint);


            double startX = Math.cos(Math.toRadians(270 + mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginLeft) + mWidth / 2 + mMarginLeft;
            double startY = Math.sin(Math.toRadians(270 + mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginTop) + mHeight / 2 + mMarginTop;
            mPaint.setShader(gradient2);
            tempCanvas.drawCircle((float) startX, (float) startY, mChartWidth / 2, mPaint);
        } else if (mPoint == 1.0) {
            Bitmap bitmapStatus1 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_3_deactive);
            tempCanvas.drawBitmap(bitmapStatus1, 0, mWidth / 2 + mMarginLeft, null);

            Bitmap bitmapStatus2 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_2_active);
            tempCanvas.drawBitmap(bitmapStatus2, mWidth - mMarginLeft - mMarginLeft, mHeight / 2 + mHeight / 2.7f, null);

            Bitmap bitmapStatus3 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_1_deactive);
            tempCanvas.drawBitmap(bitmapStatus3, mWidth / 2 + mMarginLeft + (float) (mWidth * Math.tan(Math.toRadians(180 / 10)) / 2), 0, null);

            float rightPoint = mPoint;
            float rightRadius = 180 / 5 * rightPoint;

            mPaint.setStyle(Paint.Style.FILL);
            int[] colors2 = {Color.parseColor("#b1d614"), Color.parseColor("#f5d117")};
            float[] positions2 = {0, 1};
            LinearGradient gradient2 = new LinearGradient(0, 0, 0, mHeight, colors2, positions2, Shader.TileMode.MIRROR);
            mPaint.setShader(gradient2);
            mRect = new RectF(mMarginLeft + mMarginLeft, mMarginTop + mMarginTop, mWidth, mHeight);
            tempCanvas.drawArc(mRect, 270 + mMinusRadius, rightRadius - mMinusRadius * 2, true, mPaint);

            double endX = Math.cos(Math.toRadians(-90 + rightRadius - mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginLeft) + mWidth / 2 + mMarginLeft;
            double endY = Math.sin(Math.toRadians(-90 + rightRadius - mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginLeft) + mHeight / 2 + mMarginTop;
            mPaint.setShader(gradient2);
            tempCanvas.drawCircle((float) endX, (float) endY, mChartWidth / 2, mPaint);


            double startX = Math.cos(Math.toRadians(270 + mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginLeft) + mWidth / 2 + mMarginLeft;
            double startY = Math.sin(Math.toRadians(270 + mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginTop) + mHeight / 2 + mMarginTop;
            mPaint.setShader(gradient2);
            tempCanvas.drawCircle((float) startX, (float) startY, mChartWidth / 2, mPaint);
        } else if (mPoint > 0) {
            Bitmap bitmapStatus1 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_3_deactive);
            tempCanvas.drawBitmap(bitmapStatus1, 0, mWidth / 2 + mMarginLeft, null);

            Bitmap bitmapStatus2 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_2_deactive);
            tempCanvas.drawBitmap(bitmapStatus2, mWidth - mMarginLeft - mMarginLeft, mHeight / 2 + mHeight / 2.7f, null);

            Bitmap bitmapStatus3 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_1_active);
            tempCanvas.drawBitmap(bitmapStatus3, mWidth / 2 + mMarginLeft + (float) (mWidth * Math.tan(Math.toRadians(180 / 10)) / 2), 0, null);

            float rightPoint = mPoint;
            float rightRadius = 180 / 5 * rightPoint;

            mPaint.setStyle(Paint.Style.FILL);
            int[] colors2 = {Color.parseColor("#16c1ed"), Color.parseColor("#b1d614")};
            float[] positions2 = {0, 1};
            LinearGradient gradient2 = new LinearGradient(0, 0, 0, mHeight, colors2, positions2, Shader.TileMode.MIRROR);
            mPaint.setShader(gradient2);
            mRect = new RectF(mMarginLeft + mMarginLeft, mMarginTop + mMarginTop, mWidth, mHeight);
            tempCanvas.drawArc(mRect, 270 + mMinusRadius, rightRadius - mMinusRadius * 2, true, mPaint);

            double endX = Math.cos(Math.toRadians(-90 + rightRadius - mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginLeft) + mWidth / 2 + mMarginLeft;
            double endY = Math.sin(Math.toRadians(-90 + rightRadius - mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginTop) + mHeight / 2 + mMarginTop;
            mPaint.setShader(gradient2);
            tempCanvas.drawCircle((float) endX, (float) endY, mChartWidth / 2, mPaint);


            double startX = Math.cos(Math.toRadians(270 + mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginLeft) + mWidth / 2 + mMarginLeft;
            double startY = Math.sin(Math.toRadians(270 + mMinusRadius)) * (mWidth / 2 - mChartWidth / 2 - mMarginTop) + mHeight / 2 + mMarginTop;
            mPaint.setShader(gradient2);
            tempCanvas.drawCircle((float) startX, (float) startY, mChartWidth / 2, mPaint);
        }

        mPaint.setShader(null);
        mPaint.setColor(getResources().getColor(android.R.color.white));
        tempCanvas.drawCircle(mWidth / 2 + mMarginLeft, mHeight / 2 + mMarginTop, mWidth / 2 - mChartWidth - mMarginLeft, mPaint);

        mPaint.setStrokeWidth(mLineWidth);
        tempCanvas.drawLine(mWidth / 2 + mMarginLeft, mHeight / 2 + mMarginTop, mWidth / 2 + mMarginLeft, 0, mPaint);

        mPaint.setStrokeWidth(mLineWidth);
        tempCanvas.drawLine(mWidth / 2 + mMarginLeft, mHeight / 2 + mMarginTop, mWidth / 2 + mMarginLeft, mHeight, mPaint);

        mPaint.setStrokeWidth(mLineWidth);
        tempCanvas.drawLine(mWidth / 2 + mMarginLeft, mHeight / 2 + mMarginTop, mWidth / 2 + mMarginLeft + mMarginLeft + (float) (mWidth * Math.tan(Math.toRadians(180 / 5)) / 2), 0, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(mChartWidth * 2.5f);
        int xPos = (mWidth / 2 + mMarginLeft - (int) mPaint.measureText(String.valueOf(mPoint)) / 2);
        int yPos = (int) (mHeight / 2 + mMarginTop - ((mPaint.descent() + mPaint.ascent()) / 2));
        tempCanvas.drawText(String.valueOf(mPoint), xPos, yPos, mPaint);

        mPaint.setTextSize(mChartWidth / 1.5f);
        xPos = (mWidth / 2 + mMarginLeft - (int) mPaint.measureText(getResources().getString(R.string.point_average_label)) / 2);
        tempCanvas.drawText(getResources().getString(R.string.point_average_label), xPos, yPos - (mPaint.descent() + mPaint.ascent()) * 2, mPaint);

        canvas.save();
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.restore();
    }

    private void setOnMeasureCallback() {
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                removeOnGlobalLayoutListener(this);
                mWidth = getMeasuredWidth();
                mHeight = getMeasuredHeight();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void removeOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < 16) {
            getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }

    public void setPoint(float pPoint) {
        this.mPoint = pPoint;
        this.invalidate();
    }

    public float getPoint() {
        return mPoint;
    }

    public static int converDpToPixel(Context pContext, int pDp) {
        Resources r = pContext.getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pDp, r.getDisplayMetrics());
        return px;
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}
