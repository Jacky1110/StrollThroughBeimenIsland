package com.jotangi.strollthroughbeimenisland.ui.beimenManager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.jotangi.strollthroughbeimenisland.BaseFragment
import com.jotangi.strollthroughbeimenisland.R
import com.jotangi.strollthroughbeimenisland.api.ApiConfig
import com.jotangi.strollthroughbeimenisland.databinding.AppBarMainBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentBeimenManagerBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentScanCouponBinding
import com.jotangi.strollthroughbeimenisland.utility.AppUtility


class ScanCouponFragment : BaseFragment() {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentScanCouponBinding

    private val REQUEST_CAMERA: Int = 22
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var path: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentScanCouponBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupScanCouponTitle()
        checkPermission()
    }


    private fun checkPermission() {
        //先獲取相機權限
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA
            )
        } else {
            //初始化掃描器
            initScanner()
            //偵測後回傳
            resultCallback()
        }
    }

    private fun initScanner() {
        binding.scannerSurfaceView.apply {

            //創建Barcode偵測
            barcodeDetector = BarcodeDetector.Builder(requireContext())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build()

            //獲取寬高後創建Camera
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    cameraSource = CameraSource.Builder(
                        requireContext(),
                        barcodeDetector
                    )
                        .setAutoFocusEnabled(true)
                        .setRequestedPreviewSize(
                            measuredWidth,
                            measuredHeight
                        )
                        .build()
                }
            })

            //camera綁定surfaceView
            holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceCreated(holder: SurfaceHolder) {
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return
                    }
                    cameraSource.start(holder)
                }

                override fun surfaceChanged(
                    holder: SurfaceHolder,
                    format: Int,
                    width: Int,
                    height: Int
                ) {

                }

                override fun surfaceDestroyed(holder: SurfaceHolder) {
                    cameraSource.stop()
                }
            })
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (REQUEST_CAMERA == requestCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            //初始化掃描器
            initScanner()
            //偵測後回傳
            resultCallback()
        }
    }

    private fun resultCallback() {
        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                //或取資料
                val barcode = detections.detectedItems
                if (barcode.size() > 0) {
                    path = barcode.valueAt(0).displayValue
                    if (path != null && path!!.contains("coupon_no=")) {
                        //可能會多次連線會有多次要規避多次拿取api
                        val arrays = path!!.split("=|&".toRegex()).toTypedArray()
                        val coupon = arrays[1]

                        mainViewModel.reimburseCoupon(
                            requireContext(),
                            AppUtility.getLoginId(requireContext())!!,
                            AppUtility.getLoginPassword(requireContext())!!,
                            coupon
                        )

                        findNavController().navigate(
                            R.id.action_scanCouponFragment_to_beimenManagerFragment
                        )

                        Log.d("TAG", "coupon: $coupon")
                    }
                }
            }
        })
    }
}