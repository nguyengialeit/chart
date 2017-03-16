package nguyengiale.android.v1.chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import nguyengiale.android.v1.chart.object.PointLabel;


/**
 * Created by giale on 11/28/16.
 */

public class RadarChart extends View {
    private final Paint mPaint;
    private int mWidth, mHeight;
    private float mCenterX, mCenterY;
    private ArrayList<String> mListColor = new ArrayList<>();
    private float mRadius, mTitleHeight, mPointHeight, mPadding;
    private ArrayList<Float> mListOldPoint = new ArrayList<>();
    private ArrayList<PointLabel> mListPoint = new ArrayList<>();
    private ArrayList<PointLabel> mListPointTemp = new ArrayList<>();
    private float mMaxPoint;
    private Point[] mListPolygonPoint = new Point[6];
    private Point[] mListPolygonOldPoint = new Point[6];
    private List<String> mListLabel = new ArrayList<>();
    private int mPaddingLabel = 20;

    public RadarChart(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = mWidth;
        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
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
            if (mMaxPoint == 0)
                mMaxPoint = 1;

            for (int i = 0; i < mListPoint.size(); i++) {
                if (mListPoint.get(i).getPoint() == 0)
                    mListPoint.get(i).setPoint((int) (mMaxPoint * 0.1));
            }
            if (!mListOldPoint.isEmpty())
                for (int i = 0; i < mListOldPoint.size(); i++) {
                    if (mListOldPoint.get(i) == 0)
                        mListOldPoint.set(i, (mMaxPoint * 0.1f));
                    else
                        mListOldPoint.set(i, mListOldPoint.get(i) / 30 * mMaxPoint);
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
            tempCanvas.drawText(mListPoint.get(1).getLabel(), mWidth - bounds.width() - mListPoint.get(1).getPullLabel() - 5, mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60)), mPaint);

            mPaint.setColor(Color.parseColor(mListColor.get(2)));
            mPaint.getTextBounds(mListPoint.get(2).getLabel(), 0, mListPoint.get(2).getLabel().length(), bounds);
            tempCanvas.drawText(mListPoint.get(2).getLabel(), mWidth - bounds.width() - mListPoint.get(2).getPullLabel() - 5, mHeight - (mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60))), mPaint);

            mPaint.setColor(Color.parseColor(mListColor.get(3)));
            mPaint.getTextBounds(mListPoint.get(3).getLabel(), 0, mListPoint.get(3).getLabel().length(), bounds);
            tempCanvas.drawText(mListPoint.get(3).getLabel(), mWidth / 2 - bounds.width() / 2, mHeight - mPointHeight - 10, mPaint);

            mPaint.setColor(Color.parseColor(mListColor.get(4)));
            mPaint.getTextBounds(mListPoint.get(4).getLabel(), 0, mListPoint.get(4).getLabel().length(), bounds);
            tempCanvas.drawText(mListPoint.get(4).getLabel(), mListPoint.get(4).getPullLabel(), mHeight - (mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60))), mPaint);

            mPaint.setColor(Color.parseColor(mListColor.get(5)));
            mPaint.getTextBounds(mListPoint.get(5).getLabel(), 0, mListPoint.get(5).getLabel().length(), bounds);
            tempCanvas.drawText(mListPoint.get(5).getLabel(), mListPoint.get(5).getPullLabel(), mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60)), mPaint);

            mPaint.setTextSize(mWidth / 20f);
            mPaint.setColor(Color.parseColor("#000000"));

            mPaint.getTextBounds(mListPoint.get(0).getPointLabel() + "pt", 0, mListPoint.get(0).getPointLabel().length() + 2, bounds);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            tempCanvas.drawText(mListPoint.get(0).getPointLabel(), mWidth / 2 - bounds.width() / 2, mTitleHeight + mPointHeight, mPaint);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            int onlyPoint0 = bounds.width() / 2;
            mPaint.getTextBounds(mListPoint.get(0).getPointLabel(), 0, mListPoint.get(0).getPointLabel().length(), bounds);
            tempCanvas.drawText("pt", mWidth / 2 - onlyPoint0 + bounds.width() + 10, mTitleHeight + mPointHeight, mPaint);

            mPaint.getTextBounds(mListPoint.get(1).getPointLabel() + "pt", 0, mListPoint.get(1).getPointLabel().length() + 2, bounds);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            tempCanvas.drawText(mListPoint.get(1).getPointLabel(), mWidth - bounds.width() - mListPoint.get(1).getPullPoint() - 10, mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60)) + mPointHeight, mPaint);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            int onlyPoint1 = bounds.width();
            mPaint.getTextBounds(mListPoint.get(1).getPointLabel(), 0, mListPoint.get(1).getPointLabel().length(), bounds);
            tempCanvas.drawText("pt", mWidth - onlyPoint1 - mListPoint.get(1).getPullPoint() + bounds.width(), mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60)) + mPointHeight, mPaint);

            mPaint.getTextBounds(mListPoint.get(2).getPointLabel() + "pt", 0, mListPoint.get(2).getPointLabel().length() + 2, bounds);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            tempCanvas.drawText(mListPoint.get(2).getPointLabel(), mWidth - bounds.width() - mListPoint.get(2).getPullPoint() - 10, mHeight - (mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60))) + mPointHeight, mPaint);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            int onlyPoint2 = bounds.width();
            mPaint.getTextBounds(mListPoint.get(2).getPointLabel(), 0, mListPoint.get(2).getPointLabel().length(), bounds);
            tempCanvas.drawText("pt", mWidth - onlyPoint2 - mListPoint.get(2).getPullPoint() + bounds.width(), mHeight - (mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60))) + mPointHeight, mPaint);

            mPaint.getTextBounds(mListPoint.get(3).getPointLabel() + "pt", 0, mListPoint.get(3).getPointLabel().length() + 2, bounds);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            tempCanvas.drawText(mListPoint.get(3).getPointLabel(), mWidth / 2 - bounds.width() / 2, mHeight - 10, mPaint);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            int onlyPoint3 = bounds.width() / 2;
            mPaint.getTextBounds(mListPoint.get(3).getPointLabel(), 0, mListPoint.get(3).getPointLabel().length(), bounds);
            tempCanvas.drawText("pt", mWidth / 2 - onlyPoint3 + bounds.width() + 10, mHeight - 10, mPaint);

            mPaint.getTextBounds(mListPoint.get(4).getPointLabel() + "pt", 0, mListPoint.get(4).getPointLabel().length() + 2, bounds);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            tempCanvas.drawText(mListPoint.get(4).getPointLabel(), mListPoint.get(4).getPullPoint(), mHeight - (mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60))) + mPointHeight, mPaint);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            mPaint.getTextBounds(mListPoint.get(4).getPointLabel(), 0, mListPoint.get(4).getPointLabel().length(), bounds);
            tempCanvas.drawText("pt", mListPoint.get(4).getPullPoint() + bounds.width() + 10, mHeight - (mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60))) + mPointHeight, mPaint);

            mPaint.getTextBounds(mListPoint.get(5).getPointLabel() + "pt", 0, mListPoint.get(5).getPointLabel().length() + 2, bounds);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            tempCanvas.drawText(mListPoint.get(5).getPointLabel(), mListPoint.get(5).getPullPoint(), mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60)) + mPointHeight, mPaint);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            mPaint.getTextBounds(mListPoint.get(5).getPointLabel(), 0, mListPoint.get(5).getPointLabel().length(), bounds);
            tempCanvas.drawText("pt", mListPoint.get(5).getPullPoint() + bounds.width() + 10, mPadding + mRadius - mRadius * (float) Math.cos(Math.toRadians(60)) + mPointHeight, mPaint);

            mPaint.setColor(Color.parseColor("#969696"));
            mPaint.setStrokeWidth(2);

            for (int i = 0; i < 6; i++) {
                mPaint.setStrokeWidth(1.5f);
                tempCanvas.rotate(60, mCenterX, mCenterY);
                tempCanvas.drawLine(mCenterX, mCenterY, mCenterX, mCenterY - mRadius, mPaint);
                for (int j = 1; j <= 10; j++) {
                    if (j % 5 == 0) {
                        mPaint.setStrokeWidth(3);
                        tempCanvas.drawLine(mCenterX - 5, mCenterY - mRadius / 10 * j, mCenterX + 5, mCenterY - mRadius / 10 * j, mPaint);
                    } else {
                        mPaint.setStrokeWidth(1.5f);
                        tempCanvas.drawLine(mCenterX - 5, mCenterY - mRadius / 10 * j, mCenterX + 5, mCenterY - mRadius / 10 * j, mPaint);
                    }
                }
            }
            mPaint.setStrokeWidth(1);

            tempCanvas.rotate(60, mCenterX, mCenterY);

            if (!mListOldPoint.isEmpty())
                for (int i = 0; i < 6; i++) {
                    mPaint.setColor(Color.parseColor(mListColor.get(i)));

                    float xA = -(mCenterY - mRadius * mListPoint.get(i).getPoint() / mMaxPoint - mCenterY) * (float) Math.sin(Math.toRadians(i * 60)) + mCenterX;
                    float yA = (mCenterY - mRadius * mListPoint.get(i).getPoint() / mMaxPoint - mCenterY) * (float) Math.cos(Math.toRadians(i * 60)) + mCenterY;

                    float xB = -(mCenterY - mRadius * mListOldPoint.get(i) / mMaxPoint - mCenterY) * (float) Math.sin(Math.toRadians(i * 60)) + mCenterX;
                    float yB = (mCenterY - mRadius * mListOldPoint.get(i) / mMaxPoint - mCenterY) * (float) Math.cos(Math.toRadians(i * 60)) + mCenterY;

                    mListPolygonPoint[i] = new Point((int) xA, (int) yA);
                    mListPolygonOldPoint[i] = new Point((int) xB, (int) yB);
                }
            else for (int i = 0; i < 6; i++) {
                mPaint.setColor(Color.parseColor(mListColor.get(i)));

                float xA = -(mCenterY - mRadius * mListPoint.get(i).getPoint() / mMaxPoint - mCenterY) * (float) Math.sin(Math.toRadians(i * 60)) + mCenterX;
                float yA = (mCenterY - mRadius * mListPoint.get(i).getPoint() / mMaxPoint - mCenterY) * (float) Math.cos(Math.toRadians(i * 60)) + mCenterY;

                mListPolygonPoint[i] = new Point((int) xA, (int) yA);
            }

            canvas.save();
            canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.restore();

            if (mListLabel.size() >= 3) {
                canvas.save();
                canvas.drawBitmap(maskingImage(drawBorder(), drawLine()), 0, 0, null);
                canvas.restore();
            }
            Bitmap bitmap2 = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            Canvas tempCanvas2 = new Canvas(bitmap2);
            drawPoly(tempCanvas2, getResources().getColor(android.R.color.white), mListPolygonPoint);
            Bitmap original = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.background_radar_chart), mWidth, mHeight, false);

            canvas.save();
            canvas.drawBitmap(maskingImage(original, bitmap2), 0, 0, null);
            canvas.restore();

            Bitmap result = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            Canvas tempCanvas3 = new Canvas(result);

            mPaint.setColor(Color.argb(30, 0, 0, 0));
            mPaint.setStrokeWidth(6);
            for (int i = 1; i < mListPolygonPoint.length; i++) {
                tempCanvas3.drawLine(mListPolygonPoint[i - 1].x, mListPolygonPoint[i - 1].y, mListPolygonPoint[i].x, mListPolygonPoint[i].y, mPaint);
            }
            tempCanvas3.drawLine(mListPolygonPoint[0].x, mListPolygonPoint[0].y, mListPolygonPoint[5].x, mListPolygonPoint[5].y, mPaint);

            if (!mListOldPoint.isEmpty()) {
                mPaint.setColor(Color.parseColor("#707070"));
                mPaint.setStrokeWidth(3);
                mPaint.setPathEffect(new DashPathEffect(new float[]{15, 10}, 0));
                for (int i = 1; i < mListPolygonOldPoint.length; i++) {
                    tempCanvas3.drawLine(mListPolygonOldPoint[i - 1].x, mListPolygonOldPoint[i - 1].y, mListPolygonOldPoint[i].x, mListPolygonOldPoint[i].y, mPaint);
                }
                tempCanvas3.drawLine(mListPolygonOldPoint[0].x, mListPolygonOldPoint[0].y, mListPolygonOldPoint[5].x, mListPolygonOldPoint[5].y, mPaint);
            }

            mPaint.setStrokeWidth(1);
            canvas.save();
            canvas.drawBitmap(result, 0, 0, null);
            canvas.restore();

            for (int i = 0; i < 6; i++) {
                mPaint.setColor(Color.parseColor(mListColor.get(i)));
                float xA = -(mCenterY - mRadius * mListPoint.get(i).getPoint() / mMaxPoint - mCenterY) * (float) Math.sin(Math.toRadians(i * 60)) + mCenterX;
                float yA = (mCenterY - mRadius * mListPoint.get(i).getPoint() / mMaxPoint - mCenterY) * (float) Math.cos(Math.toRadians(i * 60)) + mCenterY;
                tempCanvas3.drawCircle(xA, yA, mWidth / 105, mPaint);
            }
            if (mListLabel.size() >= 3) {
                canvas.save();
                canvas.drawBitmap(drawLabel(), 0, 0, null);
                canvas.restore();
            }
        } else {
            Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            Canvas tempCanvas = new Canvas(bitmap);

            mPaint.setTextSize(mWidth / 15f);
            float xPos = (mCenterX - (int) mPaint.measureText(String.valueOf("No data")) / 2);
            float yPos = (int) (mCenterY - ((mPaint.descent() + mPaint.ascent()) / 2));
            tempCanvas.drawText(String.valueOf("No data"), xPos, yPos, mPaint);

            canvas.save();
            canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.restore();
        }
    }

    private void drawPoly(Canvas canvas, int color, Point[] points) {
        if (points.length < 2) {
            return;
        }

        Paint polyPaint = new Paint();
        polyPaint.setAntiAlias(true);
        polyPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
        polyPaint.setColor(color);
        polyPaint.setStyle(Paint.Style.FILL);

        Path polyPath = new Path();
        polyPath.moveTo(points[0].x, points[0].y);
        int i, len;
        len = points.length;
        for (i = 0; i < len; i++) {
            polyPath.lineTo(points[i].x, points[i].y);
        }
        polyPath.lineTo(points[0].x, points[0].y);

        canvas.drawPath(polyPath, polyPaint);
    }

    private Bitmap drawLabel() {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(bitmap);
        Point[] points = new Point[6];
        for (int i = 0; i < 6; i++) {
            float xA = -(mCenterY - mRadius - mCenterY) * (float) Math.sin(Math.toRadians(i * 60)) + mCenterX;
            float yA = (mCenterY - mRadius - mCenterY) * (float) Math.cos(Math.toRadians(i * 60)) + mCenterY;

            points[i] = new Point((int) xA, (int) yA);
        }
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(3);

        Rect bounds = new Rect();
        mPaint.setTextSize(mWidth / 22f);

        //label 1
        mPaint.getTextBounds(mListLabel.get(0), 0, mListLabel.get(0).length(), bounds);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#ffffff"));
        mPaint.setAlpha(150);
        tempCanvas.drawRect(mWidth - mPaddingLabel * 2 - bounds.width(), points[1].y + mPaddingLabel, mWidth, points[1].y + mPaddingLabel * 3 + bounds.height(), mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor(mListColor.get(1)));
        mPaint.setAlpha(255);
        tempCanvas.drawRect(mWidth - mPaddingLabel * 2 - bounds.width(), points[1].y + mPaddingLabel, mWidth, points[1].y + mPaddingLabel * 3 + bounds.height(), mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        tempCanvas.drawText(mListLabel.get(0), mWidth - mPaddingLabel - bounds.width(), points[1].y + mPaddingLabel * 2 + bounds.height(), mPaint);

        //label 2
        mPaint.getTextBounds(mListLabel.get(1), 0, mListLabel.get(1).length(), bounds);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#ffffff"));
        mPaint.setAlpha(150);
        tempCanvas.drawRect(mWidth / 2 - bounds.width() / 2 - mPaddingLabel, points[3].y - mPaddingLabel * 7 - bounds.height(), mWidth / 2 + bounds.width() / 2 + mPaddingLabel, points[3].y - mPaddingLabel * 5, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor(mListColor.get(3)));
        mPaint.setAlpha(255);
        tempCanvas.drawRect(mWidth / 2 - bounds.width() / 2 - mPaddingLabel, points[3].y - mPaddingLabel * 7 - bounds.height(), mWidth / 2 + bounds.width() / 2 + mPaddingLabel, points[3].y - mPaddingLabel * 5, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        tempCanvas.drawText(mListLabel.get(1), mWidth / 2 - bounds.width() / 2, points[3].y - mPaddingLabel * 6, mPaint);

        //label 3
        mPaint.getTextBounds(mListLabel.get(2), 0, mListLabel.get(2).length(), bounds);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#ffffff"));
        mPaint.setAlpha(150);
        tempCanvas.drawRect(0, points[5].y + mPaddingLabel, bounds.width() + mPaddingLabel * 2, points[5].y + mPaddingLabel * 3 + bounds.height(), mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor(mListColor.get(5)));
        mPaint.setAlpha(255);
        tempCanvas.drawRect(0, points[5].y + mPaddingLabel, bounds.width() + mPaddingLabel * 2, points[5].y + mPaddingLabel * 3 + bounds.height(), mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        tempCanvas.drawText(mListLabel.get(2), mPaddingLabel, points[5].y + mPaddingLabel * 2 + bounds.height(), mPaint);

        return bitmap;
    }

    private Bitmap drawLine() {
        float a = (float) Math.sqrt(Math.pow(mWidth, 2) + Math.pow(mHeight, 2));
        Bitmap bitmap = Bitmap.createBitmap((int) a, (int) a, Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(bitmap);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
        tempCanvas.translate((mWidth - a) / 2, (mHeight - a) / 2);
        tempCanvas.rotate(-45, a / 2, a / 2);
        for (int i = 0; i < a; i++) {
            if (i % 7 == 0) {
                mPaint.setAlpha(255);
                tempCanvas.drawLine(i, 0, i, a, mPaint);
            } else {
                mPaint.setAlpha(20);
                tempCanvas.drawLine(i, 0, i, a, mPaint);
            }
        }
        return bitmap;
    }

    private Bitmap drawBorder() {
        float a = (float) Math.sqrt(Math.pow(mWidth, 2) + Math.pow(mHeight, 2));
        Bitmap bitmap = Bitmap.createBitmap((int) a, (int) a, Bitmap.Config.ARGB_8888);
        Point[] points = new Point[6];
        for (int i = 0; i < 6; i++) {
            float xA = -(mCenterY - mRadius - mCenterY) * (float) Math.sin(Math.toRadians(i * 60)) + mCenterX;
            float yA = (mCenterY - mRadius - mCenterY) * (float) Math.cos(Math.toRadians(i * 60)) + mCenterY;

            points[i] = new Point((int) xA, (int) yA);
        }

        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);

        Canvas tempCanvas1 = new Canvas(bitmap);
        Path polyPath1 = new Path();
        mPaint.setColor(Color.parseColor(mListColor.get(1)));
        polyPath1.moveTo(mListPolygonPoint[0].x, mListPolygonPoint[0].y);
        polyPath1.lineTo(mListPolygonPoint[0].x, mListPolygonPoint[0].y);
        polyPath1.lineTo(points[0].x, points[0].y);
        polyPath1.lineTo(points[1].x, points[1].y);
        polyPath1.lineTo(points[2].x, points[2].y);
        polyPath1.lineTo(mListPolygonPoint[2].x, mListPolygonPoint[2].y);
        polyPath1.lineTo(mListPolygonPoint[1].x, mListPolygonPoint[1].y);
        polyPath1.lineTo(mListPolygonPoint[0].x, mListPolygonPoint[0].y);
        tempCanvas1.drawPath(polyPath1, mPaint);

        Canvas tempCanvas2 = new Canvas(bitmap);
        Path polyPath2 = new Path();
        mPaint.setColor(Color.parseColor(mListColor.get(3)));
        polyPath2.moveTo(mListPolygonPoint[2].x, mListPolygonPoint[2].y);
        polyPath2.lineTo(mListPolygonPoint[2].x, mListPolygonPoint[2].y);
        polyPath2.lineTo(points[2].x, points[2].y);
        polyPath2.lineTo(points[3].x, points[3].y);
        polyPath2.lineTo(points[4].x, points[4].y);
        polyPath2.lineTo(mListPolygonPoint[4].x, mListPolygonPoint[4].y);
        polyPath2.lineTo(mListPolygonPoint[3].x, mListPolygonPoint[3].y);
        polyPath2.lineTo(mListPolygonPoint[2].x, mListPolygonPoint[2].y);
        tempCanvas2.drawPath(polyPath2, mPaint);

        Canvas tempCanvas3 = new Canvas(bitmap);
        Path polyPath3 = new Path();
        mPaint.setColor(Color.parseColor(mListColor.get(5)));
        polyPath3.moveTo(mListPolygonPoint[4].x, mListPolygonPoint[4].y);
        polyPath3.lineTo(mListPolygonPoint[4].x, mListPolygonPoint[4].y);
        polyPath3.lineTo(points[4].x, points[4].y);
        polyPath3.lineTo(points[5].x, points[5].y);
        polyPath3.lineTo(points[0].x, points[0].y);
        polyPath3.lineTo(mListPolygonPoint[0].x, mListPolygonPoint[0].y);
        polyPath3.lineTo(mListPolygonPoint[5].x, mListPolygonPoint[5].y);
        polyPath3.lineTo(mListPolygonPoint[4].x, mListPolygonPoint[4].y);
        tempCanvas3.drawPath(polyPath3, mPaint);
        return bitmap;
    }

    private Bitmap maskingImage(Bitmap s1, Bitmap s2) {
        Bitmap original = s1;
        Bitmap mask = s2;
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAlpha((int) (0.7 * 255));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(original, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        return result;
    }

    public void setListPoint(ArrayList<PointLabel> pListPointLabel) {
        this.mListPointTemp = pListPointLabel;
        this.invalidate();
    }

    public void setListOldPoint(ArrayList<Float> pListPointLabel) {
        this.mListOldPoint = pListPointLabel;
        this.invalidate();
    }

    public List<String> getmListLabel() {
        return mListLabel;
    }

    public void setmListLabel(List<String> mListLabel) {
        this.mListLabel = mListLabel;
        invalidate();
    }
}
