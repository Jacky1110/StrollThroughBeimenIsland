package com.jotangi.strollthroughbeimenisland.ui.account

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.jotangi.strollthroughbeimenisland.BaseFragment
import com.jotangi.strollthroughbeimenisland.R
import com.jotangi.strollthroughbeimenisland.SpinnerDatePickerDialog
import com.jotangi.strollthroughbeimenisland.ViewUtils
import com.jotangi.strollthroughbeimenisland.api.ApiConfig
import com.jotangi.strollthroughbeimenisland.databinding.AppBarMainBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentAccountRegisterBinding
import com.jotangi.strollthroughbeimenisland.model.SignupResponse
import com.jotangi.strollthroughbeimenisland.ui.member.MemberBean
import com.jotangi.strollthroughbeimenisland.utility.AppUtility
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class AccountRegisterFragment : BaseFragment() {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentAccountRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        setupSignupTitle()
        initObserver()
        initAction()
    }

    private fun initObserver() {
        mainViewModel.signupData.observe(viewLifecycleOwner) { result ->
            updateMemberInfo(result)
        }
    }

    private fun initAction() {
        binding.apply {
            loginButton.setOnClickListener {
                signup()
            }

            signupCheckbox.setOnCheckedChangeListener { _, _ -> }

            registerGenderEditText.setOnClickListener {
                getGenderValue()
            }

            registerBirthdayEditText.setOnClickListener {
                showDatePickerDialog()
            }
        }

    }

    private fun showDatePickerDialog() {
        binding.apply {


            val calendar = Calendar.getInstance()
            val datePickerDialog: DatePickerDialog

            if (Build.VERSION.SDK_INT != Build.VERSION_CODES.N) {
                datePickerDialog = SpinnerDatePickerDialog(
                    requireActivity(),
                    R.style.DatePickerTheme,
                    DatePickerDialog.OnDateSetListener { datePicker, i, i1, i2 ->

                        Log.d(TAG, "SpinnerDate: $i $i1 $i2")
                        var month = (i1 + 1).toString()
                        if (month.length < 2) {
                            month = "0$month"
                        }
                        var day = i2.toString()
                        if (day.length < 2) {
                            day = "0$day"
                        }
                        binding.registerBirthdayEditText.text = "${i}-${month}-${day}"
                        MemberBean.birthday = "${i}-${month}-${day}"

                    },
                    calendar[Calendar.YEAR],
                    calendar[Calendar.MONTH],
                    calendar[Calendar.DAY_OF_MONTH]
                )
            } else {
                datePickerDialog = DatePickerDialog(
                    requireActivity(),
                    R.style.DatePickerTheme,
                    DatePickerDialog.OnDateSetListener { datePicker, i, i1, i2 ->

                        Log.d(TAG, "date: $i $i1 $i2")
                        var month = (i1 + 1).toString()
                        if (month.length < 2) {
                            month = "0$month"
                        }
                        var day = i2.toString()
                        if (day.length < 2) {
                            day = "0$day"
                        }
                        binding.registerBirthdayEditText.text = "${i}-${month}-${day}"
                        MemberBean.birthday = "${i}-${month}-${day}"

                    },
                    calendar[Calendar.YEAR],
                    calendar[Calendar.MONTH],
                    calendar[Calendar.DAY_OF_MONTH]
                )
            }
            ViewUtils.colorizeDatePicker(datePickerDialog.datePicker)
            datePickerDialog.datePicker.spinnersShown = true
            datePickerDialog.datePicker.calendarViewShown = false
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.show()


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
            binding.registerGenderEditText.setText(aryOfString[which])

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

    private fun signup() {

        binding.apply {

            if (registerIdEditText.text.toString().isNullOrEmpty()) {
                showPrivateDialog(
                    "電話為必填！",
                    registerIdEditText
                )

                return
            }

            if (!registerIdEditText.text.toString()
                    .startsWith("09") && registerIdEditText.text.toString().length != 10
            ) {
                showPrivateDialog(
                    "電話號碼格式錯誤",
                    registerIdEditText
                )
            }


            if (registerPasswordEditText.text.toString().isNullOrEmpty()) {
                showPrivateDialog(
                    "密碼為必填！",
                    registerPasswordEditText
                )

                return
            }

            if (!isContainAll(binding.registerPasswordEditText.text.toString())) {
                showPrivateDialog(
                    "密碼格式不符，請重新輸入！",
                    binding.registerPasswordEditText
                )

                return
            }

            if (registerCheckPasswordEditText.text.toString() != registerPasswordEditText.text.toString()) {
                showPrivateDialog(
                    "與密碼不相符",
                    registerCheckPasswordEditText
                )

                return
            }

            if (registerNameEditText.text.toString().isNullOrEmpty()) {
                showPrivateDialog(
                    "姓名為必填！",
                    registerNameEditText
                )

                return
            }


            if (registerBirthdayEditText.text.toString().isNullOrEmpty()) {
                showPrivateDialog(
                    "生日為必選！",
                    registerBirthdayEditText
                )

                return
            }

            if (registerGenderEditText.text.toString().isNullOrEmpty()) {
                showPrivateDialog(
                    "性別為必選！",
                    registerGenderEditText
                )

                return
            }

            if (!signupCheckbox.isChecked) {
                showPrivateDialog(
                    "請同意條款！",
                    binding.registerPasswordEditText
                )

                return
            }


            mainViewModel.signup(
                requireContext(),
                registerIdEditText.text.toString(),
                registerPasswordEditText.text.toString(),
                registerNameEditText.text.toString(),
                registerEmailEditText.text.toString(),
                MemberBean.gender.toString(),
                MemberBean.birthday.toString(),
                registerReferralEditText.text.toString()
            )
        }
    }

    private fun updateMemberInfo(result: SignupResponse?) {
        if (result?.code == ApiConfig.API_CODE_SUCCESS) {
            AppUtility.updateLoginStatus(
                requireContext(),
                true
            )

            AppUtility.updateLoginId(
                requireContext(),
                binding.registerIdEditText.text.toString()
            )

            AppUtility.updateLoginName(
                requireContext(),
                binding.registerNameEditText.text.toString()
            )

            AppUtility.updateLoginPassword(
                requireContext(),
                binding.registerPasswordEditText.text.toString()
            )

            showPrivateDialog(
                result.responseMessage,
                null
            )

        } else {
            AppUtility.showPopDialog(
                requireContext(),
                result?.code,
                result?.responseMessage
            )
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
                binding.registerIdEditText,
                binding.registerPasswordEditText,
                binding.signupCheckbox -> {
                    return@setPositiveButton
                }

                null -> {

                    findNavController().navigate(R.id.action_accountRegisterFragment_to_nav_home)

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