package com.jotangi.strollthroughbeimenisland.utility

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AlertDialog
import com.jotangi.strollthroughbeimenisland.api.ApiConfig
import com.jotangi.strollthroughbeimenisland.api.AppConfig

object AppUtility {

    private fun getSpref(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            AppConfig.spref,
            Context.MODE_PRIVATE
        )
    }

    fun updateLoginStatus(
        context: Context,
        isLogin: Boolean
    ) {
        getSpref(context).edit()
            .putBoolean(
                AppConfig.IS_LOGIN,
                isLogin
            )
            .apply()
    }

    fun updateLoginId(
        context: Context,
        loginId: String
    ) {
        getSpref(context).edit()
            .putString(
                AppConfig.LOGIN_ID,
                loginId
            )
            .apply()
    }

    fun updateLoginName(
        context: Context,
        loginName: String
    ) {
        getSpref(context).edit()
            .putString(
                AppConfig.LOGIN_NAME,
                loginName
            )
            .apply()
    }

    fun updateLoginPassword(
        context: Context,
        loginPw: String
    ) {
        getSpref(context).edit()
            .putString(
                AppConfig.LOGIN_PW,
                loginPw
            )
            .apply()
    }

    fun getLoginId(context: Context): String? {
        return getSpref(context).getString(
            AppConfig.LOGIN_ID,
            ""
        )
    }

    fun getLoginName(context: Context): String? {
        return getSpref(context).getString(
            AppConfig.LOGIN_NAME,
            ""
        )
    }

    fun getLoginPassword(context: Context): String? {
        return getSpref(context).getString(
            AppConfig.LOGIN_PW,
            ""
        )
    }

    fun getWriteOffCouponNo(context: Context): String? {
        return getSpref(context).getString(
            AppConfig.WRITE_OFF_COUPON_NO,
            ""
        )
    }

    fun updateWriteOffCouponNo(
        context: Context,
        couponNo: String
    ) {
        getSpref(context).edit()
            .putString(
                AppConfig.WRITE_OFF_COUPON_NO,
                couponNo
            )
            .commit()
    }


    fun showPopDialog(
        context: Context,
        code: String?,
        message: String?
    ) {
//        val serverMessage = when (code) {
//            ApiConfig.API_CODE_0x0201 -> "已註冊成功"
//            ApiConfig.API_CODE_0x0202 -> context.resources.getString(R.string.signup_response_202)
//            ApiConfig.API_CODE_0x0203 -> context.resources.getString(R.string.signup_response_203)
//            ApiConfig.API_CODE_0x0204 -> context.resources.getString(R.string.signup_response_204)
//            ApiConfig.API_CODE_0x0206 -> context.resources.getString(R.string.signup_response_206)
//            ApiConfig.API_CODE_NOT_FOUND -> context.resources.getString(R.string.public_response_404)
//            null -> message
//            else -> message
//        }

        val alert = AlertDialog.Builder(context)

        alert.setTitle("提醒")
        alert.setMessage(message)
        alert.setPositiveButton("確定") { _, _ ->

        }

        alert.show()
    }
}