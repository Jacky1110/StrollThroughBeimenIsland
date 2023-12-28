package com.jotangi.strollthroughbeimenisland.ui.storeIntroduction

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jotangi.strollthroughbeimenisland.databinding.ItemStoreIntroductionBinding


interface StoreIntroductionClickListener {
    fun onStoreIntroductionItemClick(vo: StoreListVO)
}

class StoreIntroductionAdapter(
    private var data: List<StoreListVO>,
    val context: Context,
    private val listener: StoreIntroductionListFragment
) : RecyclerView.Adapter<StoreIntroductionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreIntroductionViewHolder {
        val binding = ItemStoreIntroductionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return StoreIntroductionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreIntroductionViewHolder, position: Int) {
        holder.bind(
            data[position],
            listener
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateDataSource(dataSource: List<StoreListVO>) {
        this.data = dataSource

        this.notifyDataSetChanged()
    }


}