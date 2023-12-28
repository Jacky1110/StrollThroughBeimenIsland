package com.jotangi.strollthroughbeimenisland.ui.memberCoupon

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jotangi.strollthroughbeimenisland.databinding.ItemCouponBinding


interface CouponClickListener {
    fun onCouponItemClick(vo: CouponListVO)
}

class CouponListAdapter(
    private var data: List<CouponListVO>,
    val context: Context,
    private val listener: CouponClickListener?
) : RecyclerView.Adapter<CouponItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponItemViewHolder {
        val binding = ItemCouponBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CouponItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CouponItemViewHolder, position: Int) {
        holder.bind(
            data[position],
            listener
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }


    fun updateDataSource(dataSource: List<CouponListVO>) {
        this.data = dataSource

        this.notifyDataSetChanged()
    }
}