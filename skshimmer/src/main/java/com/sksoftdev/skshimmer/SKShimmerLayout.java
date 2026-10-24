package com.sksoftdev.skshimmer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

/** A lightweight shimmer animation container for skeleton placeholder views. */
public class SKShimmerLayout extends ViewGroup {
    private int baseColor = 0xFFE5E7EB;
    private int highlightColor = 0xFFF8FAFC;
    private int duration = 1200;
    private boolean autoStart = true;
    private boolean running;
    private float shimmerX = -1f;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private LinearGradient gradient;
    private ValueAnimator animator;

    public SKShimmerLayout(Context c) { this(c, null); }
    public SKShimmerLayout(Context c, AttributeSet a) { this(c, a, 0); }
    public SKShimmerLayout(Context c, AttributeSet a, int defStyle) {
        super(c, a, defStyle);
        setWillNotDraw(false);
        if (a != null) {
            TypedArray t = c.obtainStyledAttributes(a, R.styleable.SKShimmerLayout, defStyle, 0);
            baseColor = t.getColor(R.styleable.SKShimmerLayout_skShimmerBaseColor, baseColor);
            highlightColor = t.getColor(R.styleable.SKShimmerLayout_skShimmerHighlightColor, highlightColor);
            duration = Math.max(300, t.getInt(R.styleable.SKShimmerLayout_skShimmerDuration, duration));
            autoStart = t.getBoolean(R.styleable.SKShimmerLayout_skShimmerAutoStart, true);
            t.recycle();
        }
    }

    @Override protected void onMeasure(int ws, int hs) {
        int maxW = 0, maxH = 0;
        int cw = MeasureSpec.makeMeasureSpec(Math.max(0, MeasureSpec.getSize(ws) - getPaddingLeft() - getPaddingRight()), MeasureSpec.AT_MOST);
        int ch = MeasureSpec.makeMeasureSpec(Math.max(0, MeasureSpec.getSize(hs) - getPaddingTop() - getPaddingBottom()), MeasureSpec.AT_MOST);
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v.getVisibility() != GONE) {
                measureChild(v, cw, ch);
                maxW = Math.max(maxW, v.getMeasuredWidth());
                maxH = Math.max(maxH, v.getMeasuredHeight());
            }
        }
        setMeasuredDimension(resolveSize(maxW + getPaddingLeft() + getPaddingRight(), ws),
                resolveSize(maxH + getPaddingTop() + getPaddingBottom(), hs));
    }

    @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int x = getPaddingLeft(), y = getPaddingTop();
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v.getVisibility() != GONE) v.layout(x, y, x + v.getMeasuredWidth(), y + v.getMeasuredHeight());
        }
        updateGradient();
    }

    @Override protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (running && gradient != null) {
            paint.setShader(gradient);
            canvas.drawRect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(),
                    getHeight() - getPaddingBottom(), paint);
            paint.setShader(null);
        }
    }

    private void updateGradient() {
        float w = Math.max(1, getWidth());
        float x = shimmerX < 0 ? -w : shimmerX;
        gradient = new LinearGradient(x - w * .35f, 0, x + w * .35f, 0,
                new int[]{baseColor, highlightColor, baseColor}, new float[]{0f, .5f, 1f}, Shader.TileMode.CLAMP);
        invalidate();
    }

    public void startShimmer() {
        if (animator != null && animator.isRunning()) return;
        running = true;
        animator = ValueAnimator.ofFloat(-Math.max(1, getWidth()), Math.max(1, getWidth()) * 2f);
        animator.setDuration(duration);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(a -> { shimmerX = (float) a.getAnimatedValue(); updateGradient(); });
        animator.start();
    }

    public void stopShimmer() {
        running = false;
        if (animator != null) { animator.cancel(); animator = null; }
        invalidate();
    }

    public void startShimmerOnce() {
        stopShimmer();
        running = true;
        animator = ValueAnimator.ofFloat(-Math.max(1, getWidth()), Math.max(1, getWidth()) * 2f);
        animator.setDuration(duration);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(a -> { shimmerX = (float) a.getAnimatedValue(); updateGradient(); });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator a) { running = false; invalidate(); }
        });
        animator.start();
    }

    public void setShimmerDuration(int millis) { duration = Math.max(300, millis); if (running) { stopShimmer(); startShimmer(); } }
    public int getShimmerDuration() { return duration; }
    public void setShimmerColors(int base, int highlight) { baseColor = base; highlightColor = highlight; updateGradient(); }

    @Override protected void onAttachedToWindow() { super.onAttachedToWindow(); if (autoStart) startShimmer(); }
    @Override protected void onDetachedFromWindow() { stopShimmer(); super.onDetachedFromWindow(); }
}
