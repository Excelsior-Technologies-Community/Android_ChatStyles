# **Android Chat Styles Library**

---
This library provides customizable chat bubble views for Android applications, making it easy to implement chat interfaces similar to WhatsApp, Messenger, or other chat apps.

---

## ✨ **Features**

- Sender and receiver chat bubbles with different colors.

- Support for text messages with optional time display.

- Custom bubble shapes with tail effect for sender/receiver.

- Easy to set colors, padding, text size, and fonts.

- Can use custom drawable backgrounds for bubbles.

- Works inside ScrollView or RecyclerView.



  ---

# **Preview**
---
<p align="center">
  <img src="https://github.com/user-attachments/assets/33ac7cd3-2951-4c3b-b40b-84f087b013f2"
       alt="Demo GIF"
       width="200">



</p>


## ⚡ **Installation**

**Step 1:** Add JitPack repository to your root build.gradle:

```gradle
maven { url = uri("https://jitpack.io") }
```

**Step 2:** Add the dependency in your app `build.gradle` (example if hosted on JitPack):  

```gradle
dependencies {
	        implementation 'com.github.Excelsior-Technologies-Community:Android_ChatStyles:1.0.0'

}
```
## ⚡ **attrs file**

```

<?xml version="1.0" encoding="utf-8"?>
<resources>

    <declare-styleable name="ChatBubbleView">

        <!-- The text message to display in the bubble -->
        <attr name="chatText" format="string" />

        <!-- Set true if this bubble is from the sender, false if from receiver -->
        <attr name="isSender" format="boolean" />

        <!-- Show or hide the avatar image beside the bubble -->
        <attr name="showAvatar" format="boolean" />

        <!-- Background color for the avatar circle -->
        <attr name="avatarBackgroundColor" format="color"/>

        <!-- Drawable resource for avatar image -->
        <attr name="avatarSrc" format="reference"/>

        <!-- Show or hide audio bubble; default hidden unless setAudioMessage() is called -->
        <attr name="audioShow" format="boolean"/>

        <!-- Drawable resource for an image bubble -->
        <attr name="imgSrc" format="reference"/>

        <!-- Show or hide the image bubble -->
        <attr name="imgShow" format="boolean"/>

        <!-- Bubble background color for sender -->
        <attr name="senderBubbleColor" format="color" />

        <!-- Bubble background color for receiver -->
        <attr name="receiverBubbleColor" format="color" />

        <!-- Color of the chat text -->
        <attr name="chatTextColor" format="color" />

        <!-- Color of the time text -->
        <attr name="timeTextColor" format="color"/>

        <!-- Custom drawable for sender bubble background -->
        <attr name="senderBackgroundDrawable" format="reference" />

        <!-- Custom drawable for receiver bubble background -->
        <attr name="receiverBackgroundDrawable" format="reference" />

        <!-- Corner radius of the bubble -->
        <attr name="bubbleCornerRadius" format="dimension" />

        <!-- Padding inside the bubble -->
        <attr name="bubblePadding" format="dimension" />

        <!-- Size of the chat text in sp -->
        <attr name="chatTextSize" format="dimension" />

        <!-- Show or hide the time below the message -->
        <attr name="showTime" format="boolean" />

        <!-- Time string to display below the message -->
        <attr name="chatTime" format="string" />

        <!-- Stroke color for bubble border -->
        <attr name="bubbleStrokeColor" format="color" />

        <!-- Stroke width for bubble border -->
        <attr name="bubbleStrokeWidth" format="dimension" />

        <!-- Maximum width of the bubble -->
        <attr name="maxBubbleWidth" format="dimension" />

        <!-- Font style for chat text: normal, bold, italic -->
        <attr name="chatFontStyle">
            <enum name="normal" value="0"/>
            <enum name="bold" value="1"/>
            <enum name="italic" value="2"/>
        </attr>

    </declare-styleable>

</resources>



```

## ⚡ **Usage**

1. Add in XML

```
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/scrollView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#FFFFFF"
    android:fillViewport="true"
    tools:context="com.ext.android_chatstyles.MainActivity"
    android:padding="16dp">

    <LinearLayout
        android:id="@+id/chatContainer"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <!-- Receiver Bubble (Left) -->
        <com.ext.android_chat_style.ChatBubbleView
            android:id="@+id/bubble2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="start"
            app:isSender="false"
            app:showAvatar="true"
            app:avatarSrc="@drawable/ic_avatar_placeholder"
            app:chatText="Hi there!"
            app:chatTime="10:31 AM"
            app:showTime="true"
            app:receiverBubbleColor="#E5E5EA"
            app:chatTextColor="#000000"
            android:layout_marginBottom="12dp"/>

        <!-- Sender Bubble (Right) -->
        <com.ext.android_chat_style.ChatBubbleView
            android:id="@+id/bubble1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="end"
            app:isSender="true"
            app:showTime="true"

            app:showAvatar="true"
            app:chatText="Hello!"
            app:chatTime="10:30 AM"
            app:senderBubbleColor="#5E6EFF"
            app:chatTextColor="#FFFFFF"
            android:layout_marginBottom="12dp"/>

        <!-- Sender Image Bubble (Right) -->
        <com.ext.android_chat_style.ChatBubbleView
            android:id="@+id/bubble3"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="end"
            app:isSender="true"
            app:showTime="true"
          app:imgShow="true"
            app:chatTime="10:32 AM"
            app:imgSrc="@drawable/ic_avatar_placeholder"
            android:layout_marginBottom="12dp"/>

        <!-- Receiver Audio Bubble (Left) -->
        <com.ext.android_chat_style.ChatBubbleView
            android:id="@+id/bubble4"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="start"
            app:avatarSrc="@drawable/ic_avatar_placeholder"
            app:isSender="false"
            app:audioShow="true"
            app:showTime="true"
            app:chatTime="10:33 AM"
            android:layout_marginBottom="12dp"/>

    </LinearLayout>
</ScrollView>



```

## ⚡ **Main Activity**

```
package com.ext.android_chatstyles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ext.android_chat_style.ChatBubbleView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 🔥 IMPORTANT:
        // By default, audio bubbles are hidden.
        // To show the audio bubble, you MUST call setAudioMessage()
        // Example: show audio of 15 seconds
        bubble4.setAudioMessage("0:15")
    }
}

```



## **📄 License**

**MIT License**  
```
Copyright (c) 2025 Excelsior Technologies

Permission is hereby granted, free of charge, to any person obtaining a copy  
of this software and associated documentation files (the "Software"), to deal  
in the Software without restriction, including without limitation the rights  
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell  
copies of the Software, and to permit persons to whom the Software is  
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all  
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED **"AS IS"**, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,  
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
```



  
