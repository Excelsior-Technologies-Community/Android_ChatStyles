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
  <img src="https://github.com/user-attachments/assets/80735f74-620e-4a83-bfb4-ed7a0995a474"
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
	        implementation 'com.github.Excelsior-Technologies-Community:ReadMoreTextView:1.0.0'

}
```
## ⚡ **attrs file**

```

<?xml version="1.0" encoding="utf-8"?>
<resources>

    <declare-styleable name="ChatBubbleView">
        <attr name="chatText" format="string" />
        <attr name="isSender" format="boolean" />

        <attr name="senderBubbleColor" format="color" />
        <attr name="receiverBubbleColor" format="color" />
        <attr name="chatTextColor" format="color" />

        <attr name="senderBackgroundDrawable" format="reference" />
        <attr name="receiverBackgroundDrawable" format="reference" />
        <attr name="bubbleCornerRadius" format="dimension" />
        <attr name="bubblePadding" format="dimension" />
        <attr name="chatTextSize" format="dimension" />

        <attr name="showTime" format="boolean" />
        <attr name="chatTime" format="string" />

        <attr name="bubbleStrokeColor" format="color" />
        <attr name="bubbleStrokeWidth" format="dimension" />

        <attr name="maxBubbleWidth" format="dimension" />
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
<com.ext.android_chat_style.ChatBubbleView
    android:id="@+id/chatSender"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="end"
    android:layout_marginBottom="8dp"
    app:chatText="How is Sara?"
    app:isSender="true"
    app:showTime="true"
    app:chatTime="19:02"
    app:senderBubbleColor="#5E6EFF"
    app:chatTextColor="#FFFFFF"/>


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



  
