package com.ext.android_chat_style

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

class ChatBubbleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val messageText: TextView
    private val timeText: TextView

    private var isSender = false
    private var textSizeSp = 16f

    // Default padding in pixels (~12dp horizontal, ~8dp vertical)
    private var bubblePadding = 32 // will be used if not set via attrs

    private var senderColor = Color.parseColor("#5E6EFF") // Your purple/blue
    private var receiverColor = Color.parseColor("#E5E5EA") // Light gray

    private var textColor = Color.WHITE

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

            messageText.text = ta.getString(R.styleable.ChatBubbleView_chatText) ?: ""

            isSender = ta.getBoolean(R.styleable.ChatBubbleView_isSender, false)

            senderColor = ta.getColor(
                R.styleable.ChatBubbleView_senderBubbleColor,
                Color.parseColor("#5E6EFF")
            )

            receiverColor = ta.getColor(
                R.styleable.ChatBubbleView_receiverBubbleColor,
                Color.parseColor("#E5E5EA")
            )

            textColor = ta.getColor(R.styleable.ChatBubbleView_chatTextColor, Color.WHITE)

            textSizeSp = ta.getDimension(R.styleable.ChatBubbleView_chatTextSize, 16f)

            // Safe fallback: 32px ≈ 12dp on most devices
            bubblePadding = ta.getDimensionPixelSize(
                R.styleable.ChatBubbleView_bubblePadding,
                (12 * resources.displayMetrics.density).toInt() // ~12dp fallback
            )

            senderDrawable = ta.getDrawable(R.styleable.ChatBubbleView_senderBackgroundDrawable)
            receiverDrawable = ta.getDrawable(R.styleable.ChatBubbleView_receiverBackgroundDrawable)

            showTime = ta.getBoolean(R.styleable.ChatBubbleView_showTime, false)
            timeTextValue = ta.getString(R.styleable.ChatBubbleView_chatTime) ?: ""

            ta.recycle()
        }

        applyStyle()
    }

    private fun applyStyle() {
        // Message text
        messageText.textSize = textSizeSp
        messageText.setTextColor(if (isSender) textColor else Color.BLACK)

        // Time text
        timeText.text = timeTextValue
        timeText.visibility = if (showTime) VISIBLE else GONE
        timeText.setTextColor(if (isSender) Color.parseColor("#CCFFFFFF") else Color.parseColor("#999999"))

        // Align bubble left/right
        (layoutParams as? LayoutParams)?.gravity = if (isSender) Gravity.END else Gravity.START

        // Set bubble background
        background = when {
            isSender && senderDrawable != null -> senderDrawable
            !isSender && receiverDrawable != null -> receiverDrawable
            else -> createBubbleDrawable()
        }

        // Padding: more on sides, less on top/bottom
        setPadding(
            bubblePadding,
            bubblePadding / 2,
            bubblePadding,
            bubblePadding / 2
        )
    }

    private fun createBubbleDrawable(): GradientDrawable {
        val largeRadius = 48f  // ~18dp
        val smallRadius = 12f  // ~4dp for tail effect

        return GradientDrawable().apply {
            cornerRadii = if (isSender) {
                // Sender: small corner at bottom-right
                floatArrayOf(
                    largeRadius, largeRadius, // top-left
                    largeRadius, largeRadius, // top-right
                    largeRadius, largeRadius, // bottom-left
                    smallRadius, smallRadius  // bottom-right ← tail
                )
            } else {
                // Receiver: small corner at bottom-left
                floatArrayOf(
                    largeRadius, largeRadius, // top-left
                    largeRadius, largeRadius, // top-right
                    smallRadius, smallRadius, // bottom-right ← tail
                    largeRadius, largeRadius  // bottom-left
                )
            }

            setColor(if (isSender) senderColor else receiverColor)
        }
    }

    /* Public methods */
    fun setMessage(text: String) {
        messageText.text = text
    }

    fun setSender(isSender: Boolean) {
        this.isSender = isSender
        applyStyle()
    }

    fun setTime(time: String, show: Boolean = true) {
        timeTextValue = time
        showTime = show
        applyStyle()
    }

    fun setBubbleColors(sender: Int, receiver: Int) {
        senderColor = sender
        receiverColor = receiver
        applyStyle()
    }
}