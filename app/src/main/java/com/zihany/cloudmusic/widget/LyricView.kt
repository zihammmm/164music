package com.zihany.cloudmusic.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Looper
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Scroller
import androidx.core.content.res.ResourcesCompat
import com.zihany.cloudmusic.App
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.song.bean.LrcEntry
import com.zihany.cloudmusic.util.LogUtil
import com.zihany.cloudmusic.util.LrcUtils
import kotlinx.android.synthetic.main.activity_song.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class LyricView constructor(private val mContext: Context, private val attrs: AttributeSet?, defStyleAttr: Int) :
        View(mContext, attrs, defStyleAttr) {
    companion object {
        private val TAG = "LyricView"
        private val PLAY_DRAWABLE_WIDTH = App.getContext().resources.getDimension(R.dimen.dp_30)
        private val TIME_TEXT_WIDTH = App.getContext().resources.getDimension(R.dimen.dp_40)
        const val ADJUST_DURATION = 100
        const val ANIMATION_DURARION = 1000
    }

    private val lrcEntryList: MutableList<LrcEntry> = ArrayList()
    private val lrcPaint = TextPaint()
    private var timeFontMetrics: Paint.FontMetrics? = null
    private var timePaint: TextPaint = TextPaint()
    private var playDrawable: Drawable? = null
    private var normalTextColor = 0
    private var textSize = 0f
    private var currentTextColor = 0
    private var timelineTextColor = 0
    private var timelineColor = 0
    private var padding = 0f
    private var defaultLabel = ""
    private var animator: ValueAnimator? = null
    private val gestureDetector: GestureDetector
    var listener: OnPlayClickListener? = null
    private val scroller: Scroller = Scroller(mContext)
    private var isFling = false
    private var isTouching = false
    private var isShowTimeline = false
    private var textGravity = 0
    private var offset = 0f
    private var dividerHeight = 0f
    private var currentLine = 0
    var coverChangeListener: OnCoverChangeListener? = null

    private val simpleOnGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            if (lrcNotEmpty() && listener != null) {
                scroller.forceFinished(true)
                removeCallbacks(hideTimelineRunnable)
                isTouching = true
                invalidate()
            }
            return true
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            if (lrcNotEmpty()) {
                isShowTimeline = true
                offset += -distanceY
                offset = min(offset, getOffset(0))
                offset = max(offset, getOffset(lrcEntryList.size - 1))
                invalidate()
            }
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            if (lrcNotEmpty()) {
                scroller.fling(0, offset.toInt(), 0, velocityY.toInt(), 0,
                        0, getOffset(lrcEntryList.size - 1).toInt(), getOffset(0).toInt())
                isFling = true
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            LogUtil.d(TAG, "onSingleTapConfirmed")
            if (lrcNotEmpty() && isShowTimeline
                    && playDrawable!!.bounds.contains(e!!.x.toInt(), e.y.toInt())) {
                val centerLine = getCenterLine()
                val centerLineTime = lrcEntryList[centerLine].time
                if (listener!!.onPlayClick(centerLineTime)) {
                    postDelayed(hideTimelineRunnable, 400)
                    currentLine = centerLine
                    invalidate()
                }
            } else {
                coverChangeListener?.let {
                    it.onCoverChange()
                    postDelayed(hideTimelineRunnable, 400)
                }
            }
            return super.onSingleTapConfirmed(e)
        }
    }


    private val hideTimelineRunnable = Runnable {
        if (lrcNotEmpty() && isShowTimeline) {
            isShowTimeline = false
            smoothScrollTo(currentLine, ANIMATION_DURARION)
        }
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        initView()
        gestureDetector = GestureDetector(mContext, simpleOnGestureListener)
        gestureDetector.setIsLongpressEnabled(false)
    }

    private fun initView() {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.LyricView)
        textSize = ta.getDimension(R.styleable.LyricView_text_size, resources.getDimension(R.dimen.sp_16))
        dividerHeight = ta.getDimension(R.styleable.LyricView_text_divider, resources.getDimension(R.dimen.dp_10))
        normalTextColor = ta.getColor(R.styleable.LyricView_normal_color, Color.parseColor("#ccffffff"))
        currentTextColor = ta.getColor(R.styleable.LyricView_current_color, Color.parseColor("#ffffff"))
        timelineTextColor = ta.getColor(R.styleable.LyricView_time_color, Color.parseColor("#ccffffff"))
        defaultLabel = ta.getString(R.styleable.LyricView_default_label)!!
        padding = ta.getDimension(R.styleable.LyricView_lrc_padding, 0f)
        timelineColor = ta.getColor(R.styleable.LyricView_timeline_color, Color.parseColor("#f0f0f0"))
        playDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_lrc_play, null)

        val timeTextSize = ta.getDimension(R.styleable.LyricView_time_text_size, resources.getDimension(R.dimen.sp_10))
        textGravity = ta.getInteger(R.styleable.LyricView_text_gravity, LrcEntry.GRAVITY_CENTER)

        ta.recycle()

        lrcPaint.isAntiAlias = true
        lrcPaint.textSize = textSize
        lrcPaint.textAlign = Paint.Align.LEFT
        timePaint.isAntiAlias = true
        timePaint.textSize = timeTextSize
        timePaint.textAlign = Paint.Align.CENTER
        timePaint.strokeWidth = resources.getDimension(R.dimen.dp_2)
        timePaint.strokeCap = Paint.Cap.ROUND
        timeFontMetrics = timePaint.fontMetrics
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val centerY = height / 2

        if (!lrcNotEmpty()) {
            lrcPaint.color = currentTextColor
            val staticLayout = StaticLayout.Builder
                    .obtain(defaultLabel, 0, defaultLabel.length, lrcPaint, getLrcWidth().toInt())
                    .setAlignment(Layout.Alignment.ALIGN_CENTER)
                    .setLineSpacing(0f, 1f)
                    .setIncludePad(false)
                    .build()
            drawText(canvas!!, staticLayout, centerY.toFloat())
        }

        val centerLine = getCenterLine()

        if (isShowTimeline) {
            playDrawable!!.draw(canvas!!)
            timePaint.color = timelineColor
            canvas.drawLine(TIME_TEXT_WIDTH, centerY.toFloat(), width - TIME_TEXT_WIDTH, centerY.toFloat(), timePaint)

            timePaint.color = timelineTextColor
            val timeText = LrcUtils.formatTime(lrcEntryList[centerLine].time)
            val timeX = width - TIME_TEXT_WIDTH / 2
            val timeY = centerY - (timeFontMetrics!!.descent + timeFontMetrics!!.ascent) / 2
            canvas.drawText(timeText, timeX, timeY, timePaint)
        }

        canvas!!.translate(0f, offset)
        var y = 0f
        lrcPaint.textSize = textSize
        for (i: Int in lrcEntryList.indices) {
            if (i > 0) {
                y += (lrcEntryList[i - 1].getHeight() + lrcEntryList[i].getHeight()) / 2 + dividerHeight
            }
            if (i == currentLine) {
                lrcPaint.color = currentTextColor
            } else if (isShowTimeline && i == centerLine) {
                lrcPaint.color = timelineTextColor
            } else {
                lrcPaint.color = normalTextColor
            }
            drawText(canvas, lrcEntryList[i].staticLayout!!, y)
        }
    }

    private fun drawText(canvas: Canvas, staticLayout: StaticLayout, y: Float) {
        canvas.save()
        canvas.translate(padding, y - staticLayout.height / 2)
        staticLayout.draw(canvas)
        canvas.restore()
    }

    private fun lrcNotEmpty(): Boolean = lrcEntryList.isNotEmpty()

    private fun reset() {
        endAnimation()
        scroller.forceFinished(true)
        isShowTimeline = false
        isTouching = false
        isFling = false
        removeCallbacks(hideTimelineRunnable)
        lrcEntryList.clear()
        offset = 0f
        currentLine = 0
        invalidate()
    }

    private fun smoothScrollTo(line: Int, duration: Int) {
        val offset = getOffset(line)
        endAnimation()

        animator = ValueAnimator.ofFloat(this.offset, offset)
        animator?.duration = duration.toLong()
        animator?.interpolator = LinearInterpolator()
        animator?.addUpdateListener { animation ->
            this.offset = animation.animatedValue as Float
            invalidate()
        }
        LrcUtils.resetDurationScale()
        animator?.start()
    }

    private fun getOffset(line: Int): Float {
        if (lrcEntryList[line].offset == Float.MIN_VALUE) {
            var offset = (height / 2).toFloat()
            for (i: Int in 1..line) {
                offset -= (lrcEntryList[i - 1].getHeight() + lrcEntryList[i].getHeight()) / 2 + dividerHeight
            }
            lrcEntryList[line].offset = offset
        }
        return lrcEntryList[line].offset
    }

    private fun endAnimation() {
        animator?.let {
            if (it.isRunning) {
                it.end()
            }
        }
    }

    /**
     * 加载双语歌词
     */
    fun loadLrc(mainLrcText: String, secondLrcText: String) {
        LogUtil.d(TAG, "mainLrcText : $mainLrcText ")
        reset()

        val lrc = arrayOf(mainLrcText, secondLrcText)
        val parseList = LrcUtils.parseLrc(lrc)
        if (parseList != null && parseList.isNotEmpty()) {
            lrcEntryList.addAll(parseList)
        }

        lrcEntryList.sort()
        initEntryList()
        invalidate()
    }

    fun updateTime(time: Long) {
        if (!lrcNotEmpty()) {
            return
        }

        GlobalScope.launch(Dispatchers.Main) {
            val line = findShowLine(time)
            if (line != currentLine) {
                currentLine = line
                if (!isShowTimeline) {
                    smoothScrollTo(line, ANIMATION_DURARION)
                } else {
                    invalidate()
                }
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            initPlayDrawable()
            initEntryList()
            if (lrcNotEmpty()) {
                smoothScrollTo(currentLine, 0)
            }
        }
    }

    private fun getCenterLine(): Int {
        var centerLine = 0
        var minDistance = Float.MAX_VALUE
        for (i: Int in lrcEntryList.indices) {
            if (abs(offset - getOffset(i)) <= minDistance) {
                minDistance = abs(offset - getOffset(i))
                centerLine = i
            }
        }
        return centerLine
    }

    private fun initEntryList() {
        if (width == 0 || !lrcNotEmpty()) {
            return
        }
        for (lrcEntry: LrcEntry in lrcEntryList) {
            lrcEntry.init(lrcPaint, getLrcWidth().toInt(), textGravity)
        }
        offset = height / 2f
    }

    private fun getLrcWidth(): Float {
        return width - 2 * padding
    }

    private fun initPlayDrawable() {
        val l = ((TIME_TEXT_WIDTH - PLAY_DRAWABLE_WIDTH) / 2).toInt()
        val t = (height / 2 - PLAY_DRAWABLE_WIDTH / 2).toInt()
        val r = (l + PLAY_DRAWABLE_WIDTH).toInt()
        val b = (t + PLAY_DRAWABLE_WIDTH).toInt()
        playDrawable?.setBounds(l, t, r, b)
    }

    private fun findShowLine(time: Long): Int {
        var left = 0
        var right = lrcEntryList.size
        while (left <= right) {
            val mid = (left + right) / 2
            val middleTime = lrcEntryList[mid].time

            if (time < middleTime) {
                right = mid - 1
            } else {
                if (mid + 1 >= lrcEntryList.size || time < lrcEntryList[mid + 1].time) {
                    return mid
                }
                left = mid + 1
            }
        }
        return 0
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event!!.actionMasked == MotionEvent.ACTION_UP || event.actionMasked == MotionEvent.ACTION_CANCEL) {
            isTouching == false
            if (lrcNotEmpty() && isFling) {
                smoothScrollTo(getCenterLine(), ADJUST_DURATION)
            }
        }
        return gestureDetector.onTouchEvent(event)
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            offset = scroller.currY.toFloat()
            invalidate()
        }
        if (isFling && scroller.isFinished) {
            isFling = false
            if (lrcNotEmpty() && !isTouching) {
                smoothScrollTo(getCenterLine(), ADJUST_DURATION)
            }
        }
    }

    override fun onDetachedFromWindow() {
        removeCallbacks(hideTimelineRunnable)
        super.onDetachedFromWindow()
    }

    interface OnPlayClickListener {
        fun onPlayClick(time: Long): Boolean
    }

    interface OnCoverChangeListener {
        fun onCoverChange()
    }

}