package com.example.stepview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class StepView extends View {
    private int outerColor = Color.YELLOW;
    private int innerColor = Color.BLUE;
    private int borderWidth = 20;
    private int stepTextColor = Color.BLACK;
    private int stepTextSize = 30;
    private Paint innerPaint;
    private Paint outerPaint;
    private Paint textPaint;

    private int curStep = 5000;
    private int maxStep = 10000;

    public void setCurStep(int curStep) {
        this.curStep = curStep;
        invalidate();
    }

    public void setMaxStep(int maxStep) {
        this.maxStep = maxStep;
    }

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StepView);
        outerColor = typedArray.getColor(R.styleable.StepView_outer_color, outerColor);
        innerColor = typedArray.getColor(R.styleable.StepView_inner_color, innerColor);
        stepTextColor = typedArray.getColor(R.styleable.StepView_step_text_color, stepTextColor);
        borderWidth = typedArray.getDimensionPixelSize(R.styleable.StepView_border_width, borderWidth);
        stepTextSize = typedArray.getDimensionPixelSize(R.styleable.StepView_step_text_size, stepTextSize);
        typedArray.recycle();

        initInnerPaint();
        initOuterPaint();
        initTextPaint();
    }

    private void initInnerPaint() {
        innerPaint = new Paint();
        innerPaint.setAntiAlias(true);
        innerPaint.setColor(innerColor);
        innerPaint.setStrokeWidth(borderWidth);
        innerPaint.setStyle(Paint.Style.STROKE);
        innerPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void initOuterPaint() {
        outerPaint = new Paint();
        outerPaint.setAntiAlias(true);
        outerPaint.setColor(outerColor);
        outerPaint.setStrokeWidth(borderWidth);
        outerPaint.setStyle(Paint.Style.STROKE);
        outerPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void initTextPaint() {
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(stepTextColor);
        textPaint.setTextSize(stepTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //设置正方形
        setMeasuredDimension(width > height ? height : width, width > height ? height : width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOuterArc(canvas, outerPaint);//先画外面的，否侧外面的会盖住里面的
        drawInnerArc(canvas, innerPaint);
        drawText(canvas, textPaint);
    }

    private void drawInnerArc(Canvas canvas, Paint innerPaint) {
        int center = getWidth() / 2;
        int radius = (getWidth() - borderWidth) / 2;
        RectF rect = new RectF(borderWidth / 2, borderWidth / 2, center + radius, center + radius);
        if (maxStep == 0) {
            return;
        }
        float percent = (float) curStep / maxStep;
        canvas.drawArc(rect, 135, 270 * percent, false, innerPaint);
    }

    private void drawOuterArc(Canvas canvas, Paint outerPaint) {
        int center = getWidth() / 2;
        int radius = (getWidth() - borderWidth) / 2;
        RectF rect = new RectF(borderWidth / 2, borderWidth / 2, center + radius, center + radius);
        canvas.drawArc(rect, 135, 270, false, outerPaint);
    }


    private void drawText(Canvas canvas, Paint textPaint) {
        String text = curStep + "";
        Rect rect = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), rect);
        int textWidth = rect.width();
        int dx = getWidth() / 2 - textWidth / 2;
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
        int baseline = getHeight() / 2 + dy;
        canvas.drawText(text, dx, baseline, textPaint);

    }
}
