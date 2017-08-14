package com.fuh.chattie.views

import android.content.Context
import android.support.annotation.LayoutRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.fuh.chattie.R

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
    }

    init {
        LayoutInflater.from(context).inflate(LAYOUT_RES, this, true)

//        if (attrs != null) {
//            val a = context.obtainStyledAttributes(attrs, R.styleable.custom_card_view)
//            if (a.hasValue(R.styleable.custom_card_view_command)) {
//                var myString = a.getString(R.styleable.custom_card_view_command)
//            }
//        }
    }

}