package com.jotangi.strollthroughbeimenisland.ui.parking

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jotangi.strollthroughbeimenisland.databinding.ItemParkingBinding


interface ParkingClickListener {
    fun onParkingPhoneClick(vo: ParkingVO)
}

interface ParkingOnClickListener {
    fun onParkingMapClick(vo: ParkingVO)
}


class ParkingAdapter(
    private var data: List<ParkingVO>,
    val context: Context,
    private val listener: ParkingClickListener?,
    private val listen: ParkingOnClickListener

) : RecyclerView.Adapter<ParkingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingItemViewHolder {
        val binding = ItemParkingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ParkingItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParkingItemViewHolder, position: Int) {
        holder.bind(
            data[position],
            listener,
            listen
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateDataSource(dataSource: List<ParkingVO>) {
        this.data = dataSource

        this.notifyDataSetChanged()
    }


}