package com.jotangi.strollthroughbeimenisland.ui.account

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
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
import com.jotangi.strollthroughbeimenisland.databinding.FragmentAccountForgetPasswordBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentAccountLoginBinding
import com.jotangi.strollthroughbeimenisland.ui.member.MemberBean
import com.jotangi.strollthroughbeimenisland.utility.AppUtility
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class AccountForgetPasswordFragment : BaseFragment() {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentAccountForgetPasswordBinding
    private var isEdit = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountForgetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupForgetPasswordTitle()
        initObserver()
        initAction()
    }

    private fun initObserver() {
        mainViewModel.forgetPwdData.observe(viewLifecycleOwner) { result ->
            if (isEdit) {
                if (result.code == ApiConfig.API_CODE_SUCCESS) {
                    showPrivateDialog(
                        result.responseMessage,
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
        }
    }

    private fun initAction() {
        binding.apply {
            forgetButton.setOnClickListener {
                isEdit = true
                forgetPassword()
            }

            forgetBirthdayEditText.setOnClickListener {
                showDatePickerDialog()
            }
        }
    }


    private fun forgetPassword() {
        binding.apply {
            if (forgetIdEditText.text.toString().isNullOrBlank()) {
                showPrivateDialog(
                    "帳號為必填！",
                    forgetIdEditText
                )

                return
            }

            if (forgetBirthdayEditText.text.toString().isNullOrBlank()) {
                showPrivateDialog(
                    "生日為必填！",
                    forgetBirthdayEditText
                )

                return
            }

            if (newPasswordEditText.text.toString().isNullOrBlank()) {
                showPrivateDialog(
                    "密碼為必填！",
                    newPasswordEditText
                )

                return
            }

            if (!isContainAll(newPasswordEditText.text.toString())) {
                showPrivateDialog(
                    "密碼格式不正確!",
                    newPasswordEditText
                )

                return
            }


            if (newPasswordEditText.text.toString() != checkNewPasswordEditText.text.toString()) {
                showPrivateDialog(
                    "密碼不一致!",
                    newPasswordEditText
                )

                return
            }

            mainViewModel.getUserForgotPwd(
                requireContext(),
                forgetIdEditText.text.toString(),
                MemberBean.birthday.toString(),
                newPasswordEditText.text.toString()
            )

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
                        binding.forgetBirthdayEditText.text = "${i}-${month}-${day}"
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
                        binding.forgetBirthdayEditText.text = "${i}-${month}-${day}"
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


    private fun showPrivateDialog(
        message: String,
        curUI: Any?
    ) {
        val alert = AlertDialog.Builder(requireContext())
        val title = "提醒！"

        alert.setTitle(title)
        alert.setMessage(message)
        alert.setPositiveButton("確定") { _, _ ->
            when (curUI) {
                binding.forgetIdEditText,
                binding.newPasswordEditText -> {
                    return@setPositiveButton
                }

                null -> {
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