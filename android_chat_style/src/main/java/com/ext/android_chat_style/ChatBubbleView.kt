package com.ext.android_chat_style

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

class ChatBubbleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val messageText: TextView
    private val timeText: TextView

    private var isSender = false
    private var textSize = 16f
    private var padding = 24

    private var senderColor =
        ContextCompat.getColor(context, android.R.color.holo_red_dark)
    private var receiverColor =
        ContextCompat.getColor(context, android.R.color.darker_gray)
    private var textColor =
        ContextCompat.getColor(context, android.R.color.white)

    private var senderDrawable: Drawable? = null
    private var receiverDrawable: Drawable? = null

    private var showTime = false
    private var timeTextValue = ""

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.view_chat_bubble, this, true)

        messageText = findViewById(R.id.tvMessage)
        timeText = findViewById(R.id.tvTime)

        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.ChatBubbleView)

            messageText.text = ta.getString(R.styleable.ChatBubbleView_chatText)
            isSender = ta.getBoolean(R.styleable.ChatBubbleView_isSender, false)

            senderColor =
                ta.getColor(R.styleable.ChatBubbleView_senderBubbleColor, senderColor)

            receiverColor =
                ta.getColor(R.styleable.ChatBubbleView_receiverBubbleColor, receiverColor)

            textColor =
                ta.getColor(R.styleable.ChatBubbleView_chatTextColor, textColor)

            textSize =
                ta.getDimension(R.styleable.ChatBubbleView_chatTextSize, textSize)

            padding =
                ta.getDimensionPixelSize(R.styleable.ChatBubbleView_bubblePadding, padding)

            senderDrawable =
                ta.getDrawable(R.styleable.ChatBubbleView_senderBackgroundDrawable)

            receiverDrawable =
                ta.getDrawable(R.styleable.ChatBubbleView_receiverBackgroundDrawable)

            showTime =
                ta.getBoolean(R.styleable.ChatBubbleView_showTime, false)

            timeTextValue =
                ta.getString(R.styleable.ChatBubbleView_chatTime) ?: ""

            ta.recycle()
        }

        applyStyle()
    }

    private fun applyStyle() {
        messageText.setTextColor(textColor)
        messageText.textSize = textSize
        timeText.text = timeTextValue
        timeText.visibility = if (showTime) VISIBLE else GONE

        // 🔥 MAIN LOGIC
        background = when {
            isSender && senderDrawable != null -> senderDrawable
            !isSender && receiverDrawable != null -> receiverDrawable
            else -> createFallbackDrawable()
        }

        setPadding(padding, padding, padding, padding)
    }

    private fun createFallbackDrawable(): Drawable {
        return GradientDrawable().apply {
            cornerRadius = 40f
            setColor(if (isSender) senderColor else receiverColor)
        }
    }

    /* Public setters */

    fun setMessage(text: String) {
        messageText.text = text
    }

    fun setSender(sender: Boolean) {
        isSender = sender
        applyStyle()
    }
}
