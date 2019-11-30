package com.ronaln.zanview

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.util.Size
import android.view.MotionEvent
import android.view.View

class ZanView : View {

    private var scaleValue: Float = 1f
    val TAG = javaClass.simpleName

    private lateinit var paint: Paint

    private lateinit var bitmapLike: Bitmap
    private lateinit var bitmapUnlike: Bitmap
    private lateinit var bitmapShining: Bitmap
    private lateinit var bitmapLikeSize: Size
    private lateinit var bitmapShiningSize: Size
    var isLike: Boolean = false
    private lateinit var res: Resources

    val DEFAULT_PADDING = SystemUtil.dp2px(context, 8f)

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initAtrrs(attrs, defStyleAttr, defStyleRes)
        initView()
    }

    private fun initView() {
        bitmapLike = BitmapFactory.decodeResource(res, R.mipmap.ic_message_like)
        bitmapUnlike = BitmapFactory.decodeResource(res, R.mipmap.ic_message_unlike)
        bitmapShining = BitmapFactory.decodeResource(res, R.mipmap.ic_message_like_shining)

        bitmapLikeSize = Size(bitmapLike.width, bitmapLike.height)
        bitmapShiningSize = Size(bitmapShining.width, bitmapShining.height)

        paint = Paint()
    }

    private fun initAtrrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        res = context.resources
        val ta =
            context.obtainStyledAttributes(attrs, R.styleable.ZanView, defStyleAttr, defStyleRes)
        isLike = ta.getBoolean(R.styleable.ZanView_isLike, false)
        ta.recycle()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = getMinSize()
        Log.d(TAG,"$size")
        setMeasuredDimension(
            getSizeByMesureSpec(size, widthMeasureSpec),
            getSizeByMesureSpec(size, heightMeasureSpec)
        )
    }

    fun getSizeByMesureSpec(minSize: Int, spec: Int): Int {
        val mode = MeasureSpec.getMode(spec)
        val size = MeasureSpec.getSize(spec)

        return when (mode) {
            MeasureSpec.EXACTLY -> size
            else //MeasureSpec.AT_MOST,MeasureSpec.UNSPECIFIED
            -> minSize
        }
    }

    private fun getMinSize(): Int {
        return bitmapLikeSize.width + bitmapShiningSize.width
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val top = ((measuredHeight - bitmapLike.height) / 2).toFloat()
        val left = ((measuredWidth - bitmapLike.width) / 2).toFloat()
        canvas.save()
        canvas.scale(scaleValue, scaleValue)
        canvas.drawBitmap(if (isLike) bitmapLike else bitmapUnlike, left, top, paint)
        if (isLike) {
            canvas.drawBitmap(bitmapShining, left, top - bitmapShiningSize.height / 2, paint)
        }
        canvas.restore()

    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> performClick()
            MotionEvent.ACTION_UP -> animClick()
            else -> {
            }
        }
        return true
    }

    private fun animClick() {
        isLike = !isLike
        val oa = ObjectAnimator.ofFloat(1f, 0.5f, 1f)
        oa.duration = 200
        oa.start()
        oa.addUpdateListener { animator ->
            scaleValue = animator.animatedValue as Float
            invalidate()
        }

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
//        bitmapLike.recycle()
//        bitmapUnlike.recycle()
//        bitmapShining.recycle()
    }
}