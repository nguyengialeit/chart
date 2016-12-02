package nguyengiale.android.v1.chart;

import android.content.Context;
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
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by giale on 11/1/16.
 */

public class ArcChart extends View {
    private final Paint mPaint;
    private RectF mRect;
    private int mWidth, mHeight;
    private int mCenterX, mCenterY;
    private int mPadding;
    private float mPoint;
    private float mRadiusChart, mRadiusLine, mMinusRadius;
    private int mLineWidth, mChartWidth;
    private float mDrawableWidth;
    private Canvas mTempCanvas;
    private Bitmap mBitmap;

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
        mLineWidth = 15;
        mMinusRadius = 10;
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        mWidth = xNew;
        mHeight = xNew;
        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
        mPadding = mWidth / 6;
        mChartWidth = mWidth / 12;
        mRadiusChart = mCenterX - mPadding;
        mRadiusLine = mRadiusChart - mChartWidth / 2;
        mMinusRadius = ((mChartWidth / 2 + mLineWidth / 2) * 180) / (float) (Math.PI * mRadiusChart);
        mDrawableWidth = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_3_active).getWidth() * 2;
        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        mTempCanvas = new Canvas(mBitmap);

    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#d6d6d6"));
        mTempCanvas.drawCircle(mCenterX, mCenterY, mRadiusLine, mPaint);

        if (mPoint > 5.0) {
            Bitmap bitmapStatus1 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_3_active);
            bitmapStatus1 = Bitmap.createScaledBitmap(bitmapStatus1, 2 * mWidth / bitmapStatus1.getWidth(), 2 * mWidth / bitmapStatus1.getHeight(), true);
            mTempCanvas.drawBitmap(bitmapStatus1, mPadding / 2 - mDrawableWidth, mCenterY - mDrawableWidth, null);

            Bitmap bitmapStatus2 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_2_deactive);
            bitmapStatus2 = Bitmap.createScaledBitmap(bitmapStatus2, 2 * mWidth / bitmapStatus2.getWidth(), 2 * mWidth / bitmapStatus2.getHeight(), true);
            mTempCanvas.drawBitmap(bitmapStatus2, -(mPadding / 2 + mDrawableWidth - mCenterY) * (float) Math.sin(Math.toRadians(135)) + mCenterX, (mPadding / 2 + mDrawableWidth - mCenterY) * (float) Math.cos(Math.toRadians(135)) + mCenterY, null);

            Bitmap bitmapStatus3 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_1_deactive);
            bitmapStatus3 = Bitmap.createScaledBitmap(bitmapStatus3, 2 * mWidth / bitmapStatus3.getWidth(), 2 * mWidth / bitmapStatus3.getHeight(), true);
            mTempCanvas.drawBitmap(bitmapStatus3, -(mPadding / 2 - mDrawableWidth - mCenterY) * (float) Math.sin(Math.toRadians(15)) + mCenterX, (mPadding / 2 - mDrawableWidth - mCenterY) * (float) Math.cos(Math.toRadians(15)) + mCenterY, null);

            float rightPoint = mPoint - 5;
            float rightRadius = 180 / 5 * rightPoint;

            mPaint.setStyle(Paint.Style.FILL);
            int[] colors2 = {Color.parseColor("#f5d117"), Color.parseColor("#f8514f")};
            float[] positions2 = {0, 1};
            LinearGradient gradient2 = new LinearGradient(mCenterX - mRadiusChart, mCenterX - mRadiusChart, mCenterX - mRadiusChart, mCenterX + mRadiusChart, colors2, positions2, Shader.TileMode.CLAMP);
            mPaint.setShader(gradient2);
            mRect = new RectF(mCenterX - mRadiusChart, mCenterY - mRadiusChart, mCenterX + mRadiusChart, mCenterY + mRadiusChart);
            mTempCanvas.drawArc(mRect, -90 + mMinusRadius, 180 - mMinusRadius, true, mPaint);

            double startX = -(mRadiusChart - mChartWidth / 2) * (float) Math.sin(Math.toRadians(-180 + mMinusRadius)) + mCenterX;
            double startY = (mRadiusChart - mChartWidth / 2) * (float) Math.cos(Math.toRadians(-180 + mMinusRadius)) + mCenterY;
            mPaint.setShader(gradient2);
            mTempCanvas.drawCircle((float) startX, (float) startY, mChartWidth / 2, mPaint);

            mPaint.setStyle(Paint.Style.FILL);
            int[] colors = {Color.parseColor("#f8514f"), Color.parseColor("#f8514f")};
            float[] positions = {0, 1};
            SweepGradient gradient = new SweepGradient(mCenterX + mPadding, mCenterY + mPadding, colors, positions);
            mPaint.setShader(gradient);
            mRect = new RectF(mCenterX - mRadiusChart, mCenterY - mRadiusChart, mCenterX + mRadiusChart, mCenterY + mRadiusChart);
            mTempCanvas.drawArc(mRect, 90, rightRadius - mMinusRadius, true, mPaint);

            double endX = -(mRadiusChart - mChartWidth / 2) * (float) Math.sin(Math.toRadians(rightRadius - mMinusRadius)) + mCenterX;
            double endY = (mRadiusChart - mChartWidth / 2) * (float) Math.cos(Math.toRadians(rightRadius - mMinusRadius)) + mCenterY;
            mPaint.setShader(gradient);
            mTempCanvas.drawCircle((float) endX, (float) endY, mChartWidth / 2, mPaint);
        } else if (mPoint == 5.0) {

            Bitmap bitmapStatus1 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_3_active);
            bitmapStatus1 = Bitmap.createScaledBitmap(bitmapStatus1, bitmapStatus1.getWidth() * 2, bitmapStatus1.getHeight() * 2, true);
            mTempCanvas.drawBitmap(bitmapStatus1, mPadding / 2 - mDrawableWidth, mCenterY - mDrawableWidth, null);

            Bitmap bitmapStatus2 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_2_deactive);
            bitmapStatus2 = Bitmap.createScaledBitmap(bitmapStatus2, bitmapStatus2.getWidth() * 2, bitmapStatus2.getHeight() * 2, true);
            mTempCanvas.drawBitmap(bitmapStatus2, -(mPadding / 2 + mDrawableWidth - mCenterY) * (float) Math.sin(Math.toRadians(135)) + mCenterX, (mPadding / 2 + mDrawableWidth - mCenterY) * (float) Math.cos(Math.toRadians(135)) + mCenterY, null);

            Bitmap bitmapStatus3 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_1_deactive);
            bitmapStatus3 = Bitmap.createScaledBitmap(bitmapStatus3, bitmapStatus3.getWidth() * 2, bitmapStatus3.getHeight() * 2, true);
            mTempCanvas.drawBitmap(bitmapStatus3, -(mPadding / 2 - mDrawableWidth - mCenterY) * (float) Math.sin(Math.toRadians(15)) + mCenterX, (mPadding / 2 - mDrawableWidth - mCenterY) * (float) Math.cos(Math.toRadians(15)) + mCenterY, null);

            float rightPoint = mPoint;
            float rightRadius = 180 / 5 * rightPoint;

            mPaint.setStyle(Paint.Style.FILL);
            int[] colors2 = {Color.parseColor("#f5d117"), Color.parseColor("#f8514f")};
            float[] positions2 = {0, 1};
            LinearGradient gradient2 = new LinearGradient(mCenterX - mRadiusChart, mCenterX - mRadiusChart, mCenterX - mRadiusChart, mCenterX + mRadiusChart, colors2, positions2, Shader.TileMode.CLAMP);
            mPaint.setShader(gradient2);
            mRect = new RectF(mCenterX - mRadiusChart, mCenterY - mRadiusChart, mCenterX + mRadiusChart, mCenterY + mRadiusChart);
            mTempCanvas.drawArc(mRect, -90 + mMinusRadius, rightRadius - mMinusRadius * 2, true, mPaint);

            double startX = -(mRadiusChart - mChartWidth / 2) * (float) Math.sin(Math.toRadians(-180 + mMinusRadius)) + mCenterX;
            double startY = (mRadiusChart - mChartWidth / 2) * (float) Math.cos(Math.toRadians(-180 + mMinusRadius)) + mCenterY;
            mPaint.setShader(gradient2);
            mTempCanvas.drawCircle((float) startX, (float) startY, mChartWidth / 2, mPaint);

            double endX = -(mRadiusChart - mChartWidth / 2) * (float) Math.sin(Math.toRadians(-180 + rightRadius - mMinusRadius)) + mCenterX;
            double endY = (mRadiusChart - mChartWidth / 2) * (float) Math.cos(Math.toRadians(-180 + rightRadius - mMinusRadius)) + mCenterY;
            mPaint.setShader(gradient2);
            mTempCanvas.drawCircle((float) endX, (float) endY, mChartWidth / 2, mPaint);
        } else if (mPoint >= 1) {

            Bitmap bitmapStatus1 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_3_deactive);
            bitmapStatus1 = Bitmap.createScaledBitmap(bitmapStatus1, bitmapStatus1.getWidth() * 2, bitmapStatus1.getHeight() * 2, true);
            mTempCanvas.drawBitmap(bitmapStatus1, mPadding / 2 - mDrawableWidth, mCenterY - mDrawableWidth, null);

            Bitmap bitmapStatus2 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_2_active);
            bitmapStatus2 = Bitmap.createScaledBitmap(bitmapStatus2, bitmapStatus2.getWidth() * 2, bitmapStatus2.getHeight() * 2, true);
            mTempCanvas.drawBitmap(bitmapStatus2, -(mPadding / 2 + mDrawableWidth - mCenterY) * (float) Math.sin(Math.toRadians(135)) + mCenterX, (mPadding / 2 + mDrawableWidth - mCenterY) * (float) Math.cos(Math.toRadians(135)) + mCenterY, null);

            Bitmap bitmapStatus3 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_1_deactive);
            bitmapStatus3 = Bitmap.createScaledBitmap(bitmapStatus3, bitmapStatus3.getWidth() * 2, bitmapStatus3.getHeight() * 2, true);
            mTempCanvas.drawBitmap(bitmapStatus3, -(mPadding / 2 - mDrawableWidth - mCenterY) * (float) Math.sin(Math.toRadians(15)) + mCenterX, (mPadding / 2 - mDrawableWidth - mCenterY) * (float) Math.cos(Math.toRadians(15)) + mCenterY, null);

            float rightPoint = mPoint;
            float rightRadius = 180 / 5 * rightPoint;

            mPaint.setStyle(Paint.Style.FILL);
            int[] colors2 = {Color.parseColor("#b1d614"), Color.parseColor("#f5d117")};
            float[] positions2 = {0, 1};
            LinearGradient gradient2 = new LinearGradient(mCenterX - mRadiusChart, mCenterX - mRadiusChart, mCenterX - mRadiusChart, mCenterX + mRadiusChart, colors2, positions2, Shader.TileMode.CLAMP);
            mPaint.setShader(gradient2);
            mRect = new RectF(mCenterX - mRadiusChart, mCenterY - mRadiusChart, mCenterX + mRadiusChart, mCenterY + mRadiusChart);
            mTempCanvas.drawArc(mRect, -90 + mMinusRadius, rightRadius - mMinusRadius * 2, true, mPaint);

            double startX = -(mRadiusChart - mChartWidth / 2) * (float) Math.sin(Math.toRadians(-180 + mMinusRadius)) + mCenterX;
            double startY = (mRadiusChart - mChartWidth / 2) * (float) Math.cos(Math.toRadians(-180 + mMinusRadius)) + mCenterY;
            mPaint.setShader(gradient2);
            mTempCanvas.drawCircle((float) startX, (float) startY, mChartWidth / 2, mPaint);

            double endX = -(mRadiusChart - mChartWidth / 2) * (float) Math.sin(Math.toRadians(-180 + rightRadius - mMinusRadius)) + mCenterX;
            double endY = (mRadiusChart - mChartWidth / 2) * (float) Math.cos(Math.toRadians(-180 + rightRadius - mMinusRadius)) + mCenterY;
            mPaint.setShader(gradient2);
            mTempCanvas.drawCircle((float) endX, (float) endY, mChartWidth / 2, mPaint);
        } else if (mPoint > 0) {

            Bitmap bitmapStatus1 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_3_deactive);
            bitmapStatus1 = Bitmap.createScaledBitmap(bitmapStatus1, bitmapStatus1.getWidth() * 2, bitmapStatus1.getHeight() * 2, true);
            mTempCanvas.drawBitmap(bitmapStatus1, mPadding / 2 - mDrawableWidth, mCenterY - mDrawableWidth, null);

            Bitmap bitmapStatus2 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_2_deactive);
            bitmapStatus2 = Bitmap.createScaledBitmap(bitmapStatus2, bitmapStatus2.getWidth() * 2, bitmapStatus2.getHeight() * 2, true);
            mTempCanvas.drawBitmap(bitmapStatus2, -(mPadding / 2 + mDrawableWidth - mCenterY) * (float) Math.sin(Math.toRadians(135)) + mCenterX, (mPadding / 2 + mDrawableWidth - mCenterY) * (float) Math.cos(Math.toRadians(135)) + mCenterY, null);

            Bitmap bitmapStatus3 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_1_active);
            bitmapStatus3 = Bitmap.createScaledBitmap(bitmapStatus3, bitmapStatus3.getWidth() * 2, bitmapStatus3.getHeight() * 2, true);
            mTempCanvas.drawBitmap(bitmapStatus3, -(mPadding / 2 - mDrawableWidth - mCenterY) * (float) Math.sin(Math.toRadians(15)) + mCenterX, (mPadding / 2 - mDrawableWidth - mCenterY) * (float) Math.cos(Math.toRadians(15)) + mCenterY, null);

            float rightPoint = mPoint;
            float rightRadius = 180 / 5 * rightPoint;

            mPaint.setStyle(Paint.Style.FILL);
            int[] colors2 = {Color.parseColor("#16c1ed"), Color.parseColor("#b1d614")};
            float[] positions2 = {0, 1};
            LinearGradient gradient2 = new LinearGradient(mCenterX - mRadiusChart, mCenterX - mRadiusChart, mCenterX - mRadiusChart, mCenterX + mRadiusChart, colors2, positions2, Shader.TileMode.CLAMP);
            mPaint.setShader(gradient2);
            mRect = new RectF(mCenterX - mRadiusChart, mCenterY - mRadiusChart, mCenterX + mRadiusChart, mCenterY + mRadiusChart);
            mTempCanvas.drawArc(mRect, -90 + mMinusRadius, rightRadius - mMinusRadius * 2, true, mPaint);

            double startX = -(mRadiusChart - mChartWidth / 2) * (float) Math.sin(Math.toRadians(-180 + mMinusRadius)) + mCenterX;
            double startY = (mRadiusChart - mChartWidth / 2) * (float) Math.cos(Math.toRadians(-180 + mMinusRadius)) + mCenterY;
            mPaint.setShader(gradient2);
            mTempCanvas.drawCircle((float) startX, (float) startY, mChartWidth / 2, mPaint);

            double endX = -(mRadiusChart - mChartWidth / 2) * (float) Math.sin(Math.toRadians(-180 + rightRadius - mMinusRadius)) + mCenterX;
            double endY = (mRadiusChart - mChartWidth / 2) * (float) Math.cos(Math.toRadians(-180 + rightRadius - mMinusRadius)) + mCenterY;
            mPaint.setShader(gradient2);
            mTempCanvas.drawCircle((float) endX, (float) endY, mChartWidth / 2, mPaint);
        }else{
            Bitmap bitmapStatus1 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_3_deactive);
            mTempCanvas.drawBitmap(bitmapStatus1, mPadding / 2 - mDrawableWidth, mCenterY - mDrawableWidth, null);

            Bitmap bitmapStatus2 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_2_deactive);
            mTempCanvas.drawBitmap(bitmapStatus2, -(mPadding / 2 + mDrawableWidth - mCenterY) * (float) Math.sin(Math.toRadians(135)) + mCenterX, (mPadding / 2 + mDrawableWidth - mCenterY) * (float) Math.cos(Math.toRadians(135)) + mCenterY, null);

            Bitmap bitmapStatus3 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_1_active);
            mTempCanvas.drawBitmap(bitmapStatus3, -(mPadding / 2 - mDrawableWidth - mCenterY) * (float) Math.sin(Math.toRadians(15)) + mCenterX, (mPadding / 2 - mDrawableWidth - mCenterY) * (float) Math.cos(Math.toRadians(15)) + mCenterY, null);
        }
        mPaint.setShader(null);

        mPaint.setColor(Color.parseColor("#ffffff"));
        mTempCanvas.drawLine(mCenterX, mCenterY, -mRadiusChart * (float) Math.sin(Math.toRadians(180)) + mCenterX, mRadiusChart * (float) Math.cos(Math.toRadians(180)) + mCenterY, mPaint);
        mTempCanvas.drawLine(mCenterX, mCenterY, -mRadiusChart * (float) Math.sin(Math.toRadians(216)) + mCenterX, mRadiusChart * (float) Math.cos(Math.toRadians(216)) + mCenterY, mPaint);
        mTempCanvas.drawLine(mCenterX, mCenterY, -mRadiusChart * (float) Math.sin(Math.toRadians(0)) + mCenterX, mRadiusChart * (float) Math.cos(Math.toRadians(0)) + mCenterY, mPaint);

        mTempCanvas.drawCircle(mCenterX, mCenterY, mRadiusChart - mChartWidth, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        mPaint.setTextSize(mWidth / 5f);
        int xPos = (mCenterX - (int) mPaint.measureText(String.valueOf(mPoint)) / 2);
        int yPos = (int) (mCenterY - ((mPaint.descent() + mPaint.ascent()) / 2));
        mTempCanvas.drawText(String.valueOf(mPoint), xPos, yPos, mPaint);

        mPaint.setTextSize(mWidth / 15f);
        mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        xPos = (mCenterX - (int) mPaint.measureText("pt / day") / 2);
        mTempCanvas.drawText("pt / day", xPos, yPos - (mPaint.descent() + mPaint.ascent()) * 2, mPaint);

        canvas.save();
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.restore();
    }

    public void setPoint(float pPoint) {
        this.mPoint = pPoint;
        this.invalidate();
    }

    public float getPoint() {
        return mPoint;
    }
}
