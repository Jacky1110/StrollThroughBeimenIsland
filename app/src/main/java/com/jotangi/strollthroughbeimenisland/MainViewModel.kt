package com.jotangi.strollthroughbeimenisland

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jotangi.strollthroughbeimenisland.api.ApiConfig
import com.jotangi.strollthroughbeimenisland.model.SignupResponse
import com.jotangi.strollthroughbeimenisland.ui.LogoutResponse
import com.jotangi.strollthroughbeimenisland.ui.account.ForgetPwdResponse
import com.jotangi.strollthroughbeimenisland.ui.parking.ParkingVO
import com.jotangi.strollthroughbeimenisland.ui.account.LoginResponse
import com.jotangi.strollthroughbeimenisland.ui.beimenManager.WriteOffTicketResponse
import com.jotangi.strollthroughbeimenisland.ui.goodFood.GoodFoodListVO
import com.jotangi.strollthroughbeimenisland.ui.home.BannerVO
import com.jotangi.strollthroughbeimenisland.ui.member.MemberInfoEditResponse
import com.jotangi.strollthroughbeimenisland.ui.member.MemberInfoVO
import com.jotangi.strollthroughbeimenisland.ui.navigationShop.NavigationVO
import com.jotangi.strollthroughbeimenisland.ui.memberCoupon.CouponListVO
import com.jotangi.strollthroughbeimenisland.ui.storeIntroduction.StoreListVO
import com.jotangi.strollthroughbeimenisland.utility.ApiUtility
import com.jotangi.strollthroughbeimenisland.utility.AppUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    //banner
    private val _bannerData: MutableLiveData<List<BannerVO>> by lazy {
        MutableLiveData<List<BannerVO>>()
    }

    val bannerData: LiveData<List<BannerVO>> get() = _bannerData

    // region member
    private val _signupData: MutableLiveData<SignupResponse> by lazy {
        MutableLiveData<SignupResponse>()
    }

    val signupData: LiveData<SignupResponse> get() = _signupData

    //登入
    private val _loginData: MutableLiveData<LoginResponse> by lazy {
        MutableLiveData<LoginResponse>()
    }
    val loginData: LiveData<LoginResponse> get() = _loginData

    //會員忘記密碼
    private val _forgetPwdData: MutableLiveData<ForgetPwdResponse> by lazy {
        MutableLiveData<ForgetPwdResponse>()
    }
    val forgetPwdData: LiveData<ForgetPwdResponse> get() = _forgetPwdData

    //會員資料
    private val _memberInfoData: MutableLiveData<List<MemberInfoVO>> by lazy {
        MutableLiveData<List<MemberInfoVO>>()
    }
    val memberInfoData: LiveData<List<MemberInfoVO>> get() = _memberInfoData

    //修改會員資料
    private val _memberInfoEditData: MutableLiveData<MemberInfoEditResponse> by lazy {
        MutableLiveData<MemberInfoEditResponse>()
    }
    val memberInfoEditData: LiveData<MemberInfoEditResponse> get() = _memberInfoEditData

    //停車資料
    private val _parkingData: MutableLiveData<List<ParkingVO>> by lazy {
        MutableLiveData<List<ParkingVO>>()
    }

    val parkingData: LiveData<List<ParkingVO>> get() = _parkingData

    //數位導覽資料
    private val _navigationData: MutableLiveData<List<NavigationVO>> by lazy {
        MutableLiveData<List<NavigationVO>>()
    }
    val navigationData: LiveData<List<NavigationVO>> get() = _navigationData


    //店家介紹
    private val _storeListData: MutableLiveData<List<StoreListVO>> by lazy {
        MutableLiveData<List<StoreListVO>>()
    }
    val storeListData: LiveData<List<StoreListVO>> get() = _storeListData

    //北門好食資料
    private val _goodFoodListData: MutableLiveData<List<GoodFoodListVO>> by lazy {
        MutableLiveData<List<GoodFoodListVO>>()
    }
    val goodFoodListData: LiveData<List<GoodFoodListVO>> get() = _goodFoodListData

    //登出
    private val _logoutData: MutableLiveData<LogoutResponse> by lazy {
        MutableLiveData<LogoutResponse>()
    }
    val logoutData: LiveData<LogoutResponse> get() = _logoutData

    //會員優惠券
    private val _couponListData: MutableLiveData<List<CouponListVO>> by lazy {
        MutableLiveData<List<CouponListVO>>()
    }

    //可使用
    private val _exchangeableCouponData: MutableLiveData<List<CouponListVO>> by lazy {
        MutableLiveData<List<CouponListVO>>()
    }
    val exchangeableCouponData: LiveData<List<CouponListVO>> get() = _exchangeableCouponData

    //使用過
    private val _exchangedCouponData: MutableLiveData<List<CouponListVO>> by lazy {
        MutableLiveData<List<CouponListVO>>()
    }
    val exchangedCouponData: LiveData<List<CouponListVO>> get() = _exchangedCouponData

    // region coupon
    private val _applyCouponData: MutableLiveData<WriteOffTicketResponse> by lazy {
        MutableLiveData<WriteOffTicketResponse>()
    }
    val applyCouponData: LiveData<WriteOffTicketResponse> get() = _applyCouponData


    fun clearData() {
        _loginData.value = null
        _memberInfoData.value = listOf()
        _memberInfoEditData.value = null
        _logoutData.value = null
    }


    fun getMainBannerData(
        context: Context
    ) {
        val call: Call<List<BannerVO>> = ApiUtility.service.apiGetBannerList()

        call.enqueue(object : Callback<List<BannerVO>> {

            override fun onResponse(
                call: Call<List<BannerVO>>,
                response: Response<List<BannerVO>>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    _bannerData.value = response.body()
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<List<BannerVO>>, t: Throwable) {
                _bannerData.value = null
            }
        })
    }


    fun signup(
        context: Context,
        memberId: String,
        memberPwd: String,
        memberName: String,
        memberEmail: String,
        memberGender: String,
        memberBirthday: String,
        recommendCode: String
    ) {
        val call: Call<SignupResponse> = ApiUtility.service.apiSignup(
            memberId,
            memberPwd,
            memberName,
            memberEmail,
            memberGender,
            memberBirthday,
            recommendCode
        )
        call.enqueue(object : Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    _signupData.value = response.body()
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                AppUtility.showPopDialog(
                    context,
                    null,
                    ApiUtility.apiFailureMessage(call, t)
                )
            }
        })
    }

    fun login(
        context: Context,
        memberId: String,
        memberPassword: String
    ) {
        val call: Call<LoginResponse> = ApiUtility.service.apiLogin(
            memberId,
            memberPassword,
            ""
        )
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    if (
                        response.body()!!.code == ApiConfig.API_CODE_0x0201 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0202 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0203 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0204 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0206
                    ) {
                        AppUtility.showPopDialog(
                            context,
                            response.body()!!.code,
                            response.body()!!.responseMessage
                        )
                    } else {
                        getMemberInfo(
                            context,
                            memberId,
                            memberPassword,
                            response.body()
                        )
                    }
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                AppUtility.showPopDialog(
                    context,
                    null,
                    ApiUtility.apiFailureMessage(call, t)
                )
            }
        })
    }

    fun getMemberInfo(
        context: Context,
        memberId: String,
        memberPassword: String,
        loginResult: LoginResponse?
    ) {
        val call: Call<List<MemberInfoVO>> = ApiUtility.service.apiGetMemberInfo(
            memberId,
            memberPassword
        )
        call.enqueue(object : Callback<List<MemberInfoVO>> {
            override fun onResponse(
                call: Call<List<MemberInfoVO>>,
                response: Response<List<MemberInfoVO>>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    _memberInfoData.value = response.body()

                    if (loginResult != null) {
                        _loginData.value = loginResult
                    }
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<List<MemberInfoVO>>, t: Throwable) {
                AppUtility.showPopDialog(
                    context,
                    null,
                    ApiUtility.apiFailureMessage(call, t)
                )
            }
        })
    }

    fun getParking(
        context: Context
    ) {
        val call: Call<List<ParkingVO>> = ApiUtility.service.apiGetParking()
        call.enqueue(object : Callback<List<ParkingVO>> {
            override fun onResponse(
                call: Call<List<ParkingVO>>,
                response: Response<List<ParkingVO>>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    _parkingData.value = response.body()
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<List<ParkingVO>>, t: Throwable) {
                _parkingData.value = null
//                AppUtility.showPopDialog(
//                    context,
//                    null,
//                    ApiUtility.apiFailureMessage(call, t)
//                )
            }
        })
    }

    fun getTourism(
        context: Context,
        tourismId: String
    ) {
        val call: Call<List<NavigationVO>> = ApiUtility.service.apiGetTourism(
            tourismId
        )
        call.enqueue(object : Callback<List<NavigationVO>> {
            override fun onResponse(
                call: Call<List<NavigationVO>>,
                response: Response<List<NavigationVO>>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    _navigationData.value = response.body()
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<List<NavigationVO>>, t: Throwable) {
                _navigationData.value = null
//                AppUtility.showPopDialog(
//                    context,
//                    null,
//                    ApiUtility.apiFailureMessage(call, t)
//                )
            }
        })
    }

    fun getStoreList(
        context: Context,
        storeId: String
    ) {
        val call: Call<List<StoreListVO>> = ApiUtility.service.apiGetStoreList(
            storeId
        )
        call.enqueue(object : Callback<List<StoreListVO>> {
            override fun onResponse(
                call: Call<List<StoreListVO>>,
                response: Response<List<StoreListVO>>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    _storeListData.value = response.body()
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<List<StoreListVO>>, t: Throwable) {
                _storeListData.value = null
//                AppUtility.showPopDialog(
//                    context,
//                    null,
//                    ApiUtility.apiFailureMessage(call, t)
//                )
            }
        })
    }

    fun getFoodList(
        context: Context,
        foodId: String
    ) {
        val call: Call<List<GoodFoodListVO>> = ApiUtility.service.apiGetFoodList(
            foodId
        )
        call.enqueue(object : Callback<List<GoodFoodListVO>> {
            override fun onResponse(
                call: Call<List<GoodFoodListVO>>,
                response: Response<List<GoodFoodListVO>>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    _goodFoodListData.value = response.body()
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<List<GoodFoodListVO>>, t: Throwable) {
                _goodFoodListData.value = null
//                AppUtility.showPopDialog(
//                    context,
//                    null,
//                    ApiUtility.apiFailureMessage(call, t)
//                )
            }
        })
    }

    fun editMemberInfo(
        context: Context,
        memberId: String,
        memberPwd: String,
        memberName: String,
        memberEmail: String,
        memberGender: String
    ) {
        val call: Call<MemberInfoEditResponse> = ApiUtility.service.apiEditMemberInfo(
            memberId,
            memberPwd,
            memberName,
            memberEmail,
            memberGender
        )
        call.enqueue(object : Callback<MemberInfoEditResponse> {
            override fun onResponse(
                call: Call<MemberInfoEditResponse>,
                response: Response<MemberInfoEditResponse>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    if (
                        response.body()!!.code == ApiConfig.API_CODE_0x0201 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0202 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0203 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0204 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0206
                    ) {
                        AppUtility.showPopDialog(
                            context,
                            response.body()!!.code,
                            response.body()!!.responseMessage
                        )
                    } else {
                        _memberInfoEditData.value = response.body()
                    }
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<MemberInfoEditResponse>, t: Throwable) {
                AppUtility.showPopDialog(
                    context,
                    null,
                    ApiUtility.apiFailureMessage(call, t)
                )
            }
        })
    }

    fun getUserForgotPwd(
        context: Context,
        memberId: String,
        memberBirthday: String,
        newPassword: String
    ) {
        val call: Call<ForgetPwdResponse> = ApiUtility.service.apiForgotPwd(
            memberId,
            memberBirthday,
            newPassword
        )
        call.enqueue(object : Callback<ForgetPwdResponse> {
            override fun onResponse(
                call: Call<ForgetPwdResponse>,
                response: Response<ForgetPwdResponse>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    _forgetPwdData.value = response.body()
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<ForgetPwdResponse>, t: Throwable) {
                AppUtility.showPopDialog(
                    context,
                    null,
                    ApiUtility.apiFailureMessage(call, t)
                )
            }
        })
    }

    fun logout(context: Context) {
        val call: Call<LogoutResponse> = ApiUtility.service.apiLogout(
            AppUtility.getLoginId(context)!!,
            AppUtility.getLoginPassword(context)!!
        )
        call.enqueue(object : Callback<LogoutResponse> {
            override fun onResponse(
                call: Call<LogoutResponse>,
                response: Response<LogoutResponse>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    if (
                        response.body()!!.code == ApiConfig.API_CODE_0x0201 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0202 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0203 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0204 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0206
                    ) {
                        AppUtility.showPopDialog(
                            context,
                            response.body()!!.code,
                            response.body()!!.responseMessage
                        )
                    } else {
                        _logoutData.value = response.body()
                    }
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                AppUtility.showPopDialog(
                    context,
                    null,
                    ApiUtility.apiFailureMessage(call, t)
                )
            }
        })
    }

    fun getMemberCouponList(
        context: Context,
        memberId: String,
        memberPwd: String,
        sid: String,
        couponType: String,
    ) {
        val call: Call<List<CouponListVO>> = ApiUtility.service.apiMemberCouponList(
            memberId,
            memberPwd,
            sid,
            couponType
        )
        call.enqueue(object : Callback<List<CouponListVO>> {
            override fun onResponse(
                call: Call<List<CouponListVO>>,
                response: Response<List<CouponListVO>>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    _couponListData.value = response.body()
                    getExchangeableCoupon()
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<List<CouponListVO>>, t: Throwable) {
                _couponListData.value = null
                getExchangeableCoupon()
//                AppUtility.showPopDialog(
//                    context,
//                    null,
//                    ApiUtility.apiFailureMessage(call, t)
//                )
            }
        })
    }

    fun getExchangeableCoupon() {
        if (_couponListData.value != null) {
            _exchangeableCouponData.value = _couponListData.value?.filter { it.usingFlag == "0" }
        } else {
            _exchangeableCouponData.value = null
        }
    }

    fun getExchangedCoupon() {
        if (_couponListData.value != null) {
            _exchangedCouponData.value = _couponListData.value?.filter { it.usingFlag == "1" }
        } else {
            _exchangedCouponData.value = null
        }
    }

    fun reimburseCoupon(
        context: Context,
        memberId: String,
        memberPassword: String,
        CouponNo: String
    ) {
        val call: Call<WriteOffTicketResponse> = ApiUtility.service.apiManagerWriteOffTicket(
            memberId,
            memberPassword,
            CouponNo
        )
        call.enqueue(object : Callback<WriteOffTicketResponse> {
            override fun onResponse(
                call: Call<WriteOffTicketResponse>,
                response: Response<WriteOffTicketResponse>
            ) {
                val statusCode = response.code()
                val url = response.raw().request.url.toString()
                Log.d("目前 status code & URL 是", "\n" + statusCode + "\n" + url)

                if (response.body() != null) {
                    if (
                        response.body()!!.code == ApiConfig.API_CODE_0x0201 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0202 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0203 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0204 ||
                        response.body()!!.code == ApiConfig.API_CODE_0x0206
                    ) {
                        AppUtility.showPopDialog(
                            context,
                            statusCode.toString(),
                            "核銷失敗"
                        )
                    } else {

                        _applyCouponData.value = response.body()
                        AppUtility.showPopDialog(
                            context,
                            statusCode.toString(),
                            "核銷成功"
                        )
                    }
                    Log.d("TAG", "onFailure: ${"核銷成功"}")
                } else {
                    AppUtility.showPopDialog(
                        context,
                        statusCode.toString(),
                        null
                    )
                }
            }

            override fun onFailure(call: Call<WriteOffTicketResponse>, t: Throwable) {
                AppUtility.showPopDialog(
                    context,
                    null,
                    ApiUtility.apiFailureMessage(call, t)
                )
                Log.d("TAG", "onFailure: ${"核銷失敗"}")
                _applyCouponData.value = null
            }
        })
    }

}