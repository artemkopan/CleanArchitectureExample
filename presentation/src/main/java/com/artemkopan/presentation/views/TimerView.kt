package com.artemkopan.presentation.views

import android.content.Context
import android.os.SystemClock
import android.support.v7.widget.AppCompatTextView
import android.text.format.DateUtils
import android.util.AttributeSet

class TimerView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_TIME = 0L
    }

    var itemTime: Long = DEFAULT_TIME
        set(value) {
            field = value
            onTimeChanged()
        }

    private var shouldRunTicker: Boolean = false

    private val ticker = object : Runnable {
        override fun run() {
            onTimeChanged()

            val now = SystemClock.uptimeMillis()
            val next = now + (1000 - now % 1000)

            handler?.postAtTime(this, next)
        }
    }

    override fun onVisibilityAggregated(isVisible: Boolean) {
        super.onVisibilityAggregated(isVisible)

        if (!shouldRunTicker && isVisible) {
            shouldRunTicker = true
            ticker.run()
        } else if (shouldRunTicker && !isVisible) {
            shouldRunTicker = false
            handler.removeCallbacks(ticker)
        }
    }


    /**
     * Update the displayed time if this view and its ancestors and window is visible
     */
    private fun onTimeChanged() {
        // shouldRunTicker always equals the last value passed into onVisibilityAggregated
        if (shouldRunTicker && itemTime != DEFAULT_TIME) {
            text = DateUtils.getRelativeTimeSpanString(itemTime, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS)
        }
    }

}