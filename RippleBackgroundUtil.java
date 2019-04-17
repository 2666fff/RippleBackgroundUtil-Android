package com.tsingri.glucosemonitor.ui;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.DisplayMetrics;

import java.util.Arrays;


public class RippleBackgroundUtil {
    private static final float DEFAULT_RIPPLE_COLOR_LEVEL = 0.8f;

    public static Drawable getButtonRippleBackground(@ColorInt int normalColor) {

        float radius = dp2px(2);
        return getRippleBackgroundDrawable(normalColor, getRippleColor(normalColor), radius);
    }

    public static Drawable getTransparentRippleBackground() {
        float radius = dp2px(2);
        int normalColor = Color.TRANSPARENT;
        int pressedColor = Color.parseColor("#30ffffff");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new RippleDrawable(ColorStateList.valueOf(getRippleColor(normalColor)),
                    getStateListDrawable(normalColor, pressedColor, -1, radius), getRippleMask(pressedColor, radius));
        } else {
            return getStateListDrawable(normalColor, pressedColor, -1, radius);
        }
    }

    public static Drawable getCircleTransparentRippleBackground(float radius) {
        int normalColor = Color.TRANSPARENT;
        int pressedColor = Color.parseColor("#30ffffff");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new RippleDrawable(ColorStateList.valueOf(getRippleColor(normalColor)),
                    getStateListDrawable(normalColor, pressedColor, -1, radius), getRippleMask(pressedColor, radius));
        } else {
            return getStateListDrawable(normalColor, pressedColor, -1, radius);
        }
    }

    public static Drawable getRippleBackgroundDrawable(
            int normalColor, int pressedColor, float radius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new RippleDrawable(ColorStateList.valueOf(getRippleColor(normalColor)),
                    getStateListDrawable(normalColor, pressedColor, -1, radius), getRippleMask(normalColor, radius));
        } else {
            return getStateListDrawable(normalColor, pressedColor, -1, radius);
        }
    }

    public static Drawable getRippleBackgroundDrawable(@ColorInt
                                                               int normalColor, float radius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new RippleDrawable(ColorStateList.valueOf(getRippleColor(normalColor)),
                    getStateListDrawable(normalColor, -1, -1, radius), getRippleMask(normalColor, radius));
        } else {
            return getStateListDrawable(normalColor, getRippleColor(normalColor), -1, radius);
        }
    }

    public static Drawable getRippleBackgroundDrawableWithDisableStatus(int normalColor, int disableColor, float radius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new RippleDrawable(ColorStateList.valueOf(getRippleColor(normalColor))
                    , getStateListDrawable(normalColor, -1, disableColor, radius)
                    , getRippleMask(normalColor, radius));
        } else {
            return getStateListDrawable(normalColor, getRippleColor(normalColor), disableColor, radius);
        }
    }

    private static int getRippleColor(int normalColor) {
        int r = (int) (((normalColor >> 16) & 0xFF) * 0.85);
        int g = (int) (((normalColor >> 8) & 0xFF) * 0.85);
        int b = (int) (((normalColor) & 0xFF) * 0.85);
        return Color.rgb(r, g, b);
    }

    private static int getRippleColor(int normalColor, float level) {
        int r = (int) (((normalColor >> 16) & 0xFF) * level);
        int g = (int) (((normalColor >> 8) & 0xFF) * level);
        int b = (int) (((normalColor) & 0xFF) * level);
        return Color.rgb(r, g, b);
    }


    private static Drawable getRippleMask(int color, float radius) {
        float[] outerRadii = new float[8];
        Arrays.fill(outerRadii, radius);

        RoundRectShape r = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(r);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

    private static StateListDrawable getStateListDrawable(
            int normalColor, int pressedColor, int disableColor, float radius) {
        StateListDrawable states = new StateListDrawable();
        if (disableColor != -1) {
            states.addState(new int[]{-android.R.attr.state_enabled},
                    getShapeDrawable(disableColor, radius));
        }
        if (pressedColor != -1) {
            states.addState(new int[]{android.R.attr.state_pressed},
                    getShapeDrawable(pressedColor, radius));
            states.addState(new int[]{android.R.attr.state_focused},
                    getShapeDrawable(pressedColor, radius));
            states.addState(new int[]{android.R.attr.state_activated},
                    getShapeDrawable(pressedColor, radius));
        }
        states.addState(new int[]{},
                getShapeDrawable(normalColor, radius));
        return states;
    }

    private static GradientDrawable getShapeDrawable(int color, float radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(radius);
        shape.setColor(color);
        return shape;
    }


    //从此处开始都是为 4个corner角度不相同的 ripple做的
    public static Drawable getRippleBackgroundDrawable(
            int normalColor, float radiusTopLeft, float radiusTopRight, float radiusBottomLeft, float radiusBottomRight) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new RippleDrawable(ColorStateList.valueOf(getRippleColor(normalColor)),
                    getStateListDrawable(normalColor, -1, -1, radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight), getRippleMask(normalColor, radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight));
        } else {
            return getStateListDrawable(normalColor, getRippleColor(normalColor), -1, radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight);
        }
    }

    private static StateListDrawable getStateListDrawable(
            int normalColor, int pressedColor, int disableColor,
            float radiusTopLeft, float radiusTopRight, float radiusBottomLeft, float radiusBottomRight) {
        StateListDrawable states = new StateListDrawable();
        if (disableColor != -1) {
            states.addState(new int[]{-android.R.attr.state_enabled},
                    getShapeDrawable(disableColor, radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight));
        }
        if (pressedColor != -1) {
            states.addState(new int[]{android.R.attr.state_pressed},
                    getShapeDrawable(pressedColor, radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight));
            states.addState(new int[]{android.R.attr.state_focused},
                    getShapeDrawable(pressedColor, radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight));
            states.addState(new int[]{android.R.attr.state_activated},
                    getShapeDrawable(pressedColor, radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight));
        }
        states.addState(new int[]{},
                getShapeDrawable(normalColor, radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight));
        return states;
    }

    private static GradientDrawable getShapeDrawable(int color,
                                                     float radiusTopLeft,
                                                     float radiusTopRight,
                                                     float radiusBottomLeft,
                                                     float radiusBottomRight) {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadii(new float[]{radiusTopLeft, radiusTopLeft,
                radiusTopRight, radiusTopRight, radiusBottomRight,
                radiusBottomRight, radiusBottomLeft, radiusBottomLeft});
        shape.setColor(color);
        return shape;
    }


    private static Drawable getRippleMask(int color, float radiusTopLeft, float radiusTopRight, float radiusBottomLeft, float radiusBottomRight) {
        float[] outerRadii = new float[]{radiusTopLeft, radiusTopLeft, radiusTopRight, radiusTopRight, radiusBottomRight, radiusBottomRight, radiusBottomLeft, radiusBottomLeft};
        RoundRectShape r = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(r);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

    public static Drawable getCircleRippleBackgroundDrawable(
            int normalColor, float radius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new RippleDrawable(ColorStateList.valueOf(getRippleColor(normalColor, DEFAULT_RIPPLE_COLOR_LEVEL)),
                    getCircleStateListDrawable(normalColor, -1, -1, radius), getCircleRippleMask(normalColor, radius));
        } else {
            return getCircleStateListDrawable(normalColor, getRippleColor(normalColor, DEFAULT_RIPPLE_COLOR_LEVEL), -1, radius);
        }
    }

    private static Drawable getCircleRippleMask(int color, float radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(color);
        return shape;
    }

    private static StateListDrawable getCircleStateListDrawable(
            int normalColor, int pressedColor, int disableColor, float radius) {
        StateListDrawable states = new StateListDrawable();
        if (disableColor != -1) {
            states.addState(new int[]{-android.R.attr.state_enabled},
                    getCircleShapeDrawable(disableColor, radius));
        }
        if (pressedColor != -1) {
            states.addState(new int[]{android.R.attr.state_pressed},
                    getCircleShapeDrawable(pressedColor, radius));
            states.addState(new int[]{android.R.attr.state_focused},
                    getCircleShapeDrawable(pressedColor, radius));
            states.addState(new int[]{android.R.attr.state_activated},
                    getCircleShapeDrawable(pressedColor, radius));
        }
        states.addState(new int[]{},
                getCircleShapeDrawable(normalColor, radius));
        return states;
    }

    private static GradientDrawable getCircleShapeDrawable(int color, float radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(color);
        return shape;
    }

    private static float dp2px(float dp) {
        return dp * ((float) Resources.getSystem().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private static float px2dp(float px) {
        return px / ((float) Resources.getSystem().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
