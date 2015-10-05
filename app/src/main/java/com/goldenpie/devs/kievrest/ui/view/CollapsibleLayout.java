package com.goldenpie.devs.kievrest.ui.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.goldenpie.devs.kievrest.ui.listener.CollapseAnimationListener;

import lombok.Getter;
import lombok.Setter;

public class CollapsibleLayout extends FrameLayout {

    @Getter
    private boolean collapsed;
    private ImageView rotateView;
    private RotateAnimation expandRotateAnimation;
    private RotateAnimation collapseRotateAnimation;
    private boolean isAnimating = false;
    @Setter
    private CollapseAnimationListener listener;

    public CollapsibleLayout(Context context) {
        super(context);
    }

    public CollapsibleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CollapsibleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CollapsibleLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
        if (collapsed) {
            getLayoutParams().height = 0;
            setVisibility(GONE);
        } else {
            measureHeight();
            getLayoutParams().height = getMeasuredHeight();
        }
        requestLayout();
    }


    public void toggle() {
        if (isAnimating) {
            return;
        } else if (collapsed) {
            expand();
        } else {
            collapse();
        }
        if (rotateView != null) {
            if (collapsed) {
                rotateView.startAnimation(expandRotateAnimation);
            } else {
                rotateView.startAnimation(collapseRotateAnimation);
            }
        }
        collapsed = !collapsed;
    }

    private void collapse() {
        int finalHeight = getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        mAnimator.start();
    }

    public void hardCollapse() {
        int finalHeight = getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        mAnimator.start();

        if (!collapsed) {
            rotateView.startAnimation(collapseRotateAnimation);
            collapsed = !collapsed;
        }
    }

    private void expand() {
        measureHeight();

        ValueAnimator mAnimator = slideAnimator(0, getMeasuredHeight());
        mAnimator.start();
    }

    private void measureHeight() {
        setVisibility(View.VISIBLE);

        final int widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        final int heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        measure(widthSpec, heightSpec);
    }

    private ValueAnimator slideAnimator(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.height = value;
                setLayoutParams(layoutParams);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (listener != null) {
                    listener.onAnimationEnd();
                }
                isAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return animator;
    }

    public void setViewToRotate(ImageView arrow) {
        this.rotateView = arrow;
        expandRotateAnimation = new RotateAnimation(0.0f, 180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, .5f);

        collapseRotateAnimation = new RotateAnimation(180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, .5f);
        setAnimationParameters(expandRotateAnimation);
        setAnimationParameters(collapseRotateAnimation);
    }

    private void setAnimationParameters(RotateAnimation animation) {
        // Set the animation's parameters
        animation.setDuration(150); // duration in ms
        animation.setRepeatCount(0);// -1 = infinite repeated
        animation.setRepeatMode(Animation.REVERSE);
        animation.setFillAfter(true);
    }


}
