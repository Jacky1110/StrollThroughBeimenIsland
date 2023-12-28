package com.jotangi.strollthroughbeimenisland.ui.member

import com.google.gson.annotations.SerializedName

data class MemberInfoVO(

    @SerializedName("member_id")
    val memberId: String,

    @SerializedName("member_name")
    val memberName: String,

    @SerializedName("member_pwd")
    val memberPwd: String,

    @SerializedName("member_type")
    val memberType: String,

    @SerializedName("member_gender")
    val memberGender: String,

    @SerializedName("member_email")
    val memberEmail: String,

    @SerializedName("member_birthday")
    val memberBirthday: String,

    @SerializedName("member_status")
    val memberStatus: String,

    @SerializedName("member_sid")
    val memberSid: String,

    )
