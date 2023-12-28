package com.jotangi.strollthroughbeimenisland.api

import com.jotangi.strollthroughbeimenisland.model.SignupResponse
import com.jotangi.strollthroughbeimenisland.ui.LogoutResponse
import com.jotangi.strollthroughbeimenisland.ui.account.ForgetPwdResponse
import com.jotangi.strollthroughbeimenisland.ui.account.LoginResponse
import com.jotangi.strollthroughbeimenisland.ui.beimenManager.WriteOffTicketResponse
import com.jotangi.strollthroughbeimenisland.ui.goodFood.GoodFoodListVO
import com.jotangi.strollthroughbeimenisland.ui.home.BannerVO
import com.jotangi.strollthroughbeimenisland.ui.member.MemberInfoEditResponse
import com.jotangi.strollthroughbeimenisland.ui.member.MemberInfoVO
import com.jotangi.strollthroughbeimenisland.ui.navigationShop.NavigationVO
import com.jotangi.strollthroughbeimenisland.ui.parking.ParkingVO
import com.jotangi.strollthroughbeimenisland.ui.memberCoupon.CouponListVO
import com.jotangi.strollthroughbeimenisland.ui.storeIntroduction.StoreListVO
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiConfig {

    companion object {

        // 正式機
        const val URL_HOST = "https://bmtraveling.jotangi.net/beimen/"
        const val API_CODE_SUCCESS = "0x0200"
        const val API_CODE_0x0201 = "0x0201"
        const val API_CODE_0x0202 = "0x0202"
        const val API_CODE_0x0203 = "0x0203"
        const val API_CODE_0x0204 = "0x0204"
        const val API_CODE_0x0206 = "0x0206"
        const val API_CODE_NOT_FOUND = "404"

    }

    // 取得 banner
    @POST("api/banner_list.php")
    fun apiGetBannerList(): Call<List<BannerVO>>


    // 註冊
    @POST("api/user_register.php")
    @FormUrlEncoded
    fun apiSignup(
        @Field("member_id") memberId: String,
        @Field("member_pwd") memberPwd: String,
        @Field("member_name") memberName: String,
        @Field("member_email") memberEmail: String,
        @Field("member_gender") memberGender: String,
        @Field("member_birthday") memberBirthday: String,
        @Field("recommend_code") recommendCode: String
    ): Call<SignupResponse>

    // 登入
    @POST("api/user_login.php")
    @FormUrlEncoded
    fun apiLogin(
        @Field("member_id") memberId: String,
        @Field("member_pwd") memberPwd: String,
        @Field("FCM_Token") fcmToken: String
    ): Call<LoginResponse>

    // 會員忘記密碼
    @POST("api/user_forgotpwd.php")
    @FormUrlEncoded
    fun apiForgotPwd(
        @Field("member_id") memberId: String,
        @Field("member_birthday") memberBirthday: String,
        @Field("new_password") newPassword: String
    ): Call<ForgetPwdResponse>

    // 取得會員資料
    @POST("api/member_info.php")
    @FormUrlEncoded
    fun apiGetMemberInfo(
        @Field("member_id") memberId: String,
        @Field("member_pwd") memberPwd: String
    ): Call<List<MemberInfoVO>>

    // 編輯會員資料
    @POST("api/user_edit.php")
    @FormUrlEncoded
    fun apiEditMemberInfo(
        @Field("member_id") memberId: String,
        @Field("member_pwd") memberPwd: String,
        @Field("member_name") memberName: String,
        @Field("member_email") memberEmail: String,
        @Field("member_gender") memberGender: String,
    ): Call<MemberInfoEditResponse>


    // 取得停車資料
    @POST("api/parking_list.php")
    fun apiGetParking(): Call<List<ParkingVO>>


    // 取得數位導覽資料
    @POST("api/tourism_list.php")
    @FormUrlEncoded
    fun apiGetTourism(
        @Field("tourism_id") tourism_id: String,
    ): Call<List<NavigationVO>>


    // 取得店家介紹資料
    @POST("api/store_list.php")
    @FormUrlEncoded
    fun apiGetStoreList(
        @Field("store_id") store_id: String,
    ): Call<List<StoreListVO>>

    // 取得北門好食資料
    @POST("api/food_list.php")
    @FormUrlEncoded
    fun apiGetFoodList(
        @Field("food_id") food_id: String,
    ): Call<List<GoodFoodListVO>>

    // 登出
    @POST("api/user_logout.php")
    @FormUrlEncoded
    fun apiLogout(
        @Field("member_id") memberId: String,
        @Field("member_pwd") memberPwd: String,
    ): Call<LogoutResponse>

    // 會員優惠券
    @POST("api/member_coupon_list.php")
    @FormUrlEncoded
    fun apiMemberCouponList(
        @Field("member_id") memberId: String,
        @Field("member_pwd") memberPwd: String,
        @Field("sid") sid: String,
        @Field("coupon_type") couponType: String,
    ): Call<List<CouponListVO>>


    // region 店長核銷
    @POST("api/coupon_apply.php")
    @FormUrlEncoded
    fun apiManagerWriteOffTicket(
        @Field("member_id") memberId: String,
        @Field("member_pwd") memberPwd: String,
        @Field("coupon_no") couponNo: String
    ): Call<WriteOffTicketResponse>
}