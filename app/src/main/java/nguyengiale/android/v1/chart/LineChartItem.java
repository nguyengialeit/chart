package nguyengiale.android.v1.chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by giale on 3/14/17.
 */

public class LineChartItem extends View {
    private float mMaxPoint, mStartPoint, mCenterPoint, mEndPoint, mLabelSize;
    private String mLabel;
    private Paint mPaint;
    private int mWidth, mHeight, mLabelHeight;
    private Point[] mListPolygonPoint;
    private boolean isShowCursor = false;
    private String mColor = "";

    public LineChartItem(Context context) {
        super(context);
    }

    public LineChartItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public LineChartItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LineChartItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h - mLabelHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mColor.equals("")) {
            if (mStartPoint != 0 && mEndPoint != 0) {
                mListPolygonPoint = new Point[5];
                mListPolygonPoint[0] = new Point(0, (int) (mHeight - mStartPoint / mMaxPoint * mHeight));
                mListPolygonPoint[1] = new Point(mWidth / 2, (int) (mHeight - mCenterPoint / mMaxPoint * mHeight));
                mListPolygonPoint[2] = new Point(mWidth, (int) (mHeight - mEndPoint / mMaxPoint * mHeight));
                mListPolygonPoint[3] = new Point(mWidth, mHeight);
                mListPolygonPoint[4] = new Point(0, mHeight);


            } else {
                mListPolygonPoint = new Point[4];
                if (mStartPoint != 0) {
                    mListPolygonPoint[0] = new Point(0, (int) (mHeight - mStartPoint / mMaxPoint * mHeight));
                    mListPolygonPoint[1] = new Point(mWidth / 2, (int) (mHeight - mCenterPoint / mMaxPoint * mHeight));
                    mListPolygonPoint[2] = new Point(mWidth / 2, mHeight);
                    mListPolygonPoint[3] = new Point(0, mHeight);
                } else if (mEndPoint != 0) {
                    mListPolygonPoint[0] = new Point(mWidth / 2, (int) (mHeight - mCenterPoint / mMaxPoint * mHeight));
                    mListPolygonPoint[1] = new Point(mWidth, (int) (mHeight - mEndPoint / mMaxPoint * mHeight));
                    mListPolygonPoint[2] = new Point(mWidth, mHeight);
                    mListPolygonPoint[3] = new Point(mWidth / 2, mHeight);
                }
            }

            //Draw result after performing masking
            canvas.save();
            canvas.drawBitmap(maskingImage(drawPoly(mListPolygonPoint), drawBackground()), 0, 0, null);
//        canvas.drawBitmap(drawBackground(), 0, 0, null);
            canvas.restore();

            Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight + mLabelHeight, Bitmap.Config.ARGB_8888);
            Canvas tempCanvas = new Canvas(bitmap);

            mPaint.setColor(Color.parseColor("#f8514f"));
            mPaint.setStrokeWidth(3);
            mPaint.setPathEffect(null);
            if (mStartPoint != 0)
                tempCanvas.drawLine(0, mHeight - mStartPoint / mMaxPoint * mHeight, mWidth / 2, mHeight - mCenterPoint / mMaxPoint * mHeight, mPaint);
            if (mEndPoint != 0)
                tempCanvas.drawLine(mWidth / 2, mHeight - mCenterPoint / mMaxPoint * mHeight, mWidth, mHeight - mEndPoint / mMaxPoint * mHeight, mPaint);
            tempCanvas.drawCircle(mWidth / 2, mHeight - mCenterPoint / mMaxPoint * mHeight, 7, mPaint);

            mPaint.setColor(Color.parseColor("#a6a6a6"));
            mPaint.setStrokeWidth(2);
            tempCanvas.drawLine(0, mHeight + mLabelHeight / 3, mWidth, mHeight + mLabelHeight / 3, mPaint);

            Rect bounds = new Rect();
            mPaint.setColor(Color.parseColor("#a6a6a6"));
            mPaint.setTextSize(mLabelSize);
            if (mColor.equals("")) {
                mPaint.getTextBounds(String.valueOf(CommonClass.splitDate(mLabel).getDay() + "日"), 0, String.valueOf(CommonClass.splitDate(mLabel).getDay() + "日").length(), bounds);
                tempCanvas.drawText(String.valueOf(CommonClass.splitDate(mLabel).getDay() + "日"), mWidth / 2 - bounds.width() / 2, mHeight + mLabelHeight, mPaint);
            } else {
                mPaint.getTextBounds(String.valueOf(CommonClass.splitDate(mLabel).getMonth() + "月"), 0, String.valueOf(CommonClass.splitDate(mLabel).getMonth() + "月").length(), bounds);
                tempCanvas.drawText(String.valueOf(CommonClass.splitDate(mLabel).getMonth() + "月"), mWidth / 2 - bounds.width() / 2, mHeight + mLabelHeight, mPaint);
            }


            mPaint.setStrokeWidth(3);
            if (isShowCursor)
                tempCanvas.drawLine(mWidth / 2, 0, mWidth / 2, mHeight + mLabelHeight / 3, mPaint);
            canvas.save();
            canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.restore();
        } else {
            if (mStartPoint != 0 && mEndPoint != 0) {
                mListPolygonPoint = new Point[5];
                mListPolygonPoint[0] = new Point(0, (int) (mStartPoint / mMaxPoint * mHeight));
                mListPolygonPoint[1] = new Point(mWidth / 2, (int) (mCenterPoint / mMaxPoint * mHeight));
                mListPolygonPoint[2] = new Point(mWidth, (int) (mEndPoint / mMaxPoint * mHeight));
                mListPolygonPoint[3] = new Point(mWidth, mHeight);
                mListPolygonPoint[4] = new Point(0, mHeight);
            } else {
                mListPolygonPoint = new Point[4];
                if (mStartPoint != 0) {
                    mListPolygonPoint[0] = new Point(0, (int) (mStartPoint / mMaxPoint * mHeight));
                    mListPolygonPoint[1] = new Point(mWidth / 2, (int) (mCenterPoint / mMaxPoint * mHeight));
                    mListPolygonPoint[2] = new Point(mWidth / 2, mHeight);
                    mListPolygonPoint[3] = new Point(0, mHeight);
                } else if (mEndPoint != 0) {
                    mListPolygonPoint[0] = new Point(mWidth / 2, (int) (mCenterPoint / mMaxPoint * mHeight));
                    mListPolygonPoint[1] = new Point(mWidth, (int) (mEndPoint / mMaxPoint * mHeight));
                    mListPolygonPoint[2] = new Point(mWidth, mHeight);
                    mListPolygonPoint[3] = new Point(mWidth / 2, mHeight);
                }
            }

            //Draw result after performing masking
            canvas.save();
            canvas.drawBitmap(maskingImage(drawPoly(mListPolygonPoint), drawBackground()), 0, 0, null);
//        canvas.drawBitmap(drawBackground(), 0, 0, null);
            canvas.restore();

            Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight + mLabelHeight, Bitmap.Config.ARGB_8888);
            Canvas tempCanvas = new Canvas(bitmap);

            mPaint.setColor(Color.parseColor(mColor));
            mPaint.setStrokeWidth(3);
            mPaint.setPathEffect(null);
            if (mStartPoint != 0)
                tempCanvas.drawLine(0, mStartPoint / mMaxPoint * mHeight, mWidth / 2, mCenterPoint / mMaxPoint * mHeight, mPaint);
            if (mEndPoint != 0)
                tempCanvas.drawLine(mWidth / 2, mCenterPoint / mMaxPoint * mHeight, mWidth, mEndPoint / mMaxPoint * mHeight, mPaint);
            tempCanvas.drawCircle(mWidth / 2, mCenterPoint / mMaxPoint * mHeight, 7, mPaint);

            int color = Color.TRANSPARENT;
            Drawable background = getRootView().getBackground();
            if (background instanceof ColorDrawable)
                color = ((ColorDrawable) background).getColor();
            mPaint.setColor(color);
            tempCanvas.drawRect(0, mHeight, mWidth, mHeight + mLabelHeight, mPaint);

            mPaint.setColor(Color.parseColor("#a6a6a6"));
            mPaint.setStrokeWidth(2);
            tempCanvas.drawLine(0, mHeight + mLabelHeight / 3, mWidth, mHeight + mLabelHeight / 3, mPaint);

            Rect bounds = new Rect();
            mPaint.setColor(Color.parseColor("#a6a6a6"));
            mPaint.setTextSize(mLabelSize);
            if (mColor.equals("")) {
                mPaint.getTextBounds(String.valueOf(CommonClass.splitDate(mLabel).getDay() + "日"), 0, String.valueOf(CommonClass.splitDate(mLabel).getDay() + "日").length(), bounds);
                tempCanvas.drawText(String.valueOf(CommonClass.splitDate(mLabel).getDay() + "日"), mWidth / 2 - bounds.width() / 2, mHeight + mLabelHeight, mPaint);
            } else {
                mPaint.getTextBounds(String.valueOf(CommonClass.splitDate(mLabel).getMonth() + "月"), 0, String.valueOf(CommonClass.splitDate(mLabel).getMonth() + "月").length(), bounds);
                tempCanvas.drawText(String.valueOf(CommonClass.splitDate(mLabel).getMonth() + "月"), mWidth / 2 - bounds.width() / 2, mHeight + mLabelHeight, mPaint);
            }


            mPaint.setStrokeWidth(3);
            if (isShowCursor)
                tempCanvas.drawLine(mWidth / 2, 0, mWidth / 2, mHeight + mLabelHeight / 3, mPaint);
            canvas.save();
            canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.restore();
        }
    }

    private Bitmap maskingImage(Bitmap s1, Bitmap s2) {
        Bitmap original = s1;
        Bitmap mask = s2;
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAlpha((int) (0.7 * 255));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mCanvas.drawBitmap(original, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        return result;
    }

    private Bitmap drawPoly(Point[] points) {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(bitmap);

        if (points.length < 2) {
            return null;
        }

        Paint polyPaint = new Paint();
        polyPaint.setAntiAlias(true);
        polyPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
        polyPaint.setStyle(Paint.Style.FILL);

        Path polyPath = new Path();
        polyPath.moveTo(points[0].x, points[0].y);
        int i, len;
        len = points.length;
        for (i = 0; i < len; i++) {
            polyPath.lineTo(points[i].x, points[i].y);
        }
        polyPath.lineTo(points[0].x, points[0].y);

        tempCanvas.drawPath(polyPath, polyPaint);
        tempCanvas.drawBitmap(bitmap, 0, 0, null);

        return bitmap;
    }

    private Bitmap drawBackground() {
        if (mColor.equals("")) {
            Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            Canvas tempCanvas = new Canvas(bitmap);
            Paint polyPaint = new Paint();
            Path polyPath = new Path();
            Point[] bottom = new Point[4];
            Point[] top = new Point[4];

            top[0] = new Point(0, (int) (mHeight - 10 / mMaxPoint * mHeight));
            top[1] = new Point(0, 0);
            top[2] = new Point(mWidth, 0);
            top[3] = new Point(mWidth, (int) (mHeight - 10 / mMaxPoint * mHeight));

            bottom[0] = new Point(0, (int) (mHeight - 10 / mMaxPoint * mHeight));
            bottom[1] = new Point(mWidth, (int) (mHeight - 10 / mMaxPoint * mHeight));
            bottom[2] = new Point(mWidth, mHeight);
            bottom[3] = new Point(0, mHeight);

            LinearGradient shader = new LinearGradient(0, bottom[0].y, 0, mHeight,
                    new int[]{
                            0xFFf8514f,
                            0xFFf8514f,
                            0xFFb3d614,
                            0x17c4ee},
                    new float[]{
                            0, 0.30f, 0.80f, 1},
                    Shader.TileMode.REPEAT);
            polyPaint.setShader(shader);
            polyPaint.setAntiAlias(true);
            polyPaint.setStyle(Paint.Style.FILL);
            polyPath.moveTo(bottom[0].x, bottom[0].y);
            int i, len;
            len = bottom.length;
            for (i = 0; i < len; i++) {
                polyPath.lineTo(bottom[i].x, bottom[i].y);
            }
            polyPath.lineTo(bottom[0].x, bottom[0].y);
            tempCanvas.drawPath(polyPath, polyPaint);

            Paint polyPaint2 = new Paint();
            Path polyPath2 = new Path();
            polyPaint2.setShader(null);
            polyPaint2.setColor(Color.parseColor("#f8514f"));
            polyPaint2.setAntiAlias(true);
            polyPaint2.setStyle(Paint.Style.FILL);
            polyPath2.moveTo(top[0].x, top[0].y);
            len = top.length;
            for (i = 0; i < len; i++) {
                polyPath2.lineTo(top[i].x, top[i].y);
            }
            polyPath2.lineTo(top[0].x, top[0].y);
            tempCanvas.drawPath(polyPath2, polyPaint2);
            tempCanvas.drawBitmap(bitmap, 0, 0, null);

            return bitmap;
        } else {
            Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            Canvas tempCanvas = new Canvas(bitmap);
            Paint polyPaint = new Paint();
            Path polyPath = new Path();
            Point[] bottom = new Point[4];

            bottom[0] = new Point(0, 0);
            bottom[1] = new Point(mWidth, 0);
            bottom[2] = new Point(mWidth, mHeight);
            bottom[3] = new Point(0, mHeight);

            LinearGradient shader = null;
            try {
                shader = new LinearGradient(0, 0, 0, mHeight,
                        Color.parseColor(mColor),
                        Color.WHITE,
                        Shader.TileMode.REPEAT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            polyPaint.setShader(shader);
            polyPaint.setAntiAlias(true);
            polyPaint.setStyle(Paint.Style.FILL);
            polyPath.moveTo(bottom[0].x, bottom[0].y);
            int i, len;
            len = bottom.length;
            for (i = 0; i < len; i++) {
                polyPath.lineTo(bottom[i].x, bottom[i].y);
            }
            polyPath.lineTo(bottom[0].x, bottom[0].y);
            tempCanvas.drawPath(polyPath, polyPaint);

            return bitmap;
        }
    }

    public float getmEndPoint() {
        return mEndPoint;
    }

    public void setmEndPoint(float mEndPoint) {
        this.mEndPoint = mEndPoint;
    }

    public float getmCenterPoint() {
        return mCenterPoint;
    }

    public void setmCenterPoint(float mCenterPoint) {
        this.mCenterPoint = mCenterPoint;
    }

    public float getmStartPoint() {
        return mStartPoint;
    }

    public void setmStartPoint(float mStartPoint) {
        this.mStartPoint = mStartPoint;
    }

    public float getmMaxPoint() {
        return mMaxPoint;
    }

    public void setmMaxPoint(float mMaxPoint) {
        this.mMaxPoint = mMaxPoint;
    }

    public String getmLabel() {
        return mLabel;
    }

    public void setmLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public int getmLabelHeight() {
        return mLabelHeight;
    }

    public void setmLabelHeight(int mLabelHeight) {
        this.mLabelHeight = mLabelHeight;
    }

    public float getmLabelSize() {
        return mLabelSize;
    }

    public void setmLabelSize(float mLabelSize) {
        this.mLabelSize = mLabelSize;
    }

    public boolean isShowCursor() {
        return isShowCursor;
    }

    public void setShowCursor(boolean showCursor) {
        isShowCursor = showCursor;
        invalidate();
    }

    public String getmColor() {
        return mColor;
    }

    public void setmColor(String mColor) {
        this.mColor = mColor;
        invalidate();
    }
}
