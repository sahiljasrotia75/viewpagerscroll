package com.app.atsz7.viewpagerautoscroll.extensions

import android.os.Handler
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import androidx.viewpager.widget.ViewPager

/**
 * This method is used to program "automatic scrolling" in a
 * [ViewPager] with an specific [interval].
 */
fun ViewPager.autoScroll(interval: Long, tvSize: AppCompatTextView, countSize: Int) {

    val handler = Handler()
    var scrollPosition = 0

    val runnable = object : Runnable {

        override fun run() {

            /**
             * Calculate "scroll position" with
             * adapter pages count and current
             * value of scrollPosition.
             */
            val count = adapter?.count ?: 0
            setCurrentItem(scrollPosition++ % count, true)
            Log.d("ScrollPostionAuto",scrollPosition.toString())
           tvSize.text= "${scrollPosition} / ${countSize}"
            handler.postDelayed(this, interval)
        }
    }

    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            // Updating "scroll position" when user scrolls manually
            scrollPosition = position + 1
           tvSize.text= "${scrollPosition} / ${countSize}"
            Log.d("ScrollPostion",scrollPosition.toString())

        }

        override fun onPageScrollStateChanged(state: Int) {
            // Not necessary
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            // Not necessary
        }
    })

    handler.post(runnable)
}
