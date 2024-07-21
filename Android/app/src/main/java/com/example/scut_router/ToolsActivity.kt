package com.example.scut_router

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ToolsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tools)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 定义按钮行为
        findViewById<ImageButton>(R.id.imageButtonWired).setOnClickListener() {
            wired()
        }
        findViewById<ImageButton>(R.id.imageButtonWireless).setOnClickListener() {
            wireless()
        }
        findViewById<ImageButton>(R.id.imageButtonSyncTime).setOnClickListener() {
            syncTime()
        }
        findViewById<ImageButton>(R.id.imageButtonReboot).setOnClickListener() {
            reboot()
        }
    }

    private external fun wired()
    private external fun wireless()
    private external fun syncTime()
    private external fun reboot()
}