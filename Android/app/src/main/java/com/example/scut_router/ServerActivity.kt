package com.example.scut_router

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ServerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_server)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.save_info_button).setOnClickListener() {
            val acIp = findViewById<EditText>(R.id.ac_ip).text.toString()
            val acName = findViewById<EditText>(R.id.ac_name).text.toString()

            setAcInfo(acIp, acName)

            Toast.makeText(this, "命令发送完成", Toast.LENGTH_SHORT).show()
        }
    }

    private external fun setAcInfo(acIP : String, acName : String)
}