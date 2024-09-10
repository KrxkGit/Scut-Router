package com.example.scut_router

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.format.Formatter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.scut_router.databinding.ActivityMainBinding
import java.net.Inet4Address
import java.net.NetworkInterface
import org.apache.commons.net.util.SubnetUtils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val requestAccessFilePermission: Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissions() // 请求文件管理权限

        // 加载布局
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 为按钮设置点击事件
        var intent: Intent
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

        // 检查连接
        if (!checkIsValidConn()) {
            val alertBuilder = AlertDialog.Builder(this)
            alertBuilder.setTitle(getString(R.string.validConnTitle))
            alertBuilder.setMessage(getString(R.string.validConnMsg))
            alertBuilder.setPositiveButton(getString(R.string.validConnYes)) { _, _ ->
                finish()
            }
            alertBuilder.setNegativeButton(getString(R.string.validConnNo)) { dialog, _ ->
                dialog.dismiss()
                loadNativeLib()
            }
            alertBuilder.show()
        } else {
            loadNativeLib()
        }
    }

    private fun loadNativeLib() {
        Toast.makeText(this, getString(R.string.loadCore), Toast.LENGTH_SHORT).show()
        // 加载 Native 库
        System.loadLibrary("scut_router")
        // 初始化 SSH 库
        val downloadPath: String =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        initLibSSHCommand(downloadPath)

        Toast.makeText(this, getString(R.string.core_loaded_successfully), Toast.LENGTH_SHORT)
            .show()
    }

    private fun htonl(value: Int): Int {
        return (value shl 24) or
                ((value and 0x00FF0000) shr 8) or
                ((value and 0x0000FF00) shl 8) or
                (value shr 24)
    }

    private fun ntohl(value: Int): Int {
        return (value shl 24) or
                ((value and 0x00FF0000) shr 8) or
                ((value and 0x0000FF00) shl 8) or
                (value shr 24)
    }

    private fun checkIsValidConn(): Boolean {
        // 分析是否与 已知子网 吻合
        // 检查是否正确连接路由器
        val context: Context = this
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val dhcpInfo = wifiManager.dhcpInfo
        val ipAddr = wifiInfo.ipAddress
        val gateway = dhcpInfo.gateway

        val ipStr = Formatter.formatIpAddress(ipAddr)
        val gatewayStr = Formatter.formatIpAddress(gateway)
        println("IP 地址： $ipStr")
        println("网关地址： ${gatewayStr}")

        val interfaces = NetworkInterface.getNetworkInterfaces()
        while (interfaces.hasMoreElements()) {
            val ifa = interfaces.nextElement()
            if (!ifa.isUp || ifa.isLoopback) {
                continue
            }
            if (ifa.name == getString(R.string.wlanIfaName)) {
                val addresses = ifa.interfaceAddresses
                for (addr in addresses) {
                    if (addr.address is Inet4Address) {
                        val prefixLen = addr.networkPrefixLength
                        val subnetUtils = SubnetUtils("$ipStr/$prefixLen")
                        val subNet = subnetUtils.info

                        println("IP 子网: ${subNet.networkAddress}")

                        if (subNet.networkAddress == this.getString(R.string.default_router_conn) && gatewayStr == getString(
                                R.string.default_router_gateway
                            )) {
                            return true
                        } else {
                            return false
                        }
                    }
                }
            }
        }

        return false
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

    private external fun initLibSSHCommand(downloadPath: String)

    /**
     * 请求文件读写权限
     */
    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11 及更高版本，检查 MANAGE_EXTERNAL_STORAGE 权限
            if (Environment.isExternalStorageManager()) {
                // 权限已被授予，可以进行文件操作
                Toast.makeText(
                    this,
                    "权限已成功获取，若首次授予请重启应用以正常运行",
                    Toast.LENGTH_SHORT
                ).show()
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
                    ), requestAccessFilePermission
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            requestAccessFilePermission -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    Toast.makeText(
                        this,
                        "权限已成功获取，若首次授予请重启应用以正常运行",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(this, "权限被拒绝，无法正常工作，请重启", Toast.LENGTH_SHORT)
                        .show()
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