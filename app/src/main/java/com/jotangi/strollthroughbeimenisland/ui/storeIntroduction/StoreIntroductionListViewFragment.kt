package com.jotangi.strollthroughbeimenisland.ui.storeIntroduction

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.jotangi.strollthroughbeimenisland.BaseFragment
import com.jotangi.strollthroughbeimenisland.R
import com.jotangi.strollthroughbeimenisland.api.ApiConfig
import com.jotangi.strollthroughbeimenisland.databinding.AppBarMainBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentNavigationViewBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentStoreIntroductionListViewBinding


class StoreIntroductionListViewFragment : BaseFragment() {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentStoreIntroductionListViewBinding

    private lateinit var storeId: String

    private var state: Boolean? = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoreIntroductionListViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupStoreIntroductionListViewTitle()
        initObserver()
        initView()
        initData()
    }

    private fun initData() {
        storeId = arguments?.getString("storeId").toString()
        mainViewModel.getStoreList(
            requireContext(),
            storeId
        )
    }

    private fun initObserver() {
        mainViewModel.storeListData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                updateViewData(result)
            }
        }
    }

    private fun initView() {

        binding.apply {
            ivUp.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    ivUp.context,
                    R.drawable.ic_baseline_keyboard_arrow_down
                )
            )

            tvDescription2.post {

                tvDescription2.maxLines = 3 //超过10行就设置只能显示3行
                state = false

                tvDescription2.setOnClickListener {

                    when (state) {
                        false -> {

                            tvDescription2.maxLines = Int.MAX_VALUE //把TextView行数显示取消掉
                            tvOpen.text = "收合"
                            ivUp.setBackgroundDrawable(
                                ContextCompat.getDrawable(
                                    ivUp.context,
                                    R.drawable.ic_baseline_keyboard_arrow_up
                                )
                            )

                            state = true
                        }

                        true -> {

                            tvDescription2.maxLines = 3
                            tvOpen.text = "展開"
                            ivUp.setBackgroundDrawable(
                                ContextCompat.getDrawable(
                                    ivUp.context,
                                    R.drawable.ic_baseline_keyboard_arrow_down
                                )
                            )
                            state = false
                        }
                    }
                }

                tvOpen.setOnClickListener {

                    when (state) {
                        false -> {

                            tvDescription2.maxLines = Int.MAX_VALUE //把TextView行数显示取消掉
                            tvOpen.text = "收合"
                            ivUp.setBackgroundDrawable(
                                ContextCompat.getDrawable(
                                    ivUp.context,
                                    R.drawable.ic_baseline_keyboard_arrow_up
                                )
                            )

                            state = true
                        }

                        true -> {

                            tvDescription2.maxLines = 3
                            tvOpen.text = "展開"
                            ivUp.setBackgroundDrawable(
                                ContextCompat.getDrawable(
                                    ivUp.context,
                                    R.drawable.ic_baseline_keyboard_arrow_down
                                )
                            )
                            state = false
                        }
                    }
                }
            }
        }
    }

    private fun updateViewData(result: List<StoreListVO>) {
        binding.apply {

            storeName.text = result[0].storeName
            tvDescription2.text = result[0].storeDescription
            tvOpenTime.text = result[0].storeOpenTime
            tvInformation.text = result[0].storeOpen
            tvAttractionAddress.text = result[0].storeAddress
            tvContactNumber.text = result[0].storePhone
            Glide.with(root).load(ApiConfig.URL_HOST + result[0].storePicture).into(ivStorePic)

            if (result[0].storeLatitude.isNotBlank()) {
                ivNavigation.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        ivNavigation.context,
                        R.drawable.ic_navigation
                    )
                )

                tvNavigation.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.c_green
                    )
                )

                llNavigation.setOnClickListener {
                    val map =
                        "http://maps.google.com/maps?" + "daddr=" + result[0].storeLatitude + "," + result[0].storeLongitude
                    val uri = Uri.parse(map)
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                }

            } else {

                ivNavigation.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        ivNavigation.context,
                        R.drawable.ic_marker_grey
                    )
                )

                tvNavigation.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.c_gray
                    )
                )

                llNavigation.setOnClickListener {

                }
            }

            if (result[0].storePhone.isNotBlank()) {

                ivNaPhone.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        ivNaPhone.context,
                        R.drawable.ic_base_phone
                    )
                )

                tvNaPhone.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.c_green
                    )
                )

                llPhone.setOnClickListener {
                    val intent =
                        Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + result[0].storePhone))
                    startActivity(intent)
                }
            } else {

                ivNaPhone.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        ivNaPhone.context,
                        R.drawable.ic_phone_grey
                    )
                )

                tvNaPhone.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.c_gray
                    )
                )

                llPhone.setOnClickListener {

                }
            }

            if (result[0].storeWebsite.contains("https")) {

                ivWebsite.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        ivWebsite.context,
                        R.drawable.ic_website
                    )
                )

                tvWebsite.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.c_green
                    )
                )

                llWebsite.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(result[0].storeWebsite)
                    startActivity(intent)
                }

            } else {

                ivWebsite.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        ivWebsite.context,
                        R.drawable.ic_world_grey
                    )
                )

                tvWebsite.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.c_gray
                    )
                )

                llWebsite.setOnClickListener {

                }
            }
        }
    }

}