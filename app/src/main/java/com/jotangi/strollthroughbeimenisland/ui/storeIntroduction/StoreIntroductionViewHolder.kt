package com.jotangi.strollthroughbeimenisland.ui.storeIntroduction

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jotangi.strollthroughbeimenisland.api.ApiConfig
import com.jotangi.strollthroughbeimenisland.databinding.ItemStoreIntroductionBinding

class StoreIntroductionViewHolder(val binding: ItemStoreIntroductionBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: StoreListVO,
        listener: StoreIntroductionClickListener?
    ) {

        binding.apply {

            itemStoreIntroductionLayout.setOnClickListener {
                listener?.onStoreIntroductionItemClick(data)
            }

            tvTitle.text = data.storeId
            tvCommodityName.text = data.storeName
            tvOpenTime.text = data.storeOpenTime
            tvMath.text = data.storeOpen

            Glide.with(root).load(ApiConfig.URL_HOST + data.storePicture).into(ivItemIcon)

        }
    }
}