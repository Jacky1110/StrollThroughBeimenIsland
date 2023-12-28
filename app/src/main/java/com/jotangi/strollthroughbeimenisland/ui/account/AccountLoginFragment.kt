package com.jotangi.strollthroughbeimenisland.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.jotangi.strollthroughbeimenisland.BaseFragment
import com.jotangi.strollthroughbeimenisland.CoverPassword
import com.jotangi.strollthroughbeimenisland.R
import com.jotangi.strollthroughbeimenisland.api.ApiConfig
import com.jotangi.strollthroughbeimenisland.databinding.AppBarMainBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentAccountLoginBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentAccountRegisterBinding
import com.jotangi.strollthroughbeimenisland.ui.member.MemberInfoVO
import com.jotangi.strollthroughbeimenisland.utility.AppUtility
import java.util.regex.Matcher
import java.util.regex.Pattern


class AccountLoginFragment : BaseFragment() {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentAccountLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        setupLoginTitle()
        initAction()
        initObserver()
    }


    private fun initObserver() {
        mainViewModel.memberInfoData.observe(viewLifecycleOwner) { result ->
            if (result.isNotEmpty()) {
                updateMemberInfo(result)
            }
        }

        mainViewModel.loginData.observe(viewLifecycleOwner) { result ->
            if (result != null) {

                loginSucceed(result)

            }
        }
    }

    private fun updateMemberInfo(result: List<MemberInfoVO>) {

        AppUtility.updateLoginId(
            requireContext(),
            binding.loginIdEditText.text.toString()
        )

        AppUtility.updateLoginName(
            requireContext(),
            result[0].memberName
        )

        AppUtility.updateLoginPassword(
            requireContext(),
            binding.loginPasswordEditText.text.toString()
        )
    }

    private fun loginSucceed(result: LoginResponse) {
        if (result.code == ApiConfig.API_CODE_SUCCESS) {
            AppUtility.updateLoginStatus(
                requireContext(),
                true
            )
            showPrivateDialog(
                result.responseMessage,
                null
            )
        }

    }

    private fun initAction() {
        binding.apply {

            loginPasswordEditText.transformationMethod = CoverPassword()

            loginButton.setOnClickListener {
                login()
            }

            signupButton.setOnClickListener {
                findNavController().navigate(R.id.action_nav_login_to_accountRegisterFragment)
            }

            forgetPasswordTextView.setOnClickListener {
                findNavController().navigate(R.id.action_nav_login_to_accountForgetPasswordFragment)
            }
        }
    }

    private fun login() {
        if (binding.loginIdEditText.text.isNullOrEmpty()) {
            showPrivateDialog(
                "帳號為必填！",
                binding.loginIdEditText
            )

            return
        }

        if (binding.loginIdEditText.text.length != 10) {
            showPrivateDialog(
                "帳號或密碼格式不符，請重新輸入！",
                binding.loginIdEditText
            )

            return
        }

        if (binding.loginPasswordEditText.text.isNullOrEmpty()) {
            showPrivateDialog(
                "密碼為必填！",
                binding.loginPasswordEditText
            )

            return
        }

//        if (!isContainAll(binding.loginPasswordEditText.text.toString())) {
//            showPrivateDialog(
//                "帳號或密碼格式不符，請重新輸入！",
//                binding.loginPasswordEditText
//            )
//
//            return
//        }

        mainViewModel.login(
            requireContext(),
            binding.loginIdEditText.text.toString(),
            binding.loginPasswordEditText.text.toString()
        )
    }


    private fun showPrivateDialog(
        message: String,
        curUI: Any?
    ) {
        val alert = AlertDialog.Builder(requireContext())
        val title = if (curUI == null) {
            "恭喜您"
        } else {
            "提醒！"
        }

        alert.setTitle(title)
        alert.setMessage(message)
        alert.setPositiveButton("確定") { _, _ ->
            when (curUI) {
                binding.loginIdEditText,
                binding.loginPasswordEditText -> {
                    return@setPositiveButton
                }

                null -> {
                    when (message) {

                        "Member ogin success!"
                        ->
                            findNavController().navigate(R.id.action_to_main)

                        "Store login success!"
                        ->
                            findNavController().navigate(R.id.action_nav_login_to_beimenManagerFragment)
                    }
                }
            }
        }

        alert.show()
    }

    fun isContainAll(string: String?): Boolean {
        val regex = "^(?=[a-z])[0-9A-Za-z]{8,16}$"
        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(string)
        return matcher.matches()
    }
}