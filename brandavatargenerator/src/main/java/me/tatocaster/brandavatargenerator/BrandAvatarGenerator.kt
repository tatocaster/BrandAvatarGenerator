package me.tatocaster.brandavatargenerator

import android.graphics.*
import android.graphics.Paint.Cap
import androidx.annotation.ColorInt
import androidx.palette.graphics.Palette
import timber.log.Timber


class BrandAvatarGenerator private constructor(
    private val brandDrawableBitmap: Bitmap,
    private val avatar: Bitmap,
    private val downScaleFactor: Float,
    private val shapeOpacity: Int
) {
    private var canvas: Canvas
    private var mutableBitmap: Bitmap =
        Bitmap.createBitmap(avatar.width, avatar.height, Bitmap.Config.ARGB_8888)

    private var x0 = 0f
    private var y0 = 0f
    private var x1 = 0f
    private var y1 = 0f
    private var x2 = 0f
    private var y2 = 0f
    private var x3 = 0f
    private var y3 = 0f

    private val min = avatar.height / -4
    private val max = avatar.height


    init {
        canvas = Canvas(mutableBitmap)
    }

    private fun generatePalette(paletteAsyncListener: (Palette?) -> Unit) {
        Palette.from(brandDrawableBitmap).generate {
            paletteAsyncListener(it)
        }
    }

    private fun overlayBrand(): Bitmap? {
        val scaledBrand = Bitmap.createScaledBitmap(
            brandDrawableBitmap,
            (avatar.width / downScaleFactor).toInt(),
            (avatar.height / downScaleFactor).toInt(),
            true
        )

        canvas.drawBitmap(scaledBrand, 10f, 10f, null)

        return mutableBitmap
    }

    private fun generateWaves(@ColorInt rgbColor: Int): Bitmap? {
        val path = generatePaths()

        val paintFill = Paint()
        paintFill.run {
            isAntiAlias = true
            color = rgbColor
            alpha = shapeOpacity
        }

        val paintLine = Paint()
        paintLine.run {
            color = rgbColor
            alpha = shapeOpacity
            strokeWidth = 1f
            strokeCap = Cap.ROUND
            style = Paint.Style.STROKE
            isAntiAlias = true
        }

        val paintClear = Paint()
        /*paintClear.run {
            color = Color.WHITE
            isAntiAlias = true
        }*/

        // Draw background first, and then clip the irrelevant section away.
        // This will let the background be uniform irrespective of the vars used.
        canvas.drawBitmap(avatar, 0f, 0f, paintClear)
//        canvas.drawRect(x0, 0f, x3, avatar.height.toFloat(), paintFill)

        canvas.save()
        canvas.clipPath(path, Region.Op.INTERSECT)
        canvas.drawRect(x0, 0f, x3, avatar.height.toFloat(), paintFill)
//        canvas.drawRect(x0, 0f, x3, avatar.height.toFloat(), paintClear)

        canvas.restore()

//        canvas.drawPath(path, paintLine) // optional to draw the path line itself


        return mutableBitmap
    }

    private fun setVars(x1: Float, y1: Float, x2: Float, y2: Float) {
        Timber.d("vars %s, %s, %s, %s", x1, y1, x2, y2)
        // When the vars changes, the path needs to be updated.
        // In order to make clipping easier, we draw lines from [x0, y0] to
        // [x0, getHeight] and [x3, y3] to [x3, getHeight].
        // This makes the fill section of the path everything below the path.
        val cx = (avatar.width / 2).toFloat()
        val cy = (avatar.height / 2).toFloat()

        this.x0 = 0f
        this.y0 = cy + y1
        this.x1 = x1
        this.y1 = cy + y1
        this.x2 = x2
        this.y2 = cy + y2
        this.x3 = avatar.width.toFloat()
        this.y3 = cy + y2
    }

    private fun generatePaths(): Path {
        val path = Path()
        // Move to bottom, draw up
        // TODO:: consider (- paintLine.getStrokeMiter())
        path.moveTo(this.x0, avatar.height.toFloat())
        path.lineTo(this.x0, this.y0)

        // draw cubic bezier
        path.cubicTo(this.x1, this.y1, this.x2, this.y2, this.x3, this.y3)

        // Draw down
        // TODO:: consider (+ paintLine.getStrokeMiter())
        path.lineTo(this.x3, avatar.height.toFloat())

        return path
    }

    private fun randomize(): Float {
        return (min..max).random().toFloat()
    }

    // main runner function
    fun generate(listener: (Bitmap?) -> Unit) {
        setVars(
            0f,
            randomize(),
            randomize(),
            randomize()
        ) // tune this

        generatePalette {
            val vibrantSwatch = it?.vibrantSwatch
            val bitmap = generateWaves(vibrantSwatch!!.rgb)
            overlayBrand()
            listener(bitmap)
            Timber.d("palette %s", vibrantSwatch.rgb)
        }
    }

    class Builder(
        private var brandDrawableBitmap: Bitmap,
        private var avatar: Bitmap
    ) {
        private var downScaleFactor: Float = 1.0f // default
        private var alpha: Int = 80 // default
        fun withDownScalingFactor(scalingFactor: Float) =
            apply { this.downScaleFactor = scalingFactor }

        fun withOpacity(alpha: Int) = apply { this.alpha = alpha }
        fun build() = BrandAvatarGenerator(brandDrawableBitmap, avatar, downScaleFactor, alpha)
    }
}