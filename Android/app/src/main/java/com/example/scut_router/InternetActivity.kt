package com.example.scut_router

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InternetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_internet)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.save_info_button).setOnClickListener() {
            val ip = findViewById<EditText>(R.id.scut_ip).text.toString()
            val dns1 = findViewById<EditText>(R.id.scut_dns1).text.toString()
            val dns2 = findViewById<EditText>(R.id.scut_dns2).text.toString()
            val netmask = findViewById<EditText>(R.id.scut_netmask).text.toString()
            val gateway = findViewById<EditText>(R.id.scut_gateway).text.toString()

            val dns = "$dns1 $dns2"

            setNetwork(ip, dns, netmask, gateway)
        }
    }

    private external fun setNetwork(ip : String, dnsArr : String, netmask : String, gateway : String)
}