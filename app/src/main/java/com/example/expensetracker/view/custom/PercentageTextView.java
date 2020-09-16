package com.example.expensetracker.view.custom;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.example.expensetracker.R;
import com.example.expensetracker.model.Category;
import com.example.expensetracker.util.Util;
import com.example.expensetracker.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

public class PercentageTextView extends AppCompatTextView {

    private static final float CIRCLE_STROKE_WIDTH_DP = 10;

    private RectF mRectF;
    private Paint mPaint;
    private int backGroundCircleColor;
    private int firstCircleColor;
    private int secondCircleColor;
    private int thirdCircleColor;
    private int fourthCircleColor;
    private int fifthCircleColor;
    private int sixthCircleColor;
    private float mCircleStrokeWidthInPx;

    private int currentColor = 1;
    private float startAngle;

    private static String TAG = "PERCENG TEXT VIEW TAG DEBUG MADAFAKA";

    private Category[] categoryArray;
    private float sum;

    private List<Category> categories = new ArrayList<>();

    public PercentageTextView(Context context) {
        super(context);
        init(null);
    }

    public PercentageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PercentageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int length = Math.min(width, height);
        int newMeasureSpec = MeasureSpec.makeMeasureSpec(length, MeasureSpec.EXACTLY);

        super.onMeasure(newMeasureSpec, newMeasureSpec);
    }

    private void init(AttributeSet attrs) {
        parseAttributes(attrs);
        mRectF = new RectF();
        mPaint = new Paint();
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float left = 0 + mCircleStrokeWidthInPx;
        float top = 0 + mCircleStrokeWidthInPx;
        float bottom = getHeight() - mCircleStrokeWidthInPx;
        float right = getWidth() - mCircleStrokeWidthInPx;

        mRectF.set(left, top, right, bottom);

        mPaint.setAntiAlias(true);
        mPaint.setColor(backGroundCircleColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mCircleStrokeWidthInPx);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        canvas.drawOval(mRectF, mPaint);

        findCategories();
        findAngles(canvas);
    }

    private void findCategories(){
        categoryArray = new Category[6];
        int i=0;
        sum = 0;
        for(Category cat: categories){
            if(cat.getPrice()>0) {
                categoryArray[i++] = cat;
                sum+=(float)cat.getPrice();
            }
        }
    }

    private void findAngles(Canvas canvas){
        startAngle = 270f;
        if(categoryArray.length==0) return;
        for(int i=0;i<categoryArray.length;i++) {
            updateCanvas(canvas, getSweepAngle(categoryArray[i]));
        }
    }

    private void updateCanvas(Canvas canvas, float sweepAngle){
        checkColor(mPaint);
        canvas.drawArc(mRectF, startAngle, sweepAngle, false, mPaint);
        startAngle+=sweepAngle;
    }


    private float getSweepAngle(Category category) {
        try {
            float percent = ((float)category.getPrice()/sum) * 100;
            float angle = (percent / 100f) * 360;
            return angle;
        } catch (Exception e) {
            return 0f;
        }
    }

    private void checkColor(Paint mPaint){
        switch (currentColor){
            case 1:
                mPaint.setColor(secondCircleColor); break;
            case 2:
                mPaint.setColor(thirdCircleColor); break;
            case 3:
                mPaint.setColor(fourthCircleColor); break;
            case 4:
                mPaint.setColor(fifthCircleColor); break;
            case 5:
                mPaint.setColor(sixthCircleColor); break;
            case 6:
                mPaint.setColor(firstCircleColor); break;
        }
        currentColor = (currentColor+1)%7;
    }

    private void parseAttributes(AttributeSet attrs) {

        if (attrs == null) {
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PercentageTextView);
        backGroundCircleColor = typedArray.getColor(R.styleable.PercentageTextView_backgroundCircleColor, 0);
        firstCircleColor = typedArray.getColor(R.styleable.PercentageTextView_firstCircleColor, 0);
        secondCircleColor = typedArray.getColor(R.styleable.PercentageTextView_secondCircleColor, 0);
        thirdCircleColor = typedArray.getColor(R.styleable.PercentageTextView_thirdCircleColor, 0);
        fourthCircleColor = typedArray.getColor(R.styleable.PercentageTextView_fourthCircleColor, 0);
        fifthCircleColor = typedArray.getColor(R.styleable.PercentageTextView_fifthCircleColor, 0);
        sixthCircleColor = typedArray.getColor(R.styleable.PercentageTextView_sixthCircleColor, 0);
        int defaultValue = (int) Util.DpToPx(getContext(), CIRCLE_STROKE_WIDTH_DP);
        mCircleStrokeWidthInPx = typedArray.getDimensionPixelSize(R.styleable.PercentageTextView_circleStrokeWidth, defaultValue);
        typedArray.recycle();
    }

    public void addCategoryList(List<Category> categories){
        this.categories = categories;
    }
}