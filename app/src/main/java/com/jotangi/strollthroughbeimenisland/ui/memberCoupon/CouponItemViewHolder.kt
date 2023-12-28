package com.jotangi.strollthroughbeimenisland.ui.memberCoupon

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jotangi.strollthroughbeimenisland.R
import com.jotangi.strollthroughbeimenisland.databinding.ItemCouponBinding

class CouponItemViewHolder(val binding: ItemCouponBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: CouponListVO,
        listener: CouponClickListener?
    ) {

        binding.apply {

            when {
                (data.usingFlag == "0") -> {

                    couponConstraint.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            btnUse.context,
                            R.drawable.shap_round_outlin
                        )
                    )
                    tvTitle.setTextColor(Color.parseColor("#FF000000"))
                    tvStoreName.setTextColor(Color.parseColor("#40798C"))
                    btnUse.text = "可\n使\n用"
                    btnUse.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            btnUse.context,
                            R.drawable.shape_round_green
                        )
                    )

                    couponConstraint.setOnClickListener {

                        listener?.onCouponItemClick(data)
                    }
                }

                (data.usingFlag == "1") -> {

                    couponConstraint.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            btnUse.context,
                            R.drawable.shape_round_outline2
                        )
                    )
                    tvTitle.setTextColor(Color.parseColor("#aaaaaa"))
                    tvStoreName.setTextColor(Color.parseColor("#aaaaaa"))
                    btnUse.text = "已\n使\n用"
                    btnUse.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            btnUse.context,
                            R.drawable.shape_round_gray
                        )
                    )
                    couponConstraint.setOnClickListener {

                    }
                }
            }
        }

        binding.tvTitle.text = data.couponName
        binding.tvStoreName.text = data.storeName
        binding.tvContent.text = data.couponDescription
        binding.tvEndDate.text = data.couponStartdate + "~" + data.couponEnddate
    }
}