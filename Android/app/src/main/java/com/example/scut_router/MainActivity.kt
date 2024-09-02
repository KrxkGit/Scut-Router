package com.example.scut_router

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.scut_router.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val REQUEST_CODE: Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissions() // 请求文件管理权限

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

        // 不销毁 SSH 库，因为应用转到后台也会调用此处
//        destroyLibSSHCommand()
    }

    /**
     * A native method that is implemented by the 'scut_router' native library,
     * which is packaged with this application.
     */

    private external fun destroyLibSSHCommand()

    companion object {
        private external fun initLibSSHCommand(downloadPath: String)
        // Used to load the 'scut_router' library on application startup.
        init {
            System.loadLibrary("scut_router")
            // 初始化 SSH 库
            val downloadPath : String = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
            initLibSSHCommand(downloadPath)
        }

    }

    /**
     * 请求文件读写权限
     */
    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11 及更高版本，检查 MANAGE_EXTERNAL_STORAGE 权限
            if (Environment.isExternalStorageManager()) {
                // 权限已被授予，可以进行文件操作
                Toast.makeText(this, "权限已成功获取，若首次授予请重启应用以正常运行", Toast.LENGTH_SHORT).show()
            } else {
                // 权限未被授予，提示用户去设置中开启
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
        } else {
            // Android 10 及更低版本，检查 READ 和 WRITE 权限
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf<String>(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    Toast.makeText(this, "权限已成功获取，若首次授予请重启应用以正常运行", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, "权限被拒绝，无法正常工作，请重启", Toast.LENGTH_SHORT).show()
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}