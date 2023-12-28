package com.jotangi.strollthroughbeimenisland.ui.navigationShop

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jotangi.strollthroughbeimenisland.databinding.ItemNavigationShopBinding


interface NavigationClickListener {
    fun onNavigationItemClick(vo: NavigationVO)
}

class NavigationAdapter(
    private var data: List<NavigationVO>,
    val context: Context,
    private val listener: NavigationClickListener?
) : RecyclerView.Adapter<NavigationItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationItemViewHolder {
        val binding = ItemNavigationShopBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NavigationItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int) {
        holder.bind(
            data[position],
            listener
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateDataSource(dataSource: List<NavigationVO>) {
        this.data = dataSource

        this.notifyDataSetChanged()
    }
}