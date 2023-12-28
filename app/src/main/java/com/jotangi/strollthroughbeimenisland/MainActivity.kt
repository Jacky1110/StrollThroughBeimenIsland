package com.jotangi.strollthroughbeimenisland

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.ui.AppBarConfiguration
import com.jotangi.strollthroughbeimenisland.databinding.ActivityMainBinding
import com.jotangi.strollthroughbeimenisland.databinding.AppBarMainBinding


class MainActivity : AppCompatActivity() {


    private val TAG: String = "${javaClass.simpleName}(TAG)"

    private lateinit var binding: ActivityMainBinding
    var isShowCamera = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


    override fun onStart() {
        super.onStart()

        checkPermission()
    }

    /**
     * 權限
     */
    private fun checkPermission() {

        // 拍照錄影、定位、錄音、藍牙
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
        } else {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
        }

        if (hasPermissions(*permissions)) {
            isShowCamera = false
            Log.w(TAG, "有 權限")

        } else {

            Log.w(TAG, "沒 權限")
            ActivityCompat.requestPermissions(this, permissions, 200)
        }
    }

    private fun hasPermissions(vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }
}
