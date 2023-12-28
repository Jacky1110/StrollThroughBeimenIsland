package com.jotangi.strollthroughbeimenisland.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.jotangi.strollthroughbeimenisland.BaseFragment
import com.jotangi.strollthroughbeimenisland.R
import com.jotangi.strollthroughbeimenisland.api.ApiConfig
import com.jotangi.strollthroughbeimenisland.api.AppConfig
import com.jotangi.strollthroughbeimenisland.databinding.AppBarMainBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentHomeBinding
import com.jotangi.strollthroughbeimenisland.ui.LogoutResponse
import com.jotangi.strollthroughbeimenisland.ui.member.MemberInfoVO
import com.jotangi.strollthroughbeimenisland.ui.storeIntroduction.StoreListVO
import com.jotangi.strollthroughbeimenisland.utility.AppUtility
import com.squareup.picasso.Picasso
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator

class HomeFragment : BaseFragment() {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentHomeBinding
    private var storeId: String = ""
    private var shopViewPagerAdapter: ShopViewPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupMainTitle()
        initObserver()
        initData()
        initAction()
        setBackView()
    }


    private fun initObserver() {
        mainViewModel.bannerData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                updateBanner(result)
            }
        }

        mainViewModel.storeListData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                updateListView(result)
            }
        }

        mainViewModel.logoutData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                updateMemberInfo(result)
                mainViewModel.clearData()
            }
        }

        mainViewModel.memberInfoData.observe(viewLifecycleOwner) { result ->
            if (result.isNotEmpty()) {
                updateViewData(result)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        binding.drawerLayout.closeDrawer(binding.drawerContent)

    }

    private fun setupMainTitle() {

        getToolBar().apply {
            toolTitleTextView.text = getString(R.string.main_fragment_title)
            toolImageButton.visibility = View.VISIBLE
            toolBackImageButton.visibility = View.GONE

            toolUseImageButton.setOnClickListener {

                binding.apply {

                    drawerLayout.openDrawer(drawerContent)

                }
            }

            toolImageButton.setOnClickListener {

                binding.apply {

                    drawerLayout.openDrawer(drawerContent)

                }
            }
        }
    }

    private fun updateBanner(result: List<BannerVO>) {

        binding.apply {
            mainBannerLoginBanner.setAdapter(object : BannerImageAdapter<BannerVO?>(result) {
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: BannerVO?,
                    position: Int,
                    size: Int
                ) {
                    if (holder != null) {
                        Glide.with(holder.itemView)
                            .load(ApiConfig.URL_HOST + data?.bannerPicture)
                            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                            .into(holder.imageView)

                        holder.imageView.setOnClickListener {
                            if (!data?.bannerLink.isNullOrEmpty()) {
                                openBannerLink(data!!.bannerLink)
                            }
                        }
                    }
                }
            })
                .addBannerLifecycleObserver(this@HomeFragment) //添加生命周期观察者
                .setIndicator(CircleIndicator(requireContext()))
        }
    }

    private fun openBannerLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)

        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun updateListView(result: List<StoreListVO>) {
        binding.apply {


            shopViewPagerAdapter = ShopViewPagerAdapter(context!!, result)
            vpChoose.clipToPadding = false
            vpChoose.clipChildren = false
            vpChoose.pageMargin = 20
            vpChoose.offscreenPageLimit = 3
            vpChoose.adapter = shopViewPagerAdapter

        }
    }

    private fun initData() {

        mainViewModel.getMainBannerData(requireContext())

        mainViewModel.getStoreList(
            requireContext(),
            storeId
        )

        if (!AppUtility.getLoginId(requireContext()).isNullOrBlank()) {
            mainViewModel.getMemberInfo(
                requireContext(),
                AppUtility.getLoginId(requireContext())!!,
                AppUtility.getLoginPassword(requireContext())!!,
                null
            )
        }
    }


    private fun initAction() {

        binding.apply {

            ivClear.setOnClickListener {

                drawerLayout.closeDrawer(drawerContent)
            }

            drawerContent.setOnClickListener {

                Log.d(TAG, "remind: ${"防止按到底層的跳轉"}")

            }

            if (AppUtility.getLoginId(requireContext()).isNullOrBlank()) {

                memberLoginConstraintLayout.visibility = View.VISIBLE
                memberLogoutConstraintLayout.visibility = View.GONE
                memberLoginConstraintLayout.setOnClickListener {
                    memberNameTextView.visibility = View.VISIBLE
                    findNavController().navigate(R.id.action_nav_home_to_nav_login)
                }


            } else {

                memberLoginConstraintLayout.visibility = View.GONE
                memberLogoutConstraintLayout.visibility = View.VISIBLE
            }

            memberLogoutConstraintLayout.setOnClickListener {
                mainViewModel.logout(requireContext())
            }

            memberDataConstraintLayout.setOnClickListener {
                if (AppUtility.getLoginId(requireContext()).isNullOrBlank()) {

                    showPrivateDialogs(
                        "請登入帳號",
                        null
                    )

                } else {

                    findNavController().navigate(R.id.action_nav_home_to_nav_member)
                }
            }

            memberCouponConstraintLayout.setOnClickListener {
                if (AppUtility.getLoginId(requireContext()).isNullOrBlank()) {

                    showPrivateDialogs(
                        "請登入帳號",
                        null
                    )

                } else {

                    findNavController().navigate(R.id.action_nav_home_to_nav_coupon)
                }
            }

            memberTermsConstraintLayout.setOnClickListener {

                findNavController().navigate(R.id.action_nav_home_to_nav_terms)
            }

            memberQuestionConstraintLayout.setOnClickListener {

                findNavController().navigate(R.id.action_nav_home_to_nav_question)
            }


            ivParking.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_stopCarFragment)
            }

            ivGuide.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_navigationShopFragment)
            }

            ivShop.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_storeIntroductionListFragment)
            }

            ivFood.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_northGateGoodFoodListFragment)
            }

            ivOnlineShop.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)

                intent.data = Uri.parse("https://shop.jotangi.net/jtgshop/shop.php")
                startActivity(intent)
            }

            ivHealthy.setOnClickListener {

                openApp(AppConfig.NUMBER_HEALTH)
            }
        }
    }

    private fun setBackView() {
        val dispatcher: OnBackPressedDispatcher?
        var callback: OnBackPressedCallback? = null

        dispatcher = requireActivity().onBackPressedDispatcher
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (true) {
                    activity?.runOnUiThread {
                        callback?.isEnabled = false
                        dispatcher.onBackPressed()
                    }
                } else {
                    Log.d(TAG, "失敗: ${"失敗"}")
                }

            }
        }
        dispatcher.addCallback(requireActivity(), callback)

    }


    private fun updateViewData(result: List<MemberInfoVO>) {
        binding.apply {
            memberNameTextView.text = result[0].memberName

            when (result[0].memberType) {

                "2" ->
                    findNavController().navigate(R.id.action_nav_home_to_beimenManagerFragment)
            }
        }
    }

    private fun updateMemberInfo(result: LogoutResponse) {
        if (result.code == ApiConfig.API_CODE_SUCCESS) {
            AppUtility.updateLoginStatus(
                requireContext(),
                false
            )

            AppUtility.updateLoginId(
                requireContext(),
                ""
            )

            AppUtility.updateLoginPassword(
                requireContext(),
                ""
            )

            AppUtility.updateWriteOffCouponNo(
                requireContext(),
                ""
            )

            showPrivateDialog(
//                result.responseMessage,
                "登出成功",
                null
            )
        } else {
            AppUtility.showPopDialog(
                requireContext(),
                result.code,
                result.responseMessage
            )
        }
    }

    private fun showPrivateDialog(
        message: String,
        curUI: Any?
    ) {
        val alert = AlertDialog.Builder(requireContext())
        val title = "提醒！"

        alert.setTitle(title)
        alert.setMessage(message)
        alert.setPositiveButton("確定") { _, _ ->
            if (AppUtility.getLoginId(requireContext()).isNullOrBlank()) {
                binding.apply {

                    drawerLayout.closeDrawer(drawerContent)
                    memberLoginConstraintLayout.visibility = View.VISIBLE
                    memberLogoutConstraintLayout.visibility = View.GONE
                    memberNameTextView.visibility = View.GONE
                    memberLoginConstraintLayout.setOnClickListener {
                        findNavController().navigate(R.id.action_nav_home_to_nav_login)
                    }
                }
            }
        }

        alert.show()
    }

    private fun showPrivateDialogs(
        message: String,
        curUI: Any?
    ) {
        val alert = AlertDialog.Builder(requireContext())
        val title = "提醒！"

        alert.setTitle(title)
        alert.setMessage(message)
        alert.setPositiveButton("確定") { _, _ ->

        }
        alert.show()
    }

    private fun openApp(appUri: String) {
        val launchIntent: Intent? =
            requireContext().packageManager.getLaunchIntentForPackage(appUri)

        if (launchIntent != null) {
            startActivity(launchIntent)
        } else {
            val mIntent = Intent(Intent.ACTION_VIEW)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            mIntent.data = Uri.parse("market://details?id=$appUri")
            requireContext().startActivity(mIntent)
        }
    }


    class ShopViewPagerAdapter(private val context: Context, shopList: List<StoreListVO>) :
        PagerAdapter() {
        private val shopList: List<StoreListVO>

        init {
            this.shopList = shopList
        }

        override fun getCount(): Int {
            return shopList.size
        }

        // 來判斷顯示的是否是同一張圖片，這裡我们將兩個參數相比較返回即可
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getPageWidth(position: Int): Float {
            return 0.5f
        }

        // 當要顯示的圖片可以進行緩存的時候，會調用這個方法進行顯示圖片的初始化，我们將要顯示的ImageView加入到ViewGroup中，然後作為返回值返回即可
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view: View =
                LayoutInflater.from(context)
                    .inflate(R.layout.item_home_store_list, container, false)
            val imageView: ImageView = view.findViewById(R.id.iv_home_store)
            val txtTitle = view.findViewById<TextView>(R.id.tv_home_store_name)
            val txtContent = view.findViewById<TextView>(R.id.tv_home_store_time)
            Picasso.get().load(ApiConfig.URL_HOST + shopList[position].storePicture)
                .into(imageView)

            txtTitle.text = shopList[position].storeName
            txtContent.text = shopList[position].storeOpenTime
            view.setOnClickListener { v ->

                val bundle = bundleOf("storeId" to shopList[position].storeId)

                findNavController(v).navigate(
                    R.id.action_nav_home_to_storeIntroductionListViewFragment,
                    bundle
                )

            }
            container.addView(view, 0) //要填0否則會報錯
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        super.destroyItem(container, position, object);
            container.removeView(`object` as View)
        }
    }
}