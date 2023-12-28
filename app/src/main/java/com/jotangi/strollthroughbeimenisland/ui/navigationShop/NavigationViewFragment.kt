package com.jotangi.strollthroughbeimenisland.ui.navigationShop

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.jotangi.strollthroughbeimenisland.BaseFragment
import com.jotangi.strollthroughbeimenisland.R
import com.jotangi.strollthroughbeimenisland.api.ApiConfig
import com.jotangi.strollthroughbeimenisland.databinding.AppBarMainBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentNavigationViewBinding

class NavigationViewFragment : BaseFragment() {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentNavigationViewBinding

    private lateinit var tourismId: String
    private lateinit var tourismPicture: String
    private var state: Boolean? = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNavigationViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupNavigationViewTitle()
        initObserver()
        initView()
        initData()
    }

    private fun initData() {
        tourismId = arguments?.getString("tourismId").toString()
        mainViewModel.getTourism(
            requireContext(),
            tourismId
        )
    }

    private fun initView() {

        binding.apply {
            ivUp.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    ivUp.context,
                    R.drawable.ic_baseline_keyboard_arrow_down
                )
            )

            tvDescription.post {

                tvDescription.maxLines = 3 //超过10行就设置只能显示3行

                tvDescription.setOnClickListener {


                    if (state == false) {

                        tvDescription.maxLines = 3

                        tvOpen.text = "展開"
                        ivUp.setBackgroundDrawable(
                            ContextCompat.getDrawable(
                                ivUp.context,
                                R.drawable.ic_baseline_keyboard_arrow_down
                            )
                        )

                        state = true


                    } else {

                        tvDescription.maxLines = Int.MAX_VALUE //把TextView行数显示取消掉
                        tvOpen.text = "收合"
                        ivUp.setBackgroundDrawable(
                            ContextCompat.getDrawable(
                                ivUp.context,
                                R.drawable.ic_baseline_keyboard_arrow_up
                            )
                        )

                        state = false
                    }
                }

                tvOpen.setOnClickListener {

                    if (state == false) {

                        tvDescription.maxLines = 3

                        tvOpen.text = "展開"
                        ivUp.setBackgroundDrawable(
                            ContextCompat.getDrawable(
                                ivUp.context,
                                R.drawable.ic_baseline_keyboard_arrow_down
                            )
                        )

                        state = true


                    } else {

                        tvDescription.maxLines = Int.MAX_VALUE //把TextView行数显示取消掉
                        tvOpen.text = "收合"
                        ivUp.setBackgroundDrawable(
                            ContextCompat.getDrawable(
                                ivUp.context,
                                R.drawable.ic_baseline_keyboard_arrow_up
                            )
                        )

                        state = false
                    }
                }
            }
        }
    }

    private fun initObserver() {
        mainViewModel.navigationData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                updateViewData(result)
            }
        }
    }

    private fun updateViewData(result: List<NavigationVO>) {
        binding.apply {
            tourismPicture = arguments?.getString("tourismPicture").toString()

            storeName.text = result[0].tourismName
            tvOpenTime.text = result[0].tourismOpenTime
            tvDescription.text = result[0].tourismDescription
            tvInformation.text = result[0].tourismTicketInfo
            tvAttractionAddress.text = result[0].tourismAddress
            tvContactNumber.text = result[0].tourismPhone
            tvTrafficInformation.text = result[0].tourismTraffic
            Glide.with(root).load(ApiConfig.URL_HOST + result[0].tourismPicture).into(ivStorePic)


            if (result[0].tourismlatitude.isNotBlank()) {

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
                        "http://maps.google.com/maps?" + "daddr=" + result[0].tourismlatitude + "," + result[0].tourismLongitude
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

            if (result[0].tourismPhone.isNotBlank()) {

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
                        Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + result[0].tourismPhone))
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


            if (result[0].tourismWebsite.contains("https")) {

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
                    intent.data = Uri.parse(result[0].tourismWebsite)
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