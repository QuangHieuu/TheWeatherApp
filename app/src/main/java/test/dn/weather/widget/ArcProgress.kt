package test.dn.weather.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import test.dn.weather.R
import java.text.DecimalFormat
import kotlin.math.cos

class ArcProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var paint: Paint? = null
    private val rectF = RectF()
    private val rectCircle = RectF()
    private var strokeWidth = 0f
    private var currentProgress = 0
    private var progress = 0f
    var max = 0
        set(max) {
            if (max > 0) {
                field = max
                invalidate()
            }
        }
    private var finishedStrokeColor = 0
    private var unfinishedStrokeColor = 0
    private var arcAngle = 0f
    private var arcBottomHeight = 0f
    private val default_finished_color = Color.WHITE
    private val default_unfinished_color = Color.rgb(72, 106, 176)
    private val default_stroke_width: Float = Utils.dp2px(resources, 4f)
    private val default_max = 100
    private val default_arc_angle = 360 * 0.8f
    private val default_distance = 37f
    private val min_size: Int = Utils.dp2px(resources, 100f).toInt()

    private fun initByAttributes(attributes: TypedArray) {
        with(attributes) {
            finishedStrokeColor =
                getColor(R.styleable.ArcProgress_arc_finished_color, default_finished_color)
            unfinishedStrokeColor =
                getColor(R.styleable.ArcProgress_arc_unfinished_color, default_unfinished_color)
            arcAngle = getFloat(R.styleable.ArcProgress_arc_angle, default_arc_angle)
            max = getInt(R.styleable.ArcProgress_arc_max, default_max)
            setProgress(getFloat(R.styleable.ArcProgress_arc_progress, 0f))
            strokeWidth =
                getDimension(R.styleable.ArcProgress_arc_stroke_width, default_stroke_width)
        }

    }

    private fun initPainters() {
        paint = Paint().apply {
            color = default_unfinished_color
            isAntiAlias = true
            strokeWidth = strokeWidth
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }
    }

    override fun invalidate() {
        initPainters()
        super.invalidate()
    }

    fun getStrokeWidth(): Float {
        return strokeWidth
    }

    fun setStrokeWidth(strokeWidth: Float) {
        this.strokeWidth = strokeWidth
        this.invalidate()
    }

    fun getProgress(): Float {
        return progress
    }

    fun setProgress(progress: Float) {
        this.progress = DecimalFormat("#.##").format(progress.toDouble()).toFloat()
        if (this.progress > max) {
            this.progress %= max.toFloat()
        }
        currentProgress = 0
        invalidate()
    }

    fun getFinishedStrokeColor(): Int {
        return finishedStrokeColor
    }

    fun setFinishedStrokeColor(finishedStrokeColor: Int) {
        this.finishedStrokeColor = finishedStrokeColor
        this.invalidate()
    }

    fun getUnfinishedStrokeColor(): Int {
        return unfinishedStrokeColor
    }

    fun setUnfinishedStrokeColor(unfinishedStrokeColor: Int) {
        this.unfinishedStrokeColor = unfinishedStrokeColor
        this.invalidate()
    }

    fun getArcAngle(): Float {
        return arcAngle
    }

    fun setArcAngle(arcAngle: Float) {
        this.arcAngle = arcAngle
        this.invalidate()
    }

    override fun getSuggestedMinimumHeight(): Int {
        return min_size
    }

    override fun getSuggestedMinimumWidth(): Int {
        return min_size
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        rectF.set(
            strokeWidth / 2f,
            strokeWidth / 2f,
            width - strokeWidth / 2f,
            MeasureSpec.getSize(heightMeasureSpec) - strokeWidth / 2f
        )
        rectCircle.set(
            strokeWidth + default_distance / 2f,
            strokeWidth + default_distance / 2f,
            width - strokeWidth - default_distance / 2f,
            MeasureSpec.getSize(heightMeasureSpec) - strokeWidth - default_distance / 2f
        )
        val radius = width / 2f
        val angle = (360 - arcAngle) / 2f
        arcBottomHeight = radius * (1 - cos(angle / 180 * Math.PI)).toFloat()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        with(paint!!) {
            val startAngle = 270 - arcAngle / 2f
            val finishedSweepAngle = currentProgress / max.toFloat() * arcAngle
            var finishedStartAngle = startAngle
            if (progress == 0f) finishedStartAngle = 0.01f
            color = unfinishedStrokeColor
            strokeWidth = this@ArcProgress.strokeWidth - 5
            canvas.drawArc(rectF, startAngle, arcAngle, false, paint!!)

            color = finishedStrokeColor
            strokeWidth = this@ArcProgress.strokeWidth
            canvas.drawArc(rectF, finishedStartAngle, finishedSweepAngle, false, paint!!)

        }
        if (arcBottomHeight == 0f) {
            val radius = width / 2f
            val angle = (360 - arcAngle) / 2f
            arcBottomHeight = radius * (1 - cos(angle / 180 * Math.PI)).toFloat()
        }
        canvas.drawCircle(
            rectCircle.centerX(),
            rectCircle.centerY(),
            rectCircle.width() / 2,
            Paint().apply {
                color = ContextCompat.getColor(context, R.color.white)
            }
        )
        if (currentProgress < progress) {
            currentProgress++
            invalidate()
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        return Bundle().apply {
            putParcelable(INSTANCE_STATE, super.onSaveInstanceState())
            putFloat(INSTANCE_STROKE_WIDTH, getStrokeWidth())
            putFloat(INSTANCE_PROGRESS, getProgress())
            putInt(INSTANCE_MAX, max)
            putInt(INSTANCE_FINISHED_STROKE_COLOR, getFinishedStrokeColor())
            putInt(INSTANCE_UNFINISHED_STROKE_COLOR, getUnfinishedStrokeColor())
            putFloat(INSTANCE_ARC_ANGLE, getArcAngle())
        }
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is Bundle) {
            with(state) {
                strokeWidth = getFloat(INSTANCE_STROKE_WIDTH)
                max = getInt(INSTANCE_MAX)
                setProgress(getFloat(INSTANCE_PROGRESS))
                finishedStrokeColor = getInt(INSTANCE_FINISHED_STROKE_COLOR)
                unfinishedStrokeColor = getInt(INSTANCE_UNFINISHED_STROKE_COLOR)
                initPainters()
                super.onRestoreInstanceState(getParcelable(INSTANCE_STATE))
            }
            return
        }
        super.onRestoreInstanceState(state)
    }

    companion object {
        private const val INSTANCE_STATE = "saved_instance"
        private const val INSTANCE_STROKE_WIDTH = "stroke_width"
        private const val INSTANCE_PROGRESS = "progress"
        private const val INSTANCE_MAX = "max"
        private const val INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color"
        private const val INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color"
        private const val INSTANCE_ARC_ANGLE = "arc_angle"
    }

    init {
        val attributes =
            context.theme.obtainStyledAttributes(attrs, R.styleable.ArcProgress, defStyleAttr, 0)
        initByAttributes(attributes)
        attributes.recycle()
        initPainters()
    }
}