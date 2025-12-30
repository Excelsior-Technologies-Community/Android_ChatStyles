package com.ext.android_chatstyles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ext.android_chat_style.ChatBubbleView
import com.ext.android_chatstyles.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Access chat bubbles
        val senderBubble = findViewById<ChatBubbleView>(R.id.chatSender)
        val receiverBubble = findViewById<ChatBubbleView>(R.id.chatReceiver)



    }
}
