package id.binar.fp.secondhand.ui.lib

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import id.binar.fp.secondhand.R
import java.util.*
import kotlin.concurrent.timerTask

class ImageSlider @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var viewPager: ViewPager2? = null
    private var pagerDots: LinearLayout? = null

    private val mAdapter by lazy { ImageSliderAdapter() }

    private var dots: Array<ImageView?>? = null

    private var currentPage = 0
    private var imageCount = 0

    //    private var cornerRadius: Int = 0
    private var period: Long = 0
    private var delay: Long = 0
    private var autoCycle = false

    private var selectedDot = 0
    private var unselectedDot = 0
    private var errorImage = 0
    private var placeholder = 0
    private var titleBackground = 0
    private var withTitle = true
    private var withBackground = true
    private var titleGravity: Int = 0x00800003 or 0x10
    private var indicatorGravity: Int = 0x11
    private var titleColor: Int = -0x1
    private var imageScaleType: ImageView.ScaleType = ImageView.ScaleType.FIT_CENTER
    private var swipeTimer = Timer()

    private val sScaleTypeArray = arrayOf(
        ImageView.ScaleType.MATRIX,
        ImageView.ScaleType.FIT_XY,
        ImageView.ScaleType.FIT_START,
        ImageView.ScaleType.FIT_CENTER,
        ImageView.ScaleType.FIT_END,
        ImageView.ScaleType.CENTER,
        ImageView.ScaleType.CENTER_CROP,
        ImageView.ScaleType.CENTER_INSIDE
    )

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.image_slider, this, true)
        viewPager = findViewById(R.id.view_pager)
//        pagerDots = findViewById(R.id.pager_dots)

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ImageSlider,
            defStyleAttr,
            defStyleAttr
        )

        period = typedArray.getInt(R.styleable.ImageSlider_slider_period, 1000).toLong()
        delay = typedArray.getInt(R.styleable.ImageSlider_slider_delay, 1000).toLong()
        autoCycle = typedArray.getBoolean(R.styleable.ImageSlider_slider_auto_cycle, false)
        placeholder = typedArray.getResourceId(
            R.styleable.ImageSlider_slider_placeholder,
            R.drawable.ic_image_placeholder
        )
        errorImage = typedArray.getResourceId(
            R.styleable.ImageSlider_slider_error_image,
            R.drawable.ic_image_broken
        )
        selectedDot = typedArray.getResourceId(
            R.styleable.ImageSlider_slider_selected_dot,
            R.drawable.indicator_active
        )
        unselectedDot = typedArray.getResourceId(
            R.styleable.ImageSlider_slider_unselected_dot,
            R.drawable.indicator_inactive
        )
//        titleBackground = typedArray.getResourceId(
//            R.styleable.ImageSlider_slider_title_background,
//            R.drawable.gradient
//        )
        withTitle = typedArray.getBoolean(R.styleable.ImageSlider_slider_with_title, true)
        withBackground = typedArray.getBoolean(R.styleable.ImageSlider_slider_with_background, true)

        typedArray.getInt(R.styleable.ImageSlider_slider_title_gravity, 0x00800003 or 0x10).let {
            titleGravity = it
        }

        typedArray.getInt(R.styleable.ImageSlider_slider_indicator_gravity, 0x11).let {
            indicatorGravity = it
        }

        typedArray.getColor(R.styleable.ImageSlider_slider_title_color, -0x1).let {
            titleColor = it
        }

        typedArray.getInt(
            R.styleable.ImageSlider_slider_image_scaleType,
            ImageView.ScaleType.FIT_CENTER.ordinal
        ).let {
            imageScaleType = sScaleTypeArray[it]
        }

    }

    /**
     * Set image list to adapter.
     *
     * @param  imageList  the image list by user
     */
    fun setImageList(imageList: List<String>) {
        imageCount = imageList.size
        mAdapter.setItem(imageList)
        mAdapter.setErrorImage(errorImage)
        mAdapter.setPlaceholderImage(placeholder)
//        mAdapter.setBackgroundImage(titleBackground)
        mAdapter.setWithTitle(withTitle)
        mAdapter.setWithBackground(withBackground)
        mAdapter.setTitleAlignment(titleGravity)
        mAdapter.setTitleColor(titleColor)
        mAdapter.setImageScaleType(imageScaleType)

        viewPager?.adapter = mAdapter
        if (imageList.isNotEmpty()) {
//            setupDots(imageList.size)
//            setCurrentIndicator(currentPage)
            if (autoCycle) {
                stopSliding()
                startSliding()
            }
        }
    }

    fun setImageListWithAdapter(adapter: RecyclerView.Adapter<*>?, size: Int = 0) {
        imageCount = size
        viewPager?.adapter = adapter
        if (size != 0) {
//            setupDots(size)
//            setCurrentIndicator(currentPage)
            if (autoCycle) {
                stopSliding()
                startSliding()
            }
        }
    }

//    private fun setupDots(size: Int) {
//        println(indicatorGravity)
//        pagerDots?.gravity = indicatorGravity
//        pagerDots?.removeAllViews()
//        dots = arrayOfNulls(size)
//        val params: LinearLayout.LayoutParams =
//            LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//        params.setMargins(8, 0, 8, 0)
//
//        for (i in 0 until size) {
//            dots!![i] = ImageView(context)
//            pagerDots?.addView(dots!![i], params)
//        }
//        viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                currentPage = position
//                setCurrentIndicator(currentPage)
//            }
//        })
//    }

//    private fun setCurrentIndicator(index: Int) {
//        val childCount = pagerDots!!.childCount
//
//        for (i in 0 until childCount) {
//            val imageView = pagerDots!![i] as ImageView
//            imageView.setImageDrawable(
//                ContextCompat.getDrawable(
//                    context,
//                    R.drawable.indicator_selector
//                )
//            )
//            imageView.isSelected = i == index
//        }
//    }

    /**
     * Start image sliding.
     *
     * @param  changeablePeriod  optional period value
     */
    fun startSliding(changeablePeriod: Long = period) {
        stopSliding()
        scheduleTimer(changeablePeriod)
    }

    /**
     * Stop image sliding.
     *
     */
    fun stopSliding() {
        swipeTimer.cancel()
        swipeTimer.purge()
    }


    private fun scheduleTimer(period: Long) {
        val handler = Handler(Looper.getMainLooper())
        val update = Runnable {
            if (currentPage == imageCount) {
                currentPage = 0
            }
            viewPager?.setCurrentItem(currentPage++, true)
        }
        swipeTimer = Timer()
        swipeTimer.schedule(timerTask {
            handler.post(update)
        }, delay, period)
    }
}