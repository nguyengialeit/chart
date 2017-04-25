package nguyengiale.android.v1.chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import nguyengiale.android.v1.chart.object.PointLabel;
import nguyengiale.android.v1.chart.object.SplitDate;

/**
 * Created by giale on 3/13/17.
 */

public class LineChart extends RelativeLayout {
    private int mWidth, mHeight, mLabelHeight;
    private Paint mPaint;
    private float mMaxPoint, mDateHeight, mPointHeight, mLabelSize;
    private float mTopChart, mBottomChart, mLeftChart;
    private int mUnitLineNumber = 2;
    private float mMaxTop, mEachUnit, mEachUnitPixel, mEachUnitHeight;
    private List<Integer> mListUnit = new ArrayList<>();
    private List<PointLabel> mListData = new ArrayList<>();
    private RecyclerView mSnappingRecyclerView;
    private LineChartBackground mLinearBackground;
    private String mDate = "", mPoint = "";
    private boolean isFirst = true;
    private int selectedPosition = 0;
    private String color = "";

    public LineChart(Context context) {
        super(context);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mLinearBackground = new LineChartBackground(getContext());
        addView(mLinearBackground);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mListData.size() > 0) {
            if (isFirst) {
                mDate = mListData.get(0).getLabel();
                mPoint = String.valueOf(mListData.get(0).getPoint());
            }
            mMaxPoint = mListData.get(0).getPoint();
            if (color.equals("")) {
                mMaxPoint = mListData.get(0).getPoint();
                for (int i = 0; i < mListData.size(); i++) {
                    if (mListData.get(i).getPoint() > mMaxPoint)
                        mMaxPoint = mListData.get(i).getPoint();
                }
                if (mMaxPoint % 2 == 1) {
                    mMaxPoint = mMaxPoint + 1;
                }
            } else {
                mMaxPoint = 50;
            }
            if (mMaxPoint % 2 == 1) {
                mMaxPoint = mMaxPoint + 1;
            }

            Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            Canvas tempCanvas = new Canvas(bitmap);
            Rect bounds = new Rect();

            if (color.equals("")) {
                mPaint.setColor(Color.parseColor("#000000"));
                mPaint.setTextSize(mWidth / 20f);
                SplitDate date = CommonClass.splitDate(mDate);

                mPaint.getTextBounds(date.getYear() + "年" + date.getMonth() + "月" + date.getDay() + "日", 0, String.valueOf(date.getYear() + "年" + date.getMonth() + "月" + date.getDay() + "日").length(), bounds);
                mDateHeight = bounds.height() + 40;
                tempCanvas.drawText(date.getYear() + "年" + date.getMonth() + "月" + date.getDay() + "日", mWidth / 2 - bounds.width() / 2, mDateHeight - 20, mPaint);

                mPaint.setTextSize(mWidth / 10f);
                mPaint.getTextBounds(mPoint, 0, mPoint.length(), bounds);
                mPointHeight = bounds.height();
                tempCanvas.drawText(mPoint, mWidth / 2 - bounds.width() / 2, mDateHeight + mPointHeight + 10, mPaint);
                mPaint.setTextSize(mWidth / 25f);

                tempCanvas.drawText("pt", mWidth / 2 + bounds.width() / 2 + 20, mDateHeight + mPointHeight + 10, mPaint);

                Bitmap d = null;
                if (Float.parseFloat(mPoint) >= 5)
                    d = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_1_big);
                else if (Float.parseFloat(mPoint) >= 1)
                    d = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_3_big);
                else
                    d = BitmapFactory.decodeResource(getResources(), R.drawable.ico_company_status_2_big);

                d = Bitmap.createScaledBitmap(d, (int) (d.getWidth() * (mPointHeight + 10) / d.getHeight()), (int) mPointHeight + 10, true);
                int mDrawableWidth = d.getWidth();
                int mDrawableHeight = d.getHeight();

                canvas.drawBitmap(d, mWidth / 2 - bounds.width() / 2 - mDrawableWidth, mDateHeight + mPointHeight - mDrawableHeight + 10, null);
            } else {
                mPaint.setColor(Color.parseColor("#000000"));
                mPaint.setTextSize(mWidth / 20f);
                SplitDate date = CommonClass.splitDate(mDate);

                mPaint.getTextBounds(date.getYear() + "年" + date.getMonth() + "月", 0, String.valueOf(date.getYear() + "年" + date.getMonth() + "月").length(), bounds);
                mDateHeight = bounds.height() + 20;
                tempCanvas.drawText(date.getYear() + "年" + date.getMonth() + "月", mWidth / 2 - bounds.width() / 2, mDateHeight - 20, mPaint);

                mPaint.setTextSize(mWidth / 12f);
                mPaint.getTextBounds(String.valueOf((int) Float.parseFloat(mPoint)), 0, String.valueOf((int) Float.parseFloat(mPoint)).length(), bounds);
                mPointHeight = bounds.height();
                tempCanvas.drawText(String.valueOf((int) Float.parseFloat(mPoint)), mWidth / 2 - bounds.width() / 2, mDateHeight + mPointHeight + 10, mPaint);
                mPaint.setTextSize(mWidth / 25f);

                tempCanvas.drawText("位", mWidth / 2 + bounds.width() / 2 + 10, mDateHeight + mPointHeight + 10, mPaint);
            }

            canvas.save();
            canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.restore();
        }
    }

    public float getmMaxPoint() {
        return mMaxPoint;
    }

    public void setmMaxPoint(float mMaxPoint) {
        this.mMaxPoint = mMaxPoint;
    }

    public List<PointLabel> getmListData() {
        return mListData;
    }

    public void setmListData(List<PointLabel> mListData) {
        this.mListData = mListData;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
        invalidate();
    }

    public String getmPoint() {
        return mPoint;
    }

    public void setmPoint(String mPoint) {
        this.mPoint = mPoint;
        invalidate();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        invalidate();
    }

    private class LineChartBackground extends View {

        public LineChartBackground(Context context) {
            super(context);
        }

        public LineChartBackground(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public LineChartBackground(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public LineChartBackground(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if (mListData.size() > 0) {

                if (isFirst) {
                    mDate = mListData.get(0).getLabel();
                    mPoint = String.valueOf(mListData.get(0).getPoint());
                }
                if (color.equals("")) {
                    mMaxPoint = mListData.get(0).getPoint();
                    for (int i = 0; i < mListData.size(); i++) {
                        if (mListData.get(i).getPoint() > mMaxPoint)
                            mMaxPoint = mListData.get(i).getPoint();
                    }
                    if (mMaxPoint % 2 == 1) {
                        mMaxPoint = mMaxPoint + 1;
                    }
                } else {
                    mMaxPoint = 50;
                }


                //Draw date
                Rect bounds = new Rect();
                mPaint.setTextSize(mWidth / 20f);
                mPaint.getTextBounds(mDate, 0, mDate.length(), bounds);
                mDateHeight = bounds.height() + 40;
                mPaint.setTextSize(mWidth / 10f);
                mPaint.getTextBounds(mPoint, 0, mPoint.length(), bounds);
                mPointHeight = bounds.height();

                Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
                Canvas tempCanvas = new Canvas(bitmap);
                mPaint.setTextSize(mWidth / 25f);
                mPaint.getTextBounds(String.valueOf(mMaxPoint), 0, String.valueOf(mMaxPoint).length(), bounds);
                mLabelHeight = bounds.height() * 3;
                mLabelSize = mPaint.getTextSize();
                mTopChart = mDateHeight + mPointHeight + 40;
                mBottomChart = mHeight - mLabelHeight;
                mLeftChart = bounds.width();
                mEachUnitHeight = bounds.height();

                mEachUnit = mMaxPoint / mUnitLineNumber;
                mEachUnitPixel = (mBottomChart - mTopChart) / mUnitLineNumber;

                mListUnit.clear();
                if (color.equals("")) {
                    for (int i = 0; i <= mUnitLineNumber; i++) {
                        mListUnit.add(Math.round(i * mEachUnit));
                    }
                } else {
                    for (int i = mUnitLineNumber; i >= 0; i--) {
                        if (Math.round(i * mEachUnit) == 0)
                            mListUnit.add(1);
                        else
                            mListUnit.add(Math.round(i * mEachUnit));
                    }
                }

                mPaint.setColor(Color.parseColor("#a6a6a6"));
                mPaint.setStrokeWidth(2);

                for (int i = 0; i < mListUnit.size(); i++) {
                    if (i != 0) {
                        mPaint.setPathEffect(new DashPathEffect(new float[]{3, 5}, 0));
                        tempCanvas.drawLine(mLeftChart, mBottomChart - mEachUnitPixel * i, mWidth, mBottomChart - mEachUnitPixel * i, mPaint);

                    } else {
                        mPaint.setPathEffect(null);
                    }

                    mPaint.getTextBounds(String.valueOf(mListUnit.get(i)), 0, String.valueOf(mListUnit.get(i)).length(), bounds);
                    tempCanvas.drawText(String.valueOf(mListUnit.get(i)), (mLeftChart - bounds.width()) / 2, mBottomChart - mEachUnitPixel * i + mEachUnitHeight / 2, mPaint);
                }

                canvas.save();
                canvas.drawBitmap(bitmap, 0, 0, null);
                canvas.restore();

                if (isFirst) {
                    mSnappingRecyclerView = new RecyclerView(getContext());
                    LayoutParams param = new LayoutParams((int) (mWidth - mLeftChart), (int) (mHeight - mTopChart));
                    param.addRule(ALIGN_PARENT_RIGHT);
                    param.addRule(ALIGN_PARENT_BOTTOM);
                    mSnappingRecyclerView.setLayoutParams(param);
                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    mSnappingRecyclerView.setLayoutManager(manager);
                    SnapHelper snapHelper = new LinearSnapHelper();
                    snapHelper.attachToRecyclerView(mSnappingRecyclerView);
                    addView(mSnappingRecyclerView);
                    mSnappingRecyclerView.setAdapter(new lineChartAdapter());
                    isFirst = false;
                }
            } else {
                Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
                Canvas tempCanvas = new Canvas(bitmap);

                mPaint.setTextSize(mWidth / 18f);
                float xPos = (mWidth / 2 - (int) mPaint.measureText(String.valueOf("No data")) / 2);
                float yPos = (int) (mHeight / 2 - ((mPaint.descent() + mPaint.ascent()) / 2));
                tempCanvas.drawText(String.valueOf("No data"), xPos, yPos, mPaint);

                canvas.save();
                canvas.drawBitmap(bitmap, 0, 0, null);
                canvas.restore();
            }
        }
    }

    public class lineChartAdapter extends RecyclerView.Adapter {

        public lineChartAdapter() {
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_linechart, parent, false);
            return new VHItem(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof VHItem) {
                final VHItem item = (VHItem) holder;
                item.lineChartItemView.setmMaxPoint(mMaxPoint);
                item.lineChartItemView.setmLabel(mListData.get(position).getLabel());
                item.lineChartItemView.setmLabelHeight(mLabelHeight);
                item.lineChartItemView.setmLabelSize(mLabelSize);
                if (position == 0) {
                    item.lineChartItemView.setmStartPoint(0);
                    item.lineChartItemView.setmCenterPoint(mListData.get(position).getPoint());
                    if (mListData.size() > position + 1)
                        item.lineChartItemView.setmEndPoint((mListData.get(position).getPoint() + mListData.get(position + 1).getPoint()) / 2);
                } else if (position == getItemCount() - 1) {
                    item.lineChartItemView.setmStartPoint((mListData.get(position).getPoint() + mListData.get(position - 1).getPoint()) / 2);
                    item.lineChartItemView.setmCenterPoint(mListData.get(position).getPoint());
                    item.lineChartItemView.setmEndPoint(0);
                } else {
                    item.lineChartItemView.setmStartPoint((mListData.get(position).getPoint() + mListData.get(position - 1).getPoint()) / 2);
                    item.lineChartItemView.setmCenterPoint(mListData.get(position).getPoint());
                    if (mListData.size() > position + 1)
                        item.lineChartItemView.setmEndPoint((mListData.get(position).getPoint() + mListData.get(position + 1).getPoint()) / 2);
                }
                if (position == selectedPosition) {
                    item.lineChartItemView.setShowCursor(true);
                } else {
                    item.lineChartItemView.setShowCursor(false);
                }
                item.itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setmDate(mListData.get(position).getLabel());
                        setmPoint(String.valueOf(mListData.get(position).getPoint()));
                        selectedPosition = position;
                        if (position == selectedPosition) {
                            item.lineChartItemView.setShowCursor(true);
                            notifyDataSetChanged();
                        } else {
                            item.lineChartItemView.setShowCursor(false);
                            notifyDataSetChanged();
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mListData.size();
        }

        class VHItem extends RecyclerView.ViewHolder {
            public LineChartItem lineChartItemView;

            public VHItem(View itemView) {
                super(itemView);
                lineChartItemView = (LineChartItem) itemView.findViewById(R.id.lineChart);
                lineChartItemView.setmColor(color);
            }
        }
    }
}
