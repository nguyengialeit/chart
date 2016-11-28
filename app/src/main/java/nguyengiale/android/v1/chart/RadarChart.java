package nguyengiale.android.v1.chart;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;

import nguyengiale.android.v1.chart.object.PointLabel;


/**
 * Created by giale on 11/28/16.
 */

public class RadarChart extends View {
    private final Paint mPaint;
    private int mWidth, mHeight;
    private int mCenterX, mCenterY;
    private ArrayList<String> mListColor = new ArrayList<>();
    private int mRadius, mTitleHeight, mPointHeight, mPadding;
    private ArrayList<PointLabel> mListPoint = new ArrayList<>();
    private ArrayList<PointLabel> mListPointTemp = new ArrayList<>();
    private int mMaxPoint;
    private Point[] mListPolygonPoint = new Point[6];

    public RadarChart(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        setOnMeasureCallback();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mListPointTemp.size(); i++) {
            mListPoint.add(new PointLabel(mListPointTemp.get(i).getLabel(), mListPointTemp.get(i).getPoint(), mWidth / 22f, mWidth / 20f));
        }

        if (mListPoint.size() > 0) {
            mListColor.add("#f73d54");
            mListColor.add("#ff6b99");
            mListColor.add("#6d5bc7");
            mListColor.add("#2680cc");
            mListColor.add("#79c45f");
            mListColor.add("#f4b833");

            mPadding = 20;

            mMaxPoint = mListPoint.get(0).getPoint();
            for (int i = 0; i < mListPoint.size(); i++) {
                if (mListPoint.get(i).getPoint() > mMaxPoint)
                    mMaxPoint = mListPoint.get(i).getPoint();
            }

            Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            Canvas tempCanvas = new Canvas(bitmap);
            Rect bounds = new Rect();
            mPaint.setTextSize(mWidth / 22f);
            mPaint.getTextBounds(mListPoint.get(0).getLabel(), 0, mListPoint.get(0).getLabel().length(), bounds);
            mTitleHeight = bounds.height();
            mPaint.setTextSize(mWidth / 20f);
            mPaint.getTextBounds(mListPoint.get(0).getLabel(), 0, mListPoint.get(0).getLabel().length(), bounds);
            mPointHeight = bounds.height();

            mRadius = mHeight / 2 - mTitleHeight - mPointHeight - mPadding;

            mPaint.setTextSize(mWidth / 22f);

            mPaint.setColor(Color.parseColor(mListColor.get(0)));
            mPaint.getTextBounds(mListPoint.get(0).getLabel(), 0, mListPoint.get(0).getLabel().length(), bounds);
            tempCanvas.drawText(mListPoint.get(0).getLabel(), mWidth / 2 - bounds.width() / 2, mTitleHeight, mPaint);

            mPaint.setColor(Color.parseColor(mListColor.get(1)));
            mPaint.getTextBounds(mListPoint.get(1).getLabel(), 0, mListPoint.get(1).getLabel().length(), bounds);
            tempCanvas.drawText(mListPoint.get(1).getLabel(), mWidth - bounds.width() - mListPoint.get(1).getPullLabel(), mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60)), mPaint);

            mPaint.setColor(Color.parseColor(mListColor.get(2)));
            mPaint.getTextBounds(mListPoint.get(2).getLabel(), 0, mListPoint.get(2).getLabel().length(), bounds);
            tempCanvas.drawText(mListPoint.get(2).getLabel(), mWidth - bounds.width() - mListPoint.get(2).getPullLabel(), mHeight - (mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60)) + mPointHeight), mPaint);

            mPaint.setColor(Color.parseColor(mListColor.get(3)));
            mPaint.getTextBounds(mListPoint.get(3).getLabel(), 0, mListPoint.get(3).getLabel().length(), bounds);
            tempCanvas.drawText(mListPoint.get(3).getLabel(), mWidth / 2 - bounds.width() / 2, mHeight - mPointHeight - 10, mPaint);

            mPaint.setColor(Color.parseColor(mListColor.get(4)));
            mPaint.getTextBounds(mListPoint.get(4).getLabel(), 0, mListPoint.get(4).getLabel().length(), bounds);
            tempCanvas.drawText(mListPoint.get(4).getLabel(), mListPoint.get(4).getPullLabel(), mHeight - (mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60)) + mPointHeight), mPaint);

            mPaint.setColor(Color.parseColor(mListColor.get(5)));
            mPaint.getTextBounds(mListPoint.get(5).getLabel(), 0, mListPoint.get(5).getLabel().length(), bounds);
            tempCanvas.drawText(mListPoint.get(5).getLabel(), mListPoint.get(5).getPullLabel(), mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60)), mPaint);

            mPaint.setTextSize(mWidth / 20f);
            mPaint.setColor(Color.parseColor("#000000"));

            mPaint.getTextBounds(String.valueOf(mListPoint.get(0).getPoint()) + "pt", 0, String.valueOf(mListPoint.get(0).getPoint()).length() + 2, bounds);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            tempCanvas.drawText(String.valueOf(mListPoint.get(0).getPoint()), mWidth / 2 - bounds.width() / 2, mTitleHeight + mPointHeight, mPaint);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            int onlyPoint0 = bounds.width() / 2;
            mPaint.getTextBounds(String.valueOf(mListPoint.get(0).getPoint()), 0, String.valueOf(mListPoint.get(0).getPoint()).length(), bounds);
            tempCanvas.drawText("pt", mWidth / 2 - onlyPoint0 + bounds.width() + 10, mTitleHeight + mPointHeight, mPaint);

            mPaint.getTextBounds(String.valueOf(mListPoint.get(1).getPoint()) + "pt", 0, String.valueOf(mListPoint.get(1).getPoint()).length() + 2, bounds);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            tempCanvas.drawText(String.valueOf(mListPoint.get(1).getPoint()), mWidth - bounds.width() - mListPoint.get(1).getPullPoint() - 5, mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60)) + mPointHeight, mPaint);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            int onlyPoint1 = bounds.width();
            mPaint.getTextBounds(String.valueOf(mListPoint.get(1).getPoint()), 0, String.valueOf(mListPoint.get(1).getPoint()).length(), bounds);
            tempCanvas.drawText("pt", mWidth - onlyPoint1 - mListPoint.get(1).getPullPoint() + bounds.width() + 5, mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60)) + mPointHeight, mPaint);

            mPaint.getTextBounds(String.valueOf(mListPoint.get(2).getPoint()) + "pt", 0, String.valueOf(mListPoint.get(2).getPoint()).length() + 2, bounds);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            tempCanvas.drawText(String.valueOf(mListPoint.get(2).getPoint()), mWidth - bounds.width() - mListPoint.get(2).getPullPoint(), mHeight - (mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60))), mPaint);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            int onlyPoint2 = bounds.width();
            mPaint.getTextBounds(String.valueOf(mListPoint.get(2).getPoint()), 0, String.valueOf(mListPoint.get(2).getPoint()).length(), bounds);
            tempCanvas.drawText("pt", mWidth - onlyPoint2 - mListPoint.get(1).getPullPoint() + bounds.width() + 5, mHeight - (mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60))), mPaint);

            mPaint.getTextBounds(String.valueOf(mListPoint.get(3).getPoint()) + "pt", 0, String.valueOf(mListPoint.get(3).getPoint()).length() + 2, bounds);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            tempCanvas.drawText(String.valueOf(mListPoint.get(3).getPoint()), mWidth / 2 - bounds.width() / 2, mHeight - 10, mPaint);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            int onlyPoint3 = bounds.width() / 2;
            mPaint.getTextBounds(String.valueOf(mListPoint.get(3).getPoint()), 0, String.valueOf(mListPoint.get(3).getPoint()).length(), bounds);
            tempCanvas.drawText("pt", mWidth / 2 - onlyPoint3 + bounds.width() + 10, mHeight - 10, mPaint);

            mPaint.getTextBounds(String.valueOf(mListPoint.get(4).getPoint()) + "pt", 0, String.valueOf(mListPoint.get(4).getPoint()).length() + 2, bounds);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            tempCanvas.drawText(String.valueOf(mListPoint.get(4).getPoint()), mListPoint.get(4).getPullPoint(), mHeight - (mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60))), mPaint);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            mPaint.getTextBounds(String.valueOf(mListPoint.get(4).getPoint()), 0, String.valueOf(mListPoint.get(4).getPoint()).length(), bounds);
            tempCanvas.drawText("pt", mListPoint.get(4).getPullPoint() + bounds.width() + 10, mHeight - (mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60))), mPaint);

            mPaint.getTextBounds(String.valueOf(mListPoint.get(5).getPoint()) + "pt", 0, String.valueOf(mListPoint.get(5).getPoint()).length() + 2, bounds);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            tempCanvas.drawText(String.valueOf(mListPoint.get(5).getPoint()), mListPoint.get(5).getPullPoint(), mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60)) + mPointHeight, mPaint);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            mPaint.getTextBounds(String.valueOf(mListPoint.get(5).getPoint()), 0, String.valueOf(mListPoint.get(5).getPoint()).length(), bounds);
            tempCanvas.drawText("pt", mListPoint.get(5).getPullPoint() + bounds.width() + 10, mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60)) + mPointHeight, mPaint);

            mPaint.setColor(Color.parseColor("#969696"));
            mPaint.setStrokeWidth(2);
            tempCanvas.rotate(-150, mCenterX, mCenterY);
            for (int i = 0; i < 6; i++) {
                tempCanvas.rotate(60, mCenterX, mCenterY);
                tempCanvas.drawLine(mCenterX, mCenterY, mCenterX + mRadius - mPadding / 2, mCenterY, mPaint);
            }

            tempCanvas.rotate(90, mCenterX, mCenterY);
            for (int i = 0; i < 6; i++) {
                tempCanvas.rotate(60, mCenterX, mCenterY);
                for (int j = 1; j <= 10; j++) {
                    if (j % 5 == 0)
                        mPaint.setStrokeWidth(4);
                    else
                        mPaint.setStrokeWidth(2);
                    tempCanvas.drawLine(j % 5 == 0 ? mCenterX - 10 : mCenterX - 5, mCenterY - mRadius / 10 * j, j % 5 == 0 ? mCenterX + 10 : mCenterX + 5, mCenterY - mRadius / 10 * j, mPaint);
                }
            }
            mPaint.setStrokeWidth(1);

            tempCanvas.rotate(60, mCenterX, mCenterY);

            for (int i = 0; i < 6; i++) {
                mPaint.setColor(Color.parseColor(mListColor.get(i)));

                float xA = (mCenterX - mCenterX) * (float) Math.cos(Math.toRadians(i * 60)) - (mCenterY - mRadius * mListPoint.get(i).getPoint() / mMaxPoint + mPadding / 2 - mCenterY) * (float) Math.sin(Math.toRadians(i * 60)) + mCenterX;
                float yA = (mCenterX - mCenterX) * (float) Math.sin(Math.toRadians(i * 60)) + (mCenterY - mRadius * mListPoint.get(i).getPoint() / mMaxPoint + mPadding / 2 - mCenterY) * (float) Math.cos(Math.toRadians(i * 60)) + mCenterY;

                mListPolygonPoint[i] = new Point(xA, yA);
            }

            canvas.save();
            canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.restore();

//            Toast.makeText(getContext(), mListPoint.get(1).getPullPoint() + "..." + mListPoint.get(1).getPullLabel(), Toast.LENGTH_SHORT).show();

            Bitmap bitmap2 = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            Canvas tempCanvas2 = new Canvas(bitmap2);
            drawPoly(tempCanvas2, getResources().getColor(android.R.color.white), mListPolygonPoint);

            Bitmap original = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.background_radar_chart), mWidth, mHeight, false);
            Bitmap mask = bitmap2;

            //You can change original image here and draw anything you want to be masked on it.

            Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas tempCanvas3 = new Canvas(result);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            tempCanvas3.drawBitmap(original, 0, 0, null);
            tempCanvas3.drawBitmap(mask, 0, 0, paint);
            paint.setXfermode(null);

            mPaint.setColor(Color.argb(40, 0, 0, 0));
            mPaint.setStrokeWidth(8);
            for (int i = 1; i < mListPolygonPoint.length; i++) {
                tempCanvas3.drawLine(mListPolygonPoint[i - 1].x, mListPolygonPoint[i - 1].y, mListPolygonPoint[i].x, mListPolygonPoint[i].y, mPaint);
            }
            tempCanvas3.drawLine(mListPolygonPoint[0].x, mListPolygonPoint[0].y, mListPolygonPoint[5].x, mListPolygonPoint[5].y, mPaint);
            mPaint.setStrokeWidth(1);
            canvas.save();
            canvas.drawBitmap(result, 0, 0, null);
            canvas.restore();

            for (int i = 0; i < 6; i++) {
                mPaint.setColor(Color.parseColor(mListColor.get(i)));
                float xA = (mCenterX - mCenterX) * (float) Math.cos(Math.toRadians(i * 60)) - (mCenterY - mRadius * mListPoint.get(i).getPoint() / mMaxPoint + mPadding / 2 - mCenterY) * (float) Math.sin(Math.toRadians(i * 60)) + mCenterX;
                float yA = (mCenterX - mCenterX) * (float) Math.sin(Math.toRadians(i * 60)) + (mCenterY - mRadius * mListPoint.get(i).getPoint() / mMaxPoint + mPadding / 2 - mCenterY) * (float) Math.cos(Math.toRadians(i * 60)) + mCenterY;
                tempCanvas3.drawCircle(xA, yA, mWidth / 105, mPaint);
            }
        }
    }

    private void setOnMeasureCallback() {
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                removeOnGlobalLayoutListener(this);
                mWidth = getMeasuredWidth();
                mHeight = mWidth;
                mCenterX = mWidth / 2;
                mCenterY = mHeight / 2;
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

    public int converDpToPixel(int pDp) {
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pDp, r.getDisplayMetrics());
        return px;
    }

    public float convertPixelsToDp(float px) {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    class Point {

        public float x = 0;
        public float y = 0;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    private void drawPoly(Canvas canvas, int color, Point[] points) {
        // line at minimum...
        if (points.length < 2) {
            return;
        }

        // paint
        Paint polyPaint = new Paint();
        polyPaint.setAntiAlias(true);
        polyPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
        polyPaint.setColor(color);
        polyPaint.setStyle(Paint.Style.FILL);

        // path
        Path polyPath = new Path();
        polyPath.moveTo(points[0].x, points[0].y);
        int i, len;
        len = points.length;
        for (i = 0; i < len; i++) {
            polyPath.lineTo(points[i].x, points[i].y);
        }
        polyPath.lineTo(points[0].x, points[0].y);

        // draw
        canvas.drawPath(polyPath, polyPaint);
    }

    public void setListPoint(ArrayList<PointLabel> pListPointLabel){
        this.mListPointTemp = pListPointLabel;
        this.invalidate();
    }
}