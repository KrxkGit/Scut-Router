package com.example.scut_router

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.scut_router.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private external fun setAcInfo(acName : String, acIp : String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 初始化 SSH 库
        initLibSSHCommand()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 为按钮设置点击事件
        var intent : Intent
        binding.imageButtonAccount.setOnClickListener() {
//            Toast.makeText(this, "Account", Toast.LENGTH_SHORT).show()
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.imageButtonInternet.setOnClickListener() {
//            Toast.makeText(this, "Internet", Toast.LENGTH_SHORT).show()
            intent = Intent(this, InternetActivity::class.java)
            startActivity(intent)
        }
        binding.imageViewButtonTool.setOnClickListener() {
//            Toast.makeText(this, "Tool", Toast.LENGTH_SHORT).show()
            intent = Intent(this, ToolsActivity::class.java)
            startActivity(intent)
        }
        binding.imageButtonServer.setOnClickListener() {
//            Toast.makeText(this, "Server", Toast.LENGTH_SHORT).show()
            intent = Intent(this, ServerActivity::class.java)
            startActivity(intent)
        }
        binding.imageButtonAbout.setOnClickListener() {
//            Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
            val url = getString(R.string.GithubSrc)
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)

        }
    }

    override fun onDestroy() {
        super.onDestroy()

        destroyLibSSHCommand()
    }

    /**
     * A native method that is implemented by the 'scut_router' native library,
     * which is packaged with this application.
     */
    private external fun initLibSSHCommand()
    private external fun destroyLibSSHCommand()

    companion object {
        // Used to load the 'scut_router' library on application startup.
        init {
            System.loadLibrary("scut_router")
        }
    }
}