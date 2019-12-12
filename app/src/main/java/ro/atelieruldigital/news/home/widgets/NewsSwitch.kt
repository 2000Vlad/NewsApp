package ro.atelieruldigital.news.home.widgets

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec.*
import ro.atelieruldigital.news.R
import java.lang.Exception
import kotlin.math.max
import kotlin.math.min

/**
 * This widget acts like a switch which lets the user choose either top headlines or all news
 */
class NewsSwitch(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, drawable: Drawable) : this(context, null) {
        this.drawable = drawable
    }

    var allNewsTint: Int
    var headlinesTint: Int
    var state: Int
    var drawable: Drawable? = null
        set(value) {
            field = value
            if (!isInLayout) requestLayout()
        }

    var currentTint: Int
    private var caretPosition: PointF = PointF(-1f, -1f)
    private lateinit var caretSize: Point

    private val DRAWABLE_DESIRED_WIDTH get() = (drawable!!.intrinsicWidth * context.resources.displayMetrics.density).toInt()
    private val DRAWABLE_DESIRED_HEIGHT get() = (drawable!!.intrinsicHeight * context.resources.displayMetrics.density).toInt()

    /**
     * This is the actual width of the news drawable (computed in [onSizeChanged])
     */
    private var drawableWidth: Int = -1
    /**
     * This is the actual height of the news drawable (computed in [onSizeChanged])
     */
    private var drawableHeight: Int = -1
    /**
     * This is the ratio between the [drawableHeight] and the space reserved for the caret
     * ```
     * (view.height / drawableHeight) = DRAWABLE_MARGIN_RATIO + 1
     * ```
     */
    private val DRAWABLE_MARGIN_RATIO = 0.35
    /**
     * This is the width that the view would like to be drawn
     */
    private val DESIRED_WIDTH get() = DRAWABLE_DESIRED_WIDTH
    /**
     * This is the height that the view would like to be drawn
     */
    private val DESIRED_HEIGHT get() = DRAWABLE_DESIRED_HEIGHT + DRAWABLE_DESIRED_HEIGHT * DRAWABLE_MARGIN_RATIO
    /**
     * This is the length of the animations between states
     */
    private val ANIMATION_LENGTH = 175

    val HEADLINES = -1
    val ALL_NEWS = -2

    /**
     * This is the ratio of the caret's Y coordinate relative to the view's height
     * ```
     *
     * ```
     */
    private val CARET_HEIGHT_POSITION_RATIO = 0.9
    /**
     * This is the ratio of the caret's X coordinate relative to the view's height when caret is in ALL_NEWS state
     * ```
     * allNewsCaret.x = (viewWidth * CARET_All_NEWS_WIDTH_POSITION_RATIO)
     * ```
     */
    private val CARET_ALL_NEWS_WIDTH_POSITION_RATIO = 0.65
    /**
     * This is the ratio of the caret's X coordinate relative to the view's width when caret is in HEADLINES state
     * ```
     * headlineCaret.x = (viewWidth * CARET_HEADLINES_WIDTH_POSITION_RATIO)
     * ```
     */
    private val CARET_HEADLINES_WIDTH_POSITION_RATIO = 0.1
    /**
     * This is the ratio of the caret's width relative to the view's width
     * ```
     * caret.width = (viewWidth * CARET_WIDTH_RATIO)
     * ```
     */
    private val CARET_WIDTH_RATIO = 0.3
    /**
     * This is the ratio of the caret's height relative to the view's height
     * ```
     * caret.height = (viewHeight * CARET_WIDTH_RATIO)
     * ```
     */
    private val CARET_HEIGHT_RATIO = 0.1
    private val CARET_CORNER_RADIUS = 6f


    init {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.NewsSwitch)
            allNewsTint = ta.getColor(R.styleable.NewsSwitch_allNewsTint, Color.TRANSPARENT)
            headlinesTint = ta.getColor(R.styleable.NewsSwitch_headlinesTint, Color.TRANSPARENT)
            state = ta.getInt(R.styleable.NewsSwitch_state, ALL_NEWS)
            drawable = ta.getDrawable(R.styleable.NewsSwitch_drawable)
            when (state) {
                ALL_NEWS -> {
                    currentTint = allNewsTint

                }
                HEADLINES -> {
                    currentTint = headlinesTint
                }
                else -> throw NewsSwitchException()
            }

            ta.recycle()
        } else {
            allNewsTint = Color.TRANSPARENT
            headlinesTint = Color.TRANSPARENT
            state = ALL_NEWS
            currentTint = allNewsTint


        }
        isClickable = true
        isFocusable = true
    }

    override fun performClick(): Boolean {
        onClick()
        return super.performClick()
    }

    /**
     * This function calculates the caret position when the view is in a specified state
     */
    private fun getDefaultCaretPosition(size: Point, state: Int): PointF {
        val x: Int
        val y: Int
        when (state) {
            HEADLINES -> {
                x = (CARET_HEADLINES_WIDTH_POSITION_RATIO * size.x).toInt()
                y = (CARET_HEIGHT_POSITION_RATIO * size.y).toInt()
            }
            ALL_NEWS -> {
                x = (CARET_ALL_NEWS_WIDTH_POSITION_RATIO * size.x).toInt()
                y = (CARET_HEIGHT_POSITION_RATIO * size.y).toInt()
            }
            else -> throw NewsSwitchException()
        }
        return PointF(x.toFloat(), y.toFloat())
    }

    /**
     * This method runs the necessary animations to move from one state to another
     */
    private fun onClick() {
        val startTint =
                when (state) {
                    ALL_NEWS -> allNewsTint
                    HEADLINES -> headlinesTint
                    else -> throw NewsSwitchException()
                }
        val endTint =
                when (state) {
                    ALL_NEWS -> headlinesTint
                    HEADLINES -> allNewsTint
                    else -> throw NewsSwitchException()
                }
        val startCaretX = caretPosition.x
        val endCaretX = when (state) {
            ALL_NEWS -> getDefaultCaretPosition(Point(width, height), HEADLINES).x
            HEADLINES -> getDefaultCaretPosition(Point(width, height), ALL_NEWS).x
            else -> throw NewsSwitchException()
        }
        val tintAnimator = ValueAnimator.ofArgb(startTint, endTint)
        val caretAnimator = ValueAnimator.ofFloat(startCaretX, endCaretX)
        tintAnimator.duration = ANIMATION_LENGTH.toLong()
        caretAnimator.duration = ANIMATION_LENGTH.toLong()
        tintAnimator.addUpdateListener {
            currentTint = it.animatedValue as Int
            invalidate()
        }
        caretAnimator.addUpdateListener {
            caretPosition.x = it.animatedValue as Float
            invalidate()
        }
        tintAnimator.start()
        caretAnimator.start()
        state = when (state) {
            ALL_NEWS -> HEADLINES
            HEADLINES -> ALL_NEWS
            else -> throw  NewsSwitchException()
        }

    }

    /**
     * Gets the Drawable size relative to the view in order to play well with
     * disproportionate dimansions
     * @param w view width
     * @param h view height
     * @return [Point](drawableWidth, drawableHeight)
     */
    private fun getDrawableSize(w: Int, h: Int): Point {

        val drawableWidth = w
        val proportionalDrawableHeight = drawableWidth / 5.00
        val constrainedDrawableHeight = h / (DRAWABLE_MARGIN_RATIO + 1.0)
        val drawableHeight =
                if (h <= w)
                    min(proportionalDrawableHeight, constrainedDrawableHeight)
                else max(proportionalDrawableHeight, constrainedDrawableHeight)
        return Point(drawableWidth.toInt(), drawableHeight.toInt())
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val drawableSize = getDrawableSize(w, h)
        drawableWidth = drawableSize.x
        drawableHeight = drawableSize.y
        caretPosition = getDefaultCaretPosition(Point(width, height), state)
        caretSize = Point((w * CARET_WIDTH_RATIO).toInt(), (h * CARET_HEIGHT_RATIO).toInt())

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val width: Int
        val height: Int
        width = when (widthMode) {
            AT_MOST -> {
                min(DESIRED_WIDTH, widthSize)
            }
            EXACTLY -> {
                widthSize
            }
            UNSPECIFIED -> {
                DESIRED_WIDTH
            }
            else -> throw Exception()
        }
        height = when (heightMode) {
            AT_MOST -> {
                min(DESIRED_HEIGHT.toInt(), heightSize)
            }
            EXACTLY -> {
                heightSize
            }
            UNSPECIFIED -> {
                DESIRED_HEIGHT.toInt()
            }
            else -> throw Exception()
        }

        setMeasuredDimension(width, height)
    }


    val caretRect: RectF = RectF(0f, 0f, 0f, 0f)
    val paint = Paint()
    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) return

        val tint = currentTint
        if (drawable != null) {
            drawable!!.setTint(tint)
            drawable!!.setBounds(0, 0, drawableWidth, drawableHeight)
            drawable!!.draw(canvas)

            caretRect.apply {
                left = caretPosition.x
                top = caretPosition.y
                bottom = caretPosition.y + caretSize.y
                right = caretPosition.x + caretSize.x
            }
            paint.color = tint

            canvas.drawRoundRect(caretRect, CARET_CORNER_RADIUS, CARET_CORNER_RADIUS, paint)
        }


    }


}


class NewsSwitchException : Exception("'state' must be one of [ALL_NEWS, HEADLINES]")
