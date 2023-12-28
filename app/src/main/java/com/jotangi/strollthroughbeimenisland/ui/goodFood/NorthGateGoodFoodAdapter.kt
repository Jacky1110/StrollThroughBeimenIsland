package com.jotangi.strollthroughbeimenisland.ui.goodFood

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jotangi.strollthroughbeimenisland.databinding.ItemNorthGateGoodFoodBinding


interface GoodFoodClickListener {
    fun onGoodFoodItemClick(vo: GoodFoodListVO)
}

class NorthGateGoodFoodAdapter(
    private var data: List<GoodFoodListVO>,
    val context: Context,
    private val listener: NorthGateGoodFoodListFragment
): RecyclerView.Adapter<NorthGateGoodFoodViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NorthGateGoodFoodViewHolder {
        val binding = ItemNorthGateGoodFoodBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NorthGateGoodFoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NorthGateGoodFoodViewHolder, position: Int) {
        holder.bind(
            data[position],
            listener
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateDataSource(dataSource: List<GoodFoodListVO>) {
        this.data = dataSource

        this.notifyDataSetChanged()
    }
}