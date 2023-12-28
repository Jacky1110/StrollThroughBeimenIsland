package com.jotangi.strollthroughbeimenisland.ui.memberCoupon

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.zxing.BarcodeFormat
import com.jotangi.strollthroughbeimenisland.BaseFragment
import com.jotangi.strollthroughbeimenisland.R
import com.jotangi.strollthroughbeimenisland.databinding.AppBarMainBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentCouponListBinding
import com.jotangi.strollthroughbeimenisland.utility.AppUtility
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.util.*

class CouponListFragment : BaseFragment(), CouponClickListener {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentCouponListBinding
    private var data = mutableListOf<CouponListVO>()
    private lateinit var couponAdapter: CouponListAdapter


    private var sid: String = ""
    private var couponType: String = ""
    private var status = "1" //tab標籤蘭

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCouponListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupCouponListTitle()
        initObserver()
        initView()
        initData()
        initAction()
    }

    private fun initObserver() {
        mainViewModel.exchangeableCouponData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                updateListView(result)
            } else {
                AppUtility.showPopDialog(
                    requireContext(),
                    null,
                    "目前沒有符合的優惠券唷"
                )
            }
        }

        mainViewModel.exchangedCouponData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                updateListView(result)
            } else {
                AppUtility.showPopDialog(
                    requireContext(),
                    null,
                    "目前沒有符合的優惠券唷"
                )
            }
        }
    }

    private fun initView() {
        initRecyclerView()
    }


    private fun initData() {
        mainViewModel.getMemberCouponList(
            requireContext(),
            AppUtility.getLoginId(requireContext())!!,
            AppUtility.getLoginPassword(requireContext())!!,
            sid,
            couponType
        )
    }

    private fun initAction() {
        binding.apply {

            tabLayout.addTab(tabLayout.newTab().setText("可使用"))
            tabLayout.addTab(tabLayout.newTab().setText("已使用/過期"))
            tabLayout.tabMode = TabLayout.MODE_FIXED
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when (Objects.requireNonNull(tab.text).toString()) {
                        "可使用" -> {
                            status = "1"
                            mainViewModel.getExchangeableCoupon()

                        }
                        "已使用/過期" -> {
                            status = "2"
                            mainViewModel.getExchangedCoupon()

                        }
                        else -> rvCoupon.adapter = null
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
    }

    private fun initRecyclerView() {

        binding.rvCoupon.apply {
            layoutManager = LinearLayoutManager(requireContext())
            couponAdapter = CouponListAdapter(
                data,
                requireContext(),
                this@CouponListFragment
            )
            this.adapter = couponAdapter
        }
    }

    override fun onCouponItemClick(vo: CouponListVO) {
        showCustomDialog(vo)
    }

    private fun showCustomDialog(result: CouponListVO) {
        val customDialog = Dialog(requireContext())

        customDialog.setContentView(R.layout.dialog_coupon)
        customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.setCanceledOnTouchOutside(true)

        val dialogTitleTextView: TextView =
            customDialog.findViewById(R.id.dialog_coupon_discount_title_textView)!!
        val dialogContentImageView: ImageView =
            customDialog.findViewById(R.id.dialog_coupon_discount_content_imageView)!!
        val dialogCouponDiscountStoreNameTextView: TextView =
            customDialog.findViewById(R.id.dialog_coupon_discount_storeName_textView)!!
        val dialogCouponDiscountContentTextView: TextView =
            customDialog.findViewById(R.id.dialog_coupon_discount_content_textView)!!
        val dialogDateTextView: TextView =
            customDialog.findViewById(R.id.dialog_coupon_discount_endDate_textView)!!
        val dialogConfirmButton =
            customDialog.findViewById<Button>(R.id.dialog_coupon_confirm_button)

        dialogConfirmButton.setOnClickListener {
            customDialog.dismiss()
        }

        if (result.couponName.isNotEmpty()) {
            dialogTitleTextView.text = result.couponName
        }

        if (result.storeName.isNotEmpty()) {
            dialogCouponDiscountStoreNameTextView.text = result.storeName
        }

        if (result.couponDescription.isNotEmpty()) {
            dialogCouponDiscountContentTextView.text = result.couponDescription
        }

        if (result.couponNo.isNotEmpty()) {
            val couponQRCodeImageBitmap = BarcodeEncoder().encodeBitmap(
                "coupon_no=${result.couponNo}",
                BarcodeFormat.QR_CODE,
                250,
                250
            )

            dialogContentImageView.setImageBitmap(couponQRCodeImageBitmap)
        }

        if (result.couponEnddate.isNotEmpty()) {
            dialogDateTextView.text = "使用期限：${result.couponStartdate + "~" + result.couponEnddate}"
        }

        customDialog.show()
    }

    private fun updateListView(result: List<CouponListVO>) {
        couponAdapter.updateDataSource(result)

    }
}