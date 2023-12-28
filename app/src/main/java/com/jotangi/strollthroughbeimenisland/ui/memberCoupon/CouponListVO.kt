package com.jotangi.strollthroughbeimenisland.ui.memberCoupon

import com.google.gson.annotations.SerializedName

data class CouponListVO(

    @SerializedName("coupon_no")
    val couponNo: String,

    @SerializedName("using_flag")
    val usingFlag: String,

    @SerializedName("coupon_name")
    val couponName: String,

    @SerializedName("store_name")
    val storeName: String,

    @SerializedName("coupon_description")
    val couponDescription: String,

    @SerializedName("coupon_startdate")
    val couponStartdate: String,

    @SerializedName("coupon_enddate")
    val couponEnddate: String,
)
