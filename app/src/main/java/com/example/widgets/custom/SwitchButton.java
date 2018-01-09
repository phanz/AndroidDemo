package com.example.widgets.custom;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.demo.R;


public class SwitchButton extends CompoundButton {
    private static final int TOUCH_MODE_IDLE = 0;
    private static final int TOUCH_MODE_DOWN = 1;
    private static final int TOUCH_MODE_DRAGGING = 2;

    // Enum for the "typeface" XML parameter.
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int MONOSPACE = 3;

    private Drawable mThumbDrawable;
    private Drawable mTrackDrawable;
    private int mThumbTextPadding;
    private int mSwitchMinWidth;
    private int mSwitchPadding;
    private CharSequence mTextOn;
    private CharSequence mTextOff;

    private int mTouchMode;
    private int mTouchSlop;
    private float mTouchX;
    private float mTouchY;
    private VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    private int mMinFlingVelocity;

    private float mThumbPosition;
    private int mSwitchWidth;
    private int mSwitchHeight;
    private int mThumbWidth; // Does not include padding

    private int mSwitchLeft;
    private int mSwitchTop;
    private int mSwitchRight;
    private int mSwitchBottom;

    private TextPaint mTextPaint;
    private ColorStateList mTextColors;
    private Layout mOnLayout;
    private Layout mOffLayout;

    @SuppressWarnings("hiding")
    private final Rect mTempRect = new Rect();

    private static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked };
    public static final boolean DEBUG = false;
    public static final String TAG = "switch";
    public static int Duration = 400;
    private final Rect mBitmapRect = new Rect();
    private Bitmap mBitmapOn;
    private Bitmap mBitmapOff;
    private float mBitmapScale = 0f;
    private Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG);

    /**
     * Construct a new Switch with default styling.
     * 
     * @param context
     *            The Context that will determine this widget's theming.
     */
    public SwitchButton(Context context) {
        this(context, null);
    }

    /**
     * Construct a new Switch with default styling, overriding specific style
     * attributes as requested.
     * 
     * @param context
     *            The Context that will determine this widget's theming.
     * @param attrs
     *            Specification of attributes that should deviate from default
     *            styling.
     */
    public SwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.switchStyle);
    }

    /**
     * Construct a new Switch with a default style determined by the given theme
     * attribute, overriding specific style attributes as requested.
     * 
     * @param context
     *            The Context that will determine this widget's theming.
     * @param attrs
     *            Specification of attributes that should deviate from the
     *            default styling.
     * @param defStyle
     *            An attribute ID within the active theme containing a reference
     *            to the default style for this widget. e.g.
     *            android.R.attr.switchStyle.
     */
    public SwitchButton(Context context, AttributeSet attrs, int defStyle) {
        //super(context, attrs, R.style.fxlib_phiswitch_style);
        super(context, attrs, defStyle);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        Resources res = getResources();
        mTextPaint.density = res.getDisplayMetrics().density;
        // mTextPaint.setCompatibilityScaling(res.getCompatibilityInfo().applicationScale);

        mThumbDrawable = res
                .getDrawable( R.drawable.switch_inner_holo_dark);
        mTrackDrawable = res
                .getDrawable(R.drawable.switch_track_holo_dark);
        mTextOn = "";
        mTextOff = "";
        mThumbTextPadding = res
                .getDimensionPixelSize(R.dimen.fxlib_switch_thumbTextPadding);
        mSwitchMinWidth = res
                .getDimensionPixelSize(R.dimen.fxlib_switch_thumbTextPadding);
        mSwitchPadding = res
                .getDimensionPixelSize(R.dimen.fxlib_switch_padding);

        /*mBitmapOn = BitmapFactory.decodeResource(res,R.drawable.fxlib_switch_bg_on);
        mBitmapOff = BitmapFactory.decodeResource(res,R.drawable.fxlib_switch_bg_holo_dark);*/

        mBitmapOn = BitmapFactory.decodeResource(res,R.drawable.switch_bg_on);
        mBitmapOff = BitmapFactory.decodeResource(res,R.drawable.switch_bg_off);

        ViewConfiguration config = ViewConfiguration.get(context);
        mTouchSlop = config.getScaledTouchSlop();
        mMinFlingVelocity = config.getScaledMinimumFlingVelocity();

        // Refresh display with current params
        refreshDrawableState();
        setChecked(isChecked());
    }

    public void setSwitchTextAppearance(Context context, int resid) {
        TypedArray appearance = context.obtainStyledAttributes(resid,
                R.styleable.TextAppearance);

        ColorStateList colors;
        int ts;

        colors = appearance.getColorStateList(R.styleable.TextAppearance_textColor);
        if (colors != null) {
            mTextColors = colors;
        } else {
            // If no color set in TextAppearance, default to the view's
            // textColor
            mTextColors = getTextColors();
        }

        ts = appearance.getDimensionPixelSize(R.styleable.TextAppearance_textSize, 0);
        if (ts != 0) {
            if (ts != mTextPaint.getTextSize()) {
                mTextPaint.setTextSize(ts);
                requestLayout();
            }
        }

        int typefaceIndex, styleIndex;

        typefaceIndex = appearance.getInt(R.styleable.TextAppearance_typeface, -1);
        styleIndex = appearance.getInt(R.styleable.TextAppearance_textStyle, -1);

        setSwitchTypefaceByIndex(typefaceIndex, styleIndex);
        appearance.recycle();
    }

    private void setSwitchTypefaceByIndex(int typefaceIndex, int styleIndex) {
        Typeface tf = null;
        switch (typefaceIndex) {
        case SANS:
            tf = Typeface.SANS_SERIF;
            break;
        case SERIF:
            tf = Typeface.SERIF;
            break;
        case MONOSPACE:
            tf = Typeface.MONOSPACE;
            break;
        }
        setSwitchTypeface(tf, styleIndex);
    }

    /**
     * Sets the typeface and style in which the text should be displayed on the
     * switch, and turns on the fake bold and italic bits in the Paint if the
     * Typeface that you provided does not have all the bits in the style that
     * you specified.
     */
    public void setSwitchTypeface(Typeface tf, int style) {
        if (style > 0) {
            if (tf == null) {
                tf = Typeface.defaultFromStyle(style);
            } else {
                tf = Typeface.create(tf, style);
            }

            setSwitchTypeface(tf);
            // now compute what (if any) algorithmic styling is needed
            int typefaceStyle = tf != null ? tf.getStyle() : 0;
            int need = style & ~typefaceStyle;
            mTextPaint.setFakeBoldText((need & Typeface.BOLD) != 0);
            mTextPaint.setTextSkewX((need & Typeface.ITALIC) != 0 ? -0.25f : 0);
        } else {
            mTextPaint.setFakeBoldText(false);
            mTextPaint.setTextSkewX(0);
            setSwitchTypeface(tf);
        }
    }

    /**
     * Sets the typeface in which the text should be displayed on the switch.
     * Note that not all Typeface families actually have bold and italic
     * variants, so you may need to use
     * {@link #setSwitchTypeface(Typeface, int)} to get the appearance that you
     * actually want.
     * 
     * @attr ref android.R.styleable#TextView_typeface
     * @attr ref android.R.styleable#TextView_textStyle
     */
    public void setSwitchTypeface(Typeface tf) {
        if (mTextPaint.getTypeface() != tf) {
            mTextPaint.setTypeface(tf);

            requestLayout();
            invalidate();
        }
    }

    /**
     * Set the amount of horizontal padding between the switch and the
     * associated text.
     * 
     * @param pixels
     *            Amount of padding in pixels
     * 
     * @attr ref android.R.styleable#Switch_switchPadding
     */
    public void setSwitchPadding(int pixels) {
        mSwitchPadding = pixels;
        requestLayout();
    }

    /**
     * Get the amount of horizontal padding between the switch and the
     * associated text.
     * 
     * @return Amount of padding in pixels
     * 
     * @attr ref android.R.styleable#Switch_switchPadding
     */
    public int getSwitchPadding() {
        return mSwitchPadding;
    }

    /**
     * Set the minimum width of the switch in pixels. The switch's width will be
     * the maximum of this value and its measured width as determined by the
     * switch drawables and text used.
     * 
     * @param pixels
     *            Minimum width of the switch in pixels
     * 
     * @attr ref android.R.styleable#Switch_switchMinWidth
     */
    public void setSwitchMinWidth(int pixels) {
        mSwitchMinWidth = pixels;
        requestLayout();
    }

    /**
     * Get the minimum width of the switch in pixels. The switch's width will be
     * the maximum of this value and its measured width as determined by the
     * switch drawables and text used.
     * 
     * @return Minimum width of the switch in pixels
     * 
     * @attr ref android.R.styleable#Switch_switchMinWidth
     */
    public int getSwitchMinWidth() {
        return mSwitchMinWidth;
    }

    /**
     * Set the horizontal padding around the text drawn on the switch itself.
     * 
     * @param pixels
     *            Horizontal padding for switch thumb text in pixels
     * 
     * @attr ref android.R.styleable#Switch_thumbTextPadding
     */
    public void setThumbTextPadding(int pixels) {
        mThumbTextPadding = pixels;
        requestLayout();
    }

    /**
     * Get the horizontal padding around the text drawn on the switch itself.
     * 
     * @return Horizontal padding for switch thumb text in pixels
     * 
     * @attr ref android.R.styleable#Switch_thumbTextPadding
     */
    public int getThumbTextPadding() {
        return mThumbTextPadding;
    }

    /**
     * Set the drawable used for the track that the switch slides within.
     * 
     * @param track
     *            Track drawable
     * 
     * @attr ref android.R.styleable#Switch_track
     */
    public void setTrackDrawable(Drawable track) {
        mTrackDrawable = track;
        requestLayout();
    }

    /**
     * Set the drawable used for the track that the switch slides within.
     * 
     * @param resId
     *            Resource ID of a track drawable
     * 
     * @attr ref android.R.styleable#Switch_track
     */
    public void setTrackResource(int resId) {
        setTrackDrawable(getContext().getResources().getDrawable(resId));
    }

    /**
     * Get the drawable used for the track that the switch slides within.
     * 
     * @return Track drawable
     * 
     * @attr ref android.R.styleable#Switch_track
     */
    public Drawable getTrackDrawable() {
        return mTrackDrawable;
    }

    /**
     * Set the drawable used for the switch "thumb" - the piece that the user
     * can physically touch and drag along the track.
     * 
     * @param thumb
     *            Thumb drawable
     * 
     * @attr ref android.R.styleable#Switch_thumb
     */
    public void setThumbDrawable(Drawable thumb) {
        mThumbDrawable = thumb;
        requestLayout();
    }

    /**
     * Set the drawable used for the switch "thumb" - the piece that the user
     * can physically touch and drag along the track.
     * 
     * @param resId
     *            Resource ID of a thumb drawable
     * 
     * @attr ref android.R.styleable#Switch_thumb
     */
    public void setThumbResource(int resId) {
        setThumbDrawable(getContext().getResources().getDrawable(resId));
    }

    /**
     * Get the drawable used for the switch "thumb" - the piece that the user
     * can physically touch and drag along the track.
     * 
     * @return Thumb drawable
     * 
     * @attr ref android.R.styleable#Switch_thumb
     */
    public Drawable getThumbDrawable() {
        return mThumbDrawable;
    }

    /**
     * Returns the text displayed when the button is in the checked state.
     * 
     * @attr ref android.R.styleable#Switch_textOn
     */
    public CharSequence getTextOn() {
        return mTextOn;
    }

    /**
     * Sets the text displayed when the button is in the checked state.
     * 
     * @attr ref android.R.styleable#Switch_textOn
     */
    public void setTextOn(CharSequence textOn) {
        mTextOn = textOn;
        requestLayout();
    }

    /**
     * Returns the text displayed when the button is not in the checked state.
     * 
     * @attr ref android.R.styleable#Switch_textOff
     */
    public CharSequence getTextOff() {
        return mTextOff;
    }

    /**
     * Sets the text displayed when the button is not in the checked state.
     * 
     * @attr ref android.R.styleable#Switch_textOff
     */
    public void setTextOff(CharSequence textOff) {
        mTextOff = textOff;
        requestLayout();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mTrackDrawable.getPadding(mTempRect);
        mThumbWidth = mThumbDrawable.getIntrinsicWidth();
        mSwitchWidth = mTrackDrawable.getIntrinsicWidth();
        mSwitchHeight = mTrackDrawable.getIntrinsicHeight();

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int measuredHeight = getMeasuredHeight();
        if (measuredHeight < mSwitchHeight) {
            setMeasuredDimension(getMeasuredWidthAndState(), mSwitchHeight);
        }
    }

    @Override
    public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
        super.onPopulateAccessibilityEvent(event);
        Layout layout = isChecked() ? mOnLayout : mOffLayout;
        if (layout != null && !TextUtils.isEmpty(layout.getText())) {
            event.getText().add(layout.getText());
        }
    }

    /**
     * @return true if (x, y) is within the target area of the switch thumb
     */
    private boolean hitThumb(float x, float y) {
        mThumbDrawable.getPadding(mTempRect);
        final int thumbTop = mSwitchTop - mTouchSlop;
        final int thumbLeft = mSwitchLeft + (int) (mThumbPosition + 0.5f) - mTouchSlop;
        final int thumbRight = thumbLeft + mThumbWidth + mTempRect.left + mTempRect.right
                + mTouchSlop;
        final int thumbBottom = mSwitchBottom + mTouchSlop;
        return x > thumbLeft && x < thumbRight && y > thumbTop && y < thumbBottom;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mVelocityTracker.addMovement(ev);
        final int action = ev.getActionMasked();
        switch (action) {
        case MotionEvent.ACTION_DOWN: {
            final float x = ev.getX();
            final float y = ev.getY();
            if (isEnabled() && hitThumb(x, y)) {
                mTouchMode = TOUCH_MODE_DOWN;
                mTouchX = x;
                mTouchY = y;
            }
            break;
        }

        case MotionEvent.ACTION_MOVE: {
            switch (mTouchMode) {
            case TOUCH_MODE_IDLE:
                // Didn't target the thumb, treat normally.
                break;

            case TOUCH_MODE_DOWN: {
                final float x = ev.getX();
                final float y = ev.getY();
                if (Math.abs(x - mTouchX) > mTouchSlop
                        || Math.abs(y - mTouchY) > mTouchSlop) {
                    mTouchMode = TOUCH_MODE_DRAGGING;
                    getParent().requestDisallowInterceptTouchEvent(true);
                    mTouchX = x;
                    mTouchY = y;
                    return true;
                }
                break;
            }

            case TOUCH_MODE_DRAGGING: {
                final float x = ev.getX();
                final float dx = x - mTouchX;
                float newPos = Math.max(0,
                        Math.min(mThumbPosition + dx, getThumbScrollRange()));
                if (newPos != mThumbPosition) {
                    mThumbPosition = newPos;
                    mTouchX = x;
                    invalidate();
                }
                return true;
            }
            }
            break;
        }

        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL: {
            if (mTouchMode == TOUCH_MODE_DRAGGING) {
                stopDrag(ev);
                return true;
            }
            mTouchMode = TOUCH_MODE_IDLE;
            mVelocityTracker.clear();
            break;
        }
        }
        return super.onTouchEvent(ev);
    }

    private void cancelSuperTouch(MotionEvent ev) {
        MotionEvent cancel = MotionEvent.obtain(ev);
        cancel.setAction(MotionEvent.ACTION_CANCEL);
        super.onTouchEvent(cancel);
        cancel.recycle();
    }

    /**
     * Called from onTouchEvent to end a drag operation.
     * 
     * @param ev
     *            Event that triggered the end of drag mode - ACTION_UP or
     *            ACTION_CANCEL
     */
    private void stopDrag(MotionEvent ev) {
        mTouchMode = TOUCH_MODE_IDLE;
        // Up and not canceled, also checks the switch has not been disabled
        // during the drag
        boolean commitChange = ev.getAction() == MotionEvent.ACTION_UP && isEnabled();

        cancelSuperTouch(ev);

        if (commitChange) {
            boolean newState;
            mVelocityTracker.computeCurrentVelocity(1000);
            float xvel = mVelocityTracker.getXVelocity();
            if (Math.abs(xvel) > mMinFlingVelocity) {
                newState = (xvel > 0);
            } else {
                newState = getTargetCheckedState();
            }
            animateThumbToCheckedState(newState);
        } else {
            animateThumbToCheckedState(isChecked());
        }
    }

    public void setBitmapOn(Drawable drawable) {
        mBitmapOn = ((BitmapDrawable)drawable).getBitmap();
    }
    /**
     * sunday add by animation for widget 20150413--start
     * **/

    /** set the bitmap to animation on **/
    public void setBitmapOn(int resId) {
        mBitmapOn = BitmapFactory.decodeResource(getResources(), resId);
    }

    public Bitmap getBitmapOn() {
        return mBitmapOn;
    }

    /** set the bitmap to animation off **/
    public void setBitmapOff(int resId) {
        mBitmapOff = BitmapFactory.decodeResource(getResources(), resId);
    }

    public Bitmap getBitmapOff() {
        return mBitmapOff;
    }

    /** set the animation time **/
    public void setDuration(int ms) {
        Duration = ms;
    }

    private void drawAnimationBitmapWitdhDrag(Canvas canvas) {
        canvas.save();
        Bitmap bitmap = null;
        float startPos = 0;
        if (isChecked()) {
            bitmap = mBitmapOff;
            startPos = getThumbScrollRange();
        } else {
            bitmap = mBitmapOn;
            startPos = 0;
        }
        float dis = Math.abs(startPos - mThumbPosition);
        float scale = dis / getThumbScrollRange();
        // float scale = isChecked() ? (1-mBitmapScale):mBitmapScale;
        float dx = (float) (canvas.getWidth() * (1 - scale) / 2);
        float dy = (float) (canvas.getHeight() * (1 - scale) / 2);
        canvas.translate(dx, dy);
        canvas.scale(scale, scale);
        canvas.drawBitmap(bitmap, null, mBitmapRect, paint);
        canvas.restore();
    }

    private void drawAnimationBitmap(Canvas canvas) {
        canvas.save();
        Bitmap bitmap = isChecked() ? mBitmapOff : mBitmapOn;
        float scale = isChecked() ? (1 - mBitmapScale) : mBitmapScale;
        float dx = (float) (canvas.getWidth() * (1 - scale) / 2);
        float dy = (float) (canvas.getHeight() * (1 - scale) / 2);
        canvas.translate(dx, dy);
        canvas.scale(scale, scale);
        canvas.drawBitmap(bitmap, null, mBitmapRect, paint);
        canvas.restore();
    }

    private ValueAnimator mValueAnimator;

    private void animateThumbToCheckedState(final boolean newCheckedState) {
        // TODO animate!
        float targetPos = newCheckedState ? getThumbScrollRange() : 0;
        mValueAnimator = ValueAnimator.ofFloat(mThumbPosition, targetPos);
        mValueAnimator.setDuration(Duration);
        mValueAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // TODO Auto-generated method stub
                Float o = (Float) animation.getAnimatedValue();
                mThumbPosition = o;
                mBitmapScale = mThumbPosition / getThumbScrollRange();
                postInvalidate();
            }
        });
        mValueAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                super.onAnimationEnd(animation);
                setChecked(newCheckedState);
            }
        });
        mValueAnimator.start();

    }

    @Override
    public void toggle() {
        // TODO Auto-generated method stub
        // super.toggle();
        if (mBitmapOn == null || mBitmapOff == null) {
            super.toggle();
        } else {
            animateThumbToCheckedState(!isChecked());
        }
    }

    /**
     * sunday add by animation for widget 20150413--end
     * **/
    private boolean getTargetCheckedState() {
        return mThumbPosition >= getThumbScrollRange() / 2;
    }

    private void setThumbPosition(boolean checked) {
        mThumbPosition = checked ? getThumbScrollRange() : 0;
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        setThumbPosition(isChecked());

        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        setThumbPosition(isChecked());

        int switchRight;
        int switchLeft;

        switchRight = getWidth() - getPaddingRight();
        switchLeft = switchRight - mSwitchWidth;

        int switchTop = 0;
        int switchBottom = 0;
        switch (getGravity() & Gravity.VERTICAL_GRAVITY_MASK) {
        default:
        case Gravity.TOP:
            switchTop = getPaddingTop();
            switchBottom = switchTop + mSwitchHeight;
            break;

        case Gravity.CENTER_VERTICAL:
            switchTop = (getPaddingTop() + getHeight() - getPaddingBottom()) / 2
                    - mSwitchHeight / 2;
            switchBottom = switchTop + mSwitchHeight;
            break;

        case Gravity.BOTTOM:
            switchBottom = getHeight() - getPaddingBottom();
            switchTop = switchBottom - mSwitchHeight;
            break;
        }

        mSwitchLeft = switchLeft;
        mSwitchTop = switchTop;
        mSwitchBottom = switchBottom;
        mSwitchRight = switchRight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the switch
        int switchLeft = mSwitchLeft;
        int switchTop = mSwitchTop;
        int switchRight = mSwitchRight;
        int switchBottom = mSwitchBottom;

        mTrackDrawable.setBounds(switchLeft, switchTop, switchRight, switchBottom);
        mTrackDrawable.draw(canvas);

        canvas.save();

        mTrackDrawable.getPadding(mTempRect);
        int switchInnerLeft = switchLeft + mTempRect.left;
        int switchInnerTop = switchTop + mTempRect.top;
        int switchInnerRight = switchRight - mTempRect.right;
        int switchInnerBottom = switchBottom - mTempRect.bottom;
        canvas.clipRect(switchInnerLeft, switchTop, switchInnerRight, switchBottom);

        mBitmapRect.left = switchInnerLeft;
        mBitmapRect.top = switchInnerTop;
        mBitmapRect.right = switchInnerRight;
        mBitmapRect.bottom = switchInnerBottom;
        if (mBitmapOn != null && mBitmapOff != null) {
            if (mValueAnimator != null && mValueAnimator.isRunning()) {
                drawAnimationBitmap(canvas);
            } else if (mTouchMode != TOUCH_MODE_IDLE) {
                drawAnimationBitmapWitdhDrag(canvas);
            }
        } else {
            if (DEBUG)
                Log.w(TAG, "mBitmapOn == null or mBitmapOff == null");
        }

        mThumbDrawable.getPadding(mTempRect);
        final int thumbPos = (int) (mThumbPosition + 0.5f);
        int thumbLeft = switchInnerLeft - mTempRect.left + thumbPos;
        int thumbRight = switchInnerLeft + thumbPos + mThumbWidth + mTempRect.right;

        mThumbDrawable.setBounds(thumbLeft, switchTop, thumbRight, switchBottom);
        mThumbDrawable.draw(canvas);

        // mTextColors should not be null, but just in case
        if (mTextColors != null) {
            mTextPaint.setColor(mTextColors.getColorForState(getDrawableState(),
                    mTextColors.getDefaultColor()));
        }
        mTextPaint.drawableState = getDrawableState();

        Layout switchText = getTargetCheckedState() ? mOnLayout : mOffLayout;
        if (switchText != null) {
            canvas.translate((thumbLeft + thumbRight) / 2 - switchText.getWidth() / 2,
                    (switchInnerTop + switchInnerBottom) / 2 - switchText.getHeight() / 2);
            switchText.draw(canvas);
        }

        canvas.restore();
    }

    @Override
    public int getCompoundPaddingLeft() {
        if (true) {
            return super.getCompoundPaddingLeft();
        }
        int padding = super.getCompoundPaddingLeft() + mSwitchWidth;
        if (!TextUtils.isEmpty(getText())) {
            padding += mSwitchPadding;
        }
        return padding;
    }

    @Override
    public int getCompoundPaddingRight() {
        int padding = super.getCompoundPaddingRight() + mSwitchWidth;
        if (!TextUtils.isEmpty(getText())) {
            padding += mSwitchPadding;
        }
        return padding;
    }

    private int getThumbScrollRange() {
        if (mTrackDrawable == null) {
            return 0;
        }
        mTrackDrawable.getPadding(mTempRect);
        return mSwitchWidth - mThumbWidth - mTempRect.left - mTempRect.right;
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        int[] myDrawableState = getDrawableState();

        // Set the state of the Drawable
        // Drawable may be null when checked state is set from XML, from super
        // constructor
        if (mThumbDrawable != null)
            mThumbDrawable.setState(myDrawableState);
        if (mTrackDrawable != null)
            mTrackDrawable.setState(myDrawableState);

        invalidate();
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == mThumbDrawable
                || who == mTrackDrawable;
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        mThumbDrawable.jumpToCurrentState();
        mTrackDrawable.jumpToCurrentState();
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(Switch.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(Switch.class.getName());
        CharSequence switchText = isChecked() ? mTextOn : mTextOff;
        if (!TextUtils.isEmpty(switchText)) {
            CharSequence oldText = info.getText();
            if (TextUtils.isEmpty(oldText)) {
                info.setText(switchText);
            } else {
                StringBuilder newText = new StringBuilder();
                newText.append(oldText).append(' ').append(switchText);
                info.setText(newText);
            }
        }
    }
}
