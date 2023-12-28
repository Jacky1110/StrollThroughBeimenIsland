package com.jotangi.strollthroughbeimenisland.ui.goodFood

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.jotangi.strollthroughbeimenisland.BaseFragment
import com.jotangi.strollthroughbeimenisland.R
import com.jotangi.strollthroughbeimenisland.api.ApiConfig
import com.jotangi.strollthroughbeimenisland.databinding.AppBarMainBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentNorthGateGoodFoodListViewBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentStoreIntroductionListViewBinding


class NorthGateGoodFoodListViewFragment : BaseFragment() {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentNorthGateGoodFoodListViewBinding

    private lateinit var foodId: String

    private var state: Boolean? = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNorthGateGoodFoodListViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupGoodFoodListViewTitle()
        initObserver()
        initView()
        initData()
    }

    private fun initData() {
        foodId = arguments?.getString("foodId").toString()
        mainViewModel.getFoodList(
            requireContext(),
            foodId
        )
    }

    private fun initObserver() {
        mainViewModel.goodFoodListData.observe(viewLifecycleOwner) { result ->
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

            tvDescription.post {

                tvDescription.maxLines = 3 //超过10行就设置只能显示10行
                state = false

                tvDescription.setOnClickListener {

                    if (state == false) {
                        tvDescription.maxLines = Int.MAX_VALUE //把TextView行数显示取消掉
                        tvOpen.text = "收合"
                        ivUp.setBackgroundDrawable(
                            ContextCompat.getDrawable(
                                ivUp.context,
                                R.drawable.ic_baseline_keyboard_arrow_up
                            )
                        )

                        state = true
                    } else if (state == true) {
                        tvDescription.maxLines = 3
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

    private fun updateViewData(result: List<GoodFoodListVO>) {
        binding.apply {
            storeName.text = result[0].foodName
            tvDescription.text = result[0].foodDescription
            tvInstructions.text = result[0].foodOrderDescription
            Glide.with(root).load(ApiConfig.URL_HOST + result[0].foodPicture).into(ivStorePic)

            Log.d(TAG, "foodPhone: ${result[0].foodPhone}")

            if (result[0].storeId.isNotBlank()) {
                ivFoodStore.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        ivNaPhone.context,
                        R.drawable.ic_food_store
                    )
                )

                tvFoodStore.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.c_green
                    )
                )

                llNavigation.setOnClickListener {

                    val bundle = bundleOf("storeId" to result[0].storeId)

                    findNavController().navigate(
                        R.id.action_northGateGoodFoodListViewFragment_to_storeIntroductionListViewFragment,
                        bundle
                    )
                }
            } else {

                ivFoodStore.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        ivNaPhone.context,
                        R.drawable.ic_home_grey
                    )
                )

                tvFoodStore.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.c_gray
                    )
                )
                llNavigation.setOnClickListener {

                }
            }

            if (result[0].foodPhone.isNotBlank()) {

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
                        Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + result[0].foodPhone))
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
            }

            if (result[0].foodWebsite.contains("https")) {

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
                    intent.data = Uri.parse(result[0].foodWebsite)
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
            }
        }
    }
}