package com.example.scut_router

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.save_info_button).setOnClickListener() {
            // 提前保存用户名和密码，否则视图刷新无法获取
            val username = findViewById<EditText>(R.id.username).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()

            // 本科生自动登录
            val radio = findViewById<RadioButton>(R.id.radioButton)

            if (radio.isChecked) {
                autoLogin()
            } else {
                cancelAutoLogin()
            }

            if (username.isEmpty() || password.isEmpty()) {
                setScutInfo("", "")
            } else {
                setScutInfo(username, password)
            }

            Toast.makeText(this, "命令发送完成", Toast.LENGTH_SHORT).show()
        }
    }

    private external fun setScutInfo(username : String, password : String)
    private external fun autoLogin()
    private external fun cancelAutoLogin()

}