package com.jotangi.strollthroughbeimenisland.ui.navigationShop

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jotangi.strollthroughbeimenisland.api.ApiConfig
import com.jotangi.strollthroughbeimenisland.databinding.ItemNavigationShopBinding

class NavigationItemViewHolder(val binding: ItemNavigationShopBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: NavigationVO,
        listener: NavigationClickListener?
    ) {

        binding.apply {

            itemNaviConstraintLayout.setOnClickListener {
                listener?.onNavigationItemClick(data)
            }

            tvTitle.text = data.tourismId
            tvCommodityName.text = data.tourismName
            tvOpenTime.text = data.tourismOpenTime
            tvMath.text = data.tourismTicketInfo
            Glide.with(root).load(ApiConfig.URL_HOST + data.tourismPicture).into(ivItemIcon)

        }
    }
}