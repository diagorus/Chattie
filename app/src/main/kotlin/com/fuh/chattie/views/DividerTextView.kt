package com.fuh.chattie.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.annotation.ColorInt
import android.support.annotation.LayoutRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.fuh.chattie.R
import kotlinx.android.synthetic.main.view_divider_text_view.view.*

/**
 * Created by lll on 14.08.2017.
 */
class DividerTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        @LayoutRes
        private const val LAYOUT_RES = R.layout.view_divider_text_view

        private val DEFAULT_TEXT = ""
        private val DEFAULT_TEXT_COLOR = Color.parseColor("#FF000000")
        private val DEFAULT_DIVIDER_COLOR = Color.parseColor("#FF000000")
    }

    init {
        LayoutInflater.from(context).inflate(LAYOUT_RES, this, true)

        var text: String? = DEFAULT_TEXT
        var textColor: Int = DEFAULT_TEXT_COLOR
        var dividerColor: Int = DEFAULT_DIVIDER_COLOR

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.DividerTextView, 0, 0)

            try {
                text = a.getString(R.styleable.DividerTextView_dividerText)
                textColor = a.getColor(R.styleable.DividerTextView_dividerTextColor, DEFAULT_TEXT_COLOR)
                dividerColor = a.getColor(R.styleable.DividerTextView_dividerColor, DEFAULT_DIVIDER_COLOR)
            } finally {
                a.recycle()
            }
        }

        if (!text.isNullOrEmpty()) {
            tvDividerTextViewText.text = text

            val params = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            params.setMargins(8, 0, 8, 0)
            tvDividerTextViewText.layoutParams = params
        }

        tvDividerTextViewText.setTextColor(textColor)

        vDividerTextViewLeftSide.background = ColorDrawable(dividerColor)
        vDividerTextViewRightSide.background = ColorDrawable(dividerColor)
    }
}