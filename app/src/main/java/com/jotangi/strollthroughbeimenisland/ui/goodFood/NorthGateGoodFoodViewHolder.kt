package com.jotangi.strollthroughbeimenisland.ui.goodFood

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jotangi.strollthroughbeimenisland.api.ApiConfig
import com.jotangi.strollthroughbeimenisland.databinding.ItemNavigationShopBinding
import com.jotangi.strollthroughbeimenisland.databinding.ItemNorthGateGoodFoodBinding
import com.jotangi.strollthroughbeimenisland.ui.navigationShop.NavigationClickListener
import com.jotangi.strollthroughbeimenisland.ui.navigationShop.NavigationVO

class NorthGateGoodFoodViewHolder(val binding: ItemNorthGateGoodFoodBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: GoodFoodListVO,
        listener: GoodFoodClickListener?
    ) {

        binding.apply {

            itemGoodFoodConstraintLayout.setOnClickListener {
                listener?.onGoodFoodItemClick(data)
            }

            tvTitle.text = data.foodId
            tvCommodityName.text = data.foodName
            tvStoreName.text = data.storeName
            tvOpenTime.text = data.storeOpenTime

            if (data.foodPhone.isNotBlank() && data.foodWebsite.contains("https")) {

                tvMath.visibility = View.VISIBLE
                tvMath.text = "可電話/線上訂購"

            } else {

                tvMath.visibility = View.GONE
            }

            Glide.with(root).load(ApiConfig.URL_HOST + data.foodPicture).into(ivItemIcon)
        }

    }

}