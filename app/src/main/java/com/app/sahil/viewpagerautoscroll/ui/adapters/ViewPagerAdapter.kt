package com.app.atsz7.viewpagerautoscroll.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.app.atsz7.viewpagerautoscroll.R
import com.app.atsz7.viewpagerautoscroll.data.model.Data
import com.app.atsz7.viewpagerautoscroll.data.model.SlideData
import kotlinx.android.synthetic.main.item_images_list.view.*

/**
 * The [ViewPagerAdapter] class is used as adapter of "slide text"
 * view pager.
 */

class ViewPagerAdapter() : PagerAdapter() {
    private var users= mutableListOf<Data>()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view = LayoutInflater.from(container.context).inflate(
            R.layout.item_images_list, container, false
        )

        with(view) {
            tvText.text = users?.get(position)?.text
        }

        container.addView(view)
        return view
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int = users?.size!!

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun addData(data: List<Data>) {
        data?.let { users?.addAll(it) }
    }
}
