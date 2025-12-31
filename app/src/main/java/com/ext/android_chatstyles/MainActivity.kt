package com.ext.android_chatstyles

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ext.android_chat_style.ChatBubbleView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Sender Text Bubble
        val bubble1 = findViewById<ChatBubbleView>(R.id.bubble1)


        // Receiver Text Bubble
        val bubble2 = findViewById<ChatBubbleView>(R.id.bubble2)


        // Sender Image Bubble
        val bubble3 = findViewById<ChatBubbleView>(R.id.bubble3)


        // Receiver Audio Bubble
        val bubble4 = findViewById<ChatBubbleView>(R.id.bubble4)
        bubble4.setAudioMessage("0:15")

    }
}
