package com.riridev.ririapp.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.riridev.ririapp.R

class CustomButton : MaterialButton {
    private var mState = 0

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr,
    )

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context) : super(context)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        state = if(isEnabled) DONE else NORMAL
    }

    private var state: Int
        get() = mState
        set(state) {
            when (state) {
                DONE -> {
                    setBackgroundColor(ContextCompat.getColor(context, R.color.primary))
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                }
                NORMAL -> {
                    setBackgroundColor(ContextCompat.getColor(context, R.color.primary_variant))
                    setTextColor(ContextCompat.getColor(context, R.color.gray))
                }
                else -> return
            }
            mState = state
        }

    companion object {
        const val NORMAL = 0
        const val DONE = 1
    }

}