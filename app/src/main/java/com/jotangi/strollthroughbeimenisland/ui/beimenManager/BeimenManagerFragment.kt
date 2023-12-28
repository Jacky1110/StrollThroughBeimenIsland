package com.jotangi.strollthroughbeimenisland.ui.beimenManager

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.jotangi.strollthroughbeimenisland.BaseFragment
import com.jotangi.strollthroughbeimenisland.R
import com.jotangi.strollthroughbeimenisland.api.ApiConfig
import com.jotangi.strollthroughbeimenisland.databinding.AppBarMainBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentBeimenManagerBinding
import com.jotangi.strollthroughbeimenisland.ui.LogoutResponse
import com.jotangi.strollthroughbeimenisland.utility.AppUtility


class BeimenManagerFragment : BaseFragment() {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentBeimenManagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeimenManagerBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }


    private fun init() {
        setupBeimenManagerTitle()
        initObserver()
        initView()
    }

    private fun initObserver() {
        mainViewModel.logoutData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                updateMemberInfo(result)
                mainViewModel.clearData()
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

    private fun initView() {
        binding.apply {

            imageView.setOnClickListener {
                findNavController().navigate(R.id.action_beimenManagerFragment_to_scanCouponFragment)
            }

            logoutButton.setOnClickListener {

                mainViewModel.logout(requireContext())

            }
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

                    findNavController().navigate(R.id.action_beimenManagerFragment_to_nav_home)

                }
            }
        }

        alert.show()
    }
}