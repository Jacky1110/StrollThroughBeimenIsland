package com.jotangi.strollthroughbeimenisland.ui.member

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jotangi.strollthroughbeimenisland.BaseFragment
import com.jotangi.strollthroughbeimenisland.api.ApiConfig
import com.jotangi.strollthroughbeimenisland.databinding.AppBarMainBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentAccountRegisterBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentMemberBinding
import com.jotangi.strollthroughbeimenisland.utility.AppUtility
import java.util.regex.Matcher
import java.util.regex.Pattern

class MemberFragment : BaseFragment() {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentMemberBinding
    private var isEdit = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupMemberDataTitle()
        initObserver()
        initData()
        initAction()
    }

    private fun initAction() {
        binding.apply {

            memberGenderEditText.setOnClickListener {
                getGenderValue()
            }

            reviseButton.setOnClickListener {
                isEdit = true
                editMemberData()
            }
        }
    }

    private fun editMemberData() {
        binding.apply {

            if (memberPasswordEditText.text.toString().isNullOrEmpty()) {
                showPrivateDialog(
                    "密碼為必填！",
                    memberPasswordEditText
                )

                return
            }

            if (!isContainAll(memberPasswordEditText.text.toString())) {
                showPrivateDialog(
                    "密碼格式錯誤！",
                    memberPasswordEditText
                )

                return
            }

            if (memberPasswordEditText.text.toString() != memberNewPasswordEditText.text.toString()) {
                Log.d(
                    TAG,
                    "memberPasswordEditText: ${memberPasswordEditText.text.toString()} + ${memberNewPasswordEditText.text.toString()}"
                )
                showPrivateDialog(
                    "密碼不一致！",
                    memberNewPasswordEditText
                )

                return
            }

            mainViewModel.editMemberInfo(
                requireContext(),
                AppUtility.getLoginId(requireContext())!!,
                memberPasswordEditText.text.toString(),
                memberNameEditText.text.toString(),
                memberEmailEditText.text.toString(),
                MemberBean.gender.toString(),
            )
        }
    }

    private fun getGenderValue() {
        val aryOfString = arrayOf("男", "女", "不透漏")

        // 自定义对话框
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setSingleChoiceItems(
            aryOfString,
            0
        ) { dialog: DialogInterface, which: Int ->
            binding.memberGenderEditText.text = aryOfString[which]

            when (which) {
                0 ->
                    MemberBean.gender = "1"
                1 ->
                    MemberBean.gender = "2"
                2 ->
                    MemberBean.gender = "3"
            }
            dialog.dismiss() //随便点击一个item消失对话框，不用点击确认取消
            Log.d("TAG", "onClick: ${MemberBean.gender}")
            Log.d("TAG", "onClick1: " + aryOfString[which])
        }
        builder.show()
    }

    private fun initData() {
        mainViewModel.getMemberInfo(
            requireContext(),
            AppUtility.getLoginId(requireContext())!!,
            AppUtility.getLoginPassword(requireContext())!!,
            null
        )
    }

    private fun initObserver() {
        mainViewModel.memberInfoData.observe(viewLifecycleOwner) { result ->
            updateViewData(result)
        }

        mainViewModel.memberInfoEditData.observe(viewLifecycleOwner) { result ->
            if (isEdit == true) {
                updateViewEditData(result)
            }
        }
    }

    private fun updateViewEditData(result: MemberInfoEditResponse) {
        if (result.code == ApiConfig.API_CODE_SUCCESS) {
            AppUtility.updateLoginName(
                requireContext(),
                binding.memberNameEditText.text.toString()
            )

            AppUtility.updateLoginPassword(
                requireContext(),
                binding.memberPasswordEditText.text.toString()
            )

            showPrivateDialog(
                result.responseMessage,
                null
            )
        }
    }


    private fun updateViewData(result: List<MemberInfoVO>) {
        binding.apply {

            memberIdEditText.text = result[0].memberId + "(無法修改)"
            memberNameEditText.setText(result[0].memberName)
            memberEmailEditText.setText(result[0].memberEmail)

            if (!result[0].memberGender.isNullOrBlank()) {

                when (result[0].memberGender) {

                    "1" ->
                        memberGenderEditText.text = "男"
                    "2" ->
                        memberGenderEditText.text = "女"
                    "3" ->
                        memberGenderEditText.text = "不透漏"


                }
            }

            memberBirthdayEditText.text = result[0].memberBirthday + "(無法修改)"
        }
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
                binding.memberPasswordEditText -> {
                    return@setPositiveButton
                }

                binding.memberNewPasswordEditText -> {
                    return@setPositiveButton
                }
                else -> {
                    onBackPressed()
                }
            }
        }

        alert.show()
    }

    fun isContainAll(string: String?): Boolean {
        val regex = "[0-9A-Za-z]{8,16}$"
        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(string)
        return matcher.matches()
    }
}