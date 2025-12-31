package com.ext.android_chat_style

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import kotlin.math.min

class ChatBubbleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    // ================= VIEWS =================
    private val rootContainer: LinearLayout
    private val avatarImage: ImageView
    private val bubbleContainer: LinearLayout
    private val messageText: TextView
    private val imageMessage: ImageView
    private val audioContainer: CardView
    private val audioDuration: TextView
    private val timeText: TextView

    // ================= STATE =================
    private var isSender = false
    private var showAvatar = true
    private var avatarSrc: Drawable? = null
    private var avatarBgColor = Color.TRANSPARENT

    private var senderColor = Color.parseColor("#5E6EFF")
    private var receiverColor = Color.parseColor("#E5E5EA")
    private var textColor = Color.WHITE

    private var bubblePadding = 32
    private var textSizeSp = 16f
    private var maxBubbleWidth = 0

    // ---- TIME ----
    private var showTime = false
    private var timeTextValue = ""
    private var isTimeFromXml = false
    private var timeTextColor: Int? = null

    // ---- MESSAGE ----
    private var imgSrc: Drawable? = null
    private var imgShow = true
    private var audioShow = false  // default is false now
    private var messageType = MessageType.TEXT

    enum class MessageType { TEXT, IMAGE, AUDIO }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_chat_bubble, this, true)

        rootContainer = findViewById(R.id.rootContainer)
        avatarImage = findViewById(R.id.avatarImage)
        bubbleContainer = findViewById(R.id.bubbleContainer)
        messageText = findViewById(R.id.tvMessage)
        imageMessage = findViewById(R.id.imageMessage)
        audioContainer = findViewById(R.id.audioContainer)
        audioDuration = findViewById(R.id.audioDuration)
        timeText = findViewById(R.id.tvTime)

        attrs?.let { parseAttributes(it) }
        applyStyle()
    }

    // ================= ATTRIBUTES =================
    private fun parseAttributes(attrs: AttributeSet) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ChatBubbleView)
        try {
            messageText.text = ta.getString(R.styleable.ChatBubbleView_chatText) ?: ""
            isSender = ta.getBoolean(R.styleable.ChatBubbleView_isSender, false)
            showAvatar = ta.getBoolean(R.styleable.ChatBubbleView_showAvatar, true)

            avatarSrc = ta.getDrawable(R.styleable.ChatBubbleView_avatarSrc)
            avatarBgColor = ta.getColor(
                R.styleable.ChatBubbleView_avatarBackgroundColor,
                Color.TRANSPARENT
            )

            senderColor = ta.getColor(
                R.styleable.ChatBubbleView_senderBubbleColor,
                senderColor
            )

            receiverColor = ta.getColor(
                R.styleable.ChatBubbleView_receiverBubbleColor,
                receiverColor
            )

            textColor = ta.getColor(
                R.styleable.ChatBubbleView_chatTextColor,
                textColor
            )

            textSizeSp = ta.getDimension(
                R.styleable.ChatBubbleView_chatTextSize,
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP,
                    16f,
                    resources.displayMetrics
                )
            ) / resources.displayMetrics.scaledDensity

            bubblePadding = ta.getDimensionPixelSize(
                R.styleable.ChatBubbleView_bubblePadding,
                bubblePadding
            )

            maxBubbleWidth = ta.getDimensionPixelSize(
                R.styleable.ChatBubbleView_maxBubbleWidth,
                0
            )

            // ---- TIME ----
            showTime = ta.getBoolean(R.styleable.ChatBubbleView_showTime, false)
            ta.getString(R.styleable.ChatBubbleView_chatTime)?.let {
                timeTextValue = it
                isTimeFromXml = true
            }

            if (ta.hasValue(R.styleable.ChatBubbleView_timeTextColor)) {
                timeTextColor = ta.getColor(
                    R.styleable.ChatBubbleView_timeTextColor,
                    Color.GRAY
                )
            }

            // ---- CONTENT ----
            imgSrc = ta.getDrawable(R.styleable.ChatBubbleView_imgSrc)
            imgShow = ta.getBoolean(R.styleable.ChatBubbleView_imgShow, true)
            audioShow = ta.getBoolean(R.styleable.ChatBubbleView_audioShow, false) // default false

            // 🔥 AUTO MESSAGE TYPE
            messageType = when {
                imgSrc != null -> MessageType.IMAGE
                audioShow -> MessageType.AUDIO
                else -> MessageType.TEXT
            }

        } finally {
            ta.recycle()
        }
    }

    // ================= UI =================
    private fun applyStyle() {

        rootContainer.gravity = if (isSender) Gravity.END else Gravity.START

        // Avatar
        avatarImage.visibility = if (showAvatar && !isSender) View.VISIBLE else View.GONE
        if (!isSender) {
            avatarImage.setImageDrawable(avatarSrc)
            avatarImage.background = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(avatarBgColor)
            }
        }

        // Text
        messageText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp)
        messageText.setTextColor(if (isSender) textColor else Color.BLACK)

        // Bubble
        bubbleContainer.background =
            if (isSender) bubbleDrawable(senderColor, true)
            else bubbleDrawable(receiverColor, false)

        bubbleContainer.setPadding(
            bubblePadding,
            bubblePadding / 2,
            bubblePadding,
            bubblePadding / 2
        )

        // Time
        timeText.text = timeTextValue
        timeText.visibility = if (showTime) View.VISIBLE else View.GONE
        timeText.setTextColor(
            timeTextColor
                ?: if (isSender) Color.parseColor("#CCFFFFFF")
                else Color.parseColor("#999999")
        )

        // Content
        when (messageType) {
            MessageType.TEXT -> {
                messageText.visibility = View.VISIBLE
                imageMessage.visibility = View.GONE
                audioContainer.visibility = View.GONE
            }
            MessageType.IMAGE -> {
                messageText.visibility = View.GONE
                imageMessage.visibility = if (imgShow) View.VISIBLE else View.GONE
                imageMessage.setImageDrawable(imgSrc)
                audioContainer.visibility = View.GONE
            }
            MessageType.AUDIO -> {
                messageText.visibility = View.GONE
                imageMessage.visibility = View.GONE
                audioContainer.visibility = if (audioShow) View.VISIBLE else View.GONE
            }
        }
    }

    private fun bubbleDrawable(color: Int, isSender: Boolean): GradientDrawable {
        val big = 48f
        val small = 12f
        return GradientDrawable().apply {
            cornerRadii = if (isSender) {
                floatArrayOf(big, big, big, big, big, big, small, small)
            } else {
                floatArrayOf(big, big, big, big, small, small, big, big)
            }
            setColor(color)
        }
    }

    // ================= PUBLIC API =================

    fun setMessage(text: String) {
        messageType = MessageType.TEXT
        messageText.text = text
        applyStyle()
    }

    fun setImageMessage(resId: Int) {
        messageType = MessageType.IMAGE
        imgSrc = context.getDrawable(resId)
        applyStyle()
    }

    fun setImageMessage(drawable: Drawable?) {
        messageType = MessageType.IMAGE
        imgSrc = drawable
        applyStyle()
    }

    fun setAudioMessage(duration: String) {
        messageType = MessageType.AUDIO
        audioDuration.text = duration
        audioContainer.visibility = View.VISIBLE
        audioShow = true
        applyStyle()
    }

    fun setSender(sender: Boolean) {
        isSender = sender
        applyStyle()
    }

    // XML TIME HAS PRIORITY
    fun setTime(time: String, show: Boolean = true) {
        if (isTimeFromXml) return
        timeTextValue = time
        showTime = show
        applyStyle()
    }

    fun forceSetTime(time: String, show: Boolean = true) {
        isTimeFromXml = false
        timeTextValue = time
        showTime = show
        applyStyle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var adjustedWidth = widthMeasureSpec
        if (maxBubbleWidth > 0) {
            val parent = MeasureSpec.getSize(widthMeasureSpec)
            adjustedWidth = MeasureSpec.makeMeasureSpec(
                min(parent, maxBubbleWidth),
                MeasureSpec.AT_MOST
            )
        }
        super.onMeasure(adjustedWidth, heightMeasureSpec)
    }
}
